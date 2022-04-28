package hevezolly.habbitstracker.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Room
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.data.Database.HabitsDao
import hevezolly.habbitstracker.data.Database.HabitsDatabase
import hevezolly.habbitstracker.domain.Model.Habit
import hevezolly.habbitstracker.data.Network.NetworkHabitService
import hevezolly.habbitstracker.data.Network.Uid
import hevezolly.habbitstracker.domain.Model.EditedHabit
import hevezolly.habbitstracker.domain.useCases.IHabitAddReciver
import hevezolly.habbitstracker.domain.useCases.IHabitDeleteReciver
import hevezolly.habbitstracker.domain.useCases.IHabitReplaceReciver
import hevezolly.habbitstracker.domain.useCases.IHabitsListProvider
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@RequiresApi(Build.VERSION_CODES.O)
class HabitRepositry(
    context: App, converter: Converter.Factory,
    private val token: String
): IHabitsListProvider, IHabitAddReciver, IHabitReplaceReciver, IHabitDeleteReciver {

    private val dao: HabitsDao

    private val habits: Flow<List<Habit>>

    private val network: NetworkHabitService

    override fun getHabits(): Flow<List<Habit>> = habits

    override fun addHabit(habit: Habit){
        val uid = network.updateHabit(token, habit).execute().body() ?: Uid("")
        Log.d("NETWORK", uid.value ?: "null")
        habit.uid = uid.value
        dao.addHabit(habit)
    }

    override fun deleteHabit(habit: Habit){
        Log.d("DEBUG", habit.uid)
        if (habit.uid != "")
            network.deleteHabit(token, Uid(habit.uid)).execute()
        dao.deleteHabit(habit)
    }

    override fun replaceHabit(editedHabit: EditedHabit) {
        replaceHabit(editedHabit.initialHabit, editedHabit.newHabit)
    }

    private fun replaceHabit(initialHabit: Habit, newHabit: Habit){
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

    init {
        val database = Room.databaseBuilder(context,
            HabitsDatabase::class.java, App.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
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
}