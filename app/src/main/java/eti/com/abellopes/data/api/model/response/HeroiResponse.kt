package eti.com.abellopes.data.api.model.response

import com.google.gson.annotations.SerializedName
import eti.com.abellopes.repository.model.Heroi

data class HeroiResponse (
    val success: Boolean,
    val token: String?,
    @SerializedName("data")
    val user: Heroi?
)