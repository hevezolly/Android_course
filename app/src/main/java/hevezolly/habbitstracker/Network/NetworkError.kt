package hevezolly.habbitstracker.Network

import com.google.gson.annotations.SerializedName

data class NetworkError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)