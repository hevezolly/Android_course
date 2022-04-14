package hevezolly.habbitstracker

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Room
import hevezolly.habbitstracker.Database.HabitsDao
import hevezolly.habbitstracker.Database.HabitsDatabase
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Network.NetworkHabitService
import hevezolly.habbitstracker.Network.Uid
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@RequiresApi(Build.VERSION_CODES.O)
class HabitService(context: App, converter: Converter.Factory, private val token: String) {

    private val dao: HabitsDao

    private val habits: LiveData<List<Habit>>

    private val network: NetworkHabitService

    fun getHabbits(): LiveData<List<Habit>> = habits

    fun addHabbit(h: Habit){
        val uid = network.updateHabit(token, h).execute().body() ?: Uid("")
        Log.d("NETWORK", uid.value ?: "null")
        h.uid = uid.value
        dao.addHabit(h)
    }

    fun deleteHabit(h: Habit){
        Log.d("DEBUG", h.uid)
        if (h.uid != "")
            network.deleteHabit(token, Uid(h.uid)).execute()
        dao.deleteHabit(h)
    }

    fun replaceHabit(initialHabit: Habit, newHabit: Habit){
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
            HabitsDatabase::class.java, App.DATABASE_NAME)
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