package hevezolly.habitstracker.data.Network

import com.google.gson.annotations.SerializedName

data class DoneHabit(
    @SerializedName("date")
    val doneTime: Long,
    @SerializedName("habit_uid")
    val uid: String
)
