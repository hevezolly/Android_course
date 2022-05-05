package hevezolly.habitstracker.data.Network

import com.google.gson.annotations.SerializedName


data class Uid(
    @SerializedName("uid")
    val value: String
)