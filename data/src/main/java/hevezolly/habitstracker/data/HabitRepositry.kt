package hevezolly.habitstracker.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Room
import hevezolly.habitstracker.data.Database.HabitsDao
import hevezolly.habitstracker.data.Database.HabitsDatabase
import hevezolly.habitstracker.data.Network.DoneHabit
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.data.Network.NetworkHabitService
import hevezolly.habitstracker.data.Network.Uid
import hevezolly.habitstracker.domain.Model.EditedHabit
import hevezolly.habitstracker.data.exchange.ExchangableHabit
import hevezolly.habitstracker.data.exchange.toExchangable
import hevezolly.habitstracker.data.exchange.toHabit
import hevezolly.habitstracker.domain.useCases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@RequiresApi(Build.VERSION_CODES.O)
class HabitRepositry(
    private val database: HabitsDatabase, converter: Converter.Factory,
    private val token: String
): IHabitsListProvider, IHabitAddReciver, IHabitReplaceReciver, IHabitDeleteReciver, IHabitCompleteReciver {

    private val dao: HabitsDao

    private val habits: Flow<List<ExchangableHabit>>

    private val network: NetworkHabitService

    override fun getHabits(): Flow<List<Habit>> = habits.map { list -> list.map { it.toHabit() } }

    override suspend fun addHabit(habit: Habit){
        val exchangable = habit.toExchangable()
        val uid = network.updateHabit(token, exchangable).execute().body() ?: Uid("")
        Log.d("NETWORK", uid.value ?: "null")
        exchangable.uid = uid.value
        dao.addHabit(exchangable)
    }

    override suspend fun deleteHabit(habit: Habit){
        val uid = getCurrentHabits().first { it.id == habit.id }.uid
        Log.d("DEBUG", uid)
        if (uid != "")
            network.deleteHabit(token, Uid(uid)).execute()
        dao.deleteHabit(habit.toExchangable())
    }

    override suspend fun replaceHabit(editedHabit: EditedHabit) {
        replaceHabit(editedHabit.initialHabit.toExchangable(), editedHabit.newHabit.toExchangable())
    }

    private suspend fun replaceHabit(initialHabit: ExchangableHabit, newHabit: ExchangableHabit){
        newHabit.uid = initialHabit.uid
        newHabit.id = initialHabit.id
        if (newHabit.uid != "")
            network.updateHabit(token, newHabit).execute()
        dao.replaceHabit(newHabit)
    }

    fun syncDatabaseWithNetwork(){
        val h = network.getHabits(token).execute()
        if(!h.isSuccessful)
            return
        for (remoteHabit in h.body() ?: listOf()) {
            val localHabit = dao.getHabitsByUid(remoteHabit.uid).maxByOrNull { it.creationTime }
            if (localHabit == null) {
                dao.addHabit(remoteHabit)
                continue
            }
            if (localHabit.creationTime > remoteHabit.creationTime){
                network.updateHabit(token, localHabit).execute()
                continue
            }
            remoteHabit.id = localHabit.id
            dao.replaceHabit(remoteHabit)
        }
        dao.getHabitsByUid("").forEach{
            val uid = network.updateHabit(token, it).execute().body() ?: Uid("")
            it.uid = uid.value
            dao.replaceHabit(it)
        }
    }

    private fun getCurrentHabits(): List<ExchangableHabit>{
        var h: List<ExchangableHabit>
        runBlocking {
            h = habits.first()
        }
        return h
    }

    init {
        dao = database.habitsDao()
        habits = dao.getHabits()
        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor{
                Log.i("intercept", it)
            })
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(converter)
            .build()
        network = retrofit.create(NetworkHabitService::class.java)
    }

    override suspend fun complete(habit: Habit, date: Long) {
        dao.replaceHabit(habit.toExchangable())
        val uid = getCurrentHabits().first { it.id == habit.id }.uid
        Log.v("NETWORK", uid)
        network.doneHabit(token, DoneHabit(date, uid))
    }
}