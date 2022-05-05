package hevezolly.habitstracker.data.Network

import hevezolly.habitstracker.data.exchange.ExchangableHabit
import hevezolly.habitstracker.domain.Model.Habit
import retrofit2.Call
import retrofit2.http.*

interface NetworkHabitService {

    @GET("habit")
    fun getHabits(@Header("Authorization") token: String): Call<List<ExchangableHabit>>

    @PUT("habit")
    fun updateHabit(@Header("Authorization") token: String, @Body habit: ExchangableHabit): Call<Uid>

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    fun deleteHabit(@Header("Authorization") token: String, @Body uid: Uid): Call<Unit>

    @POST("habit_done")
    fun doneHabit(@Header("Authorization") token: String, @Body done: DoneHabit): Call<Unit>
}