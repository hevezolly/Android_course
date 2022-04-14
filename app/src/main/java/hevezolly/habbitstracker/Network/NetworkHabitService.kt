package hevezolly.habbitstracker.Network

import hevezolly.habbitstracker.Model.Habit
import retrofit2.Call
import retrofit2.http.*
import java.lang.Error

interface NetworkHabitService {

    @GET("habit")
    fun getHabits(@Header("Authorization") token: String): Call<List<Habit>>

    @PUT("habit")
    fun updateHabit(@Header("Authorization") token: String, @Body habit: Habit): Call<Uid>

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    fun deleteHabit(@Header("Authorization") token: String, @Body uid: Uid): Call<Unit>

}