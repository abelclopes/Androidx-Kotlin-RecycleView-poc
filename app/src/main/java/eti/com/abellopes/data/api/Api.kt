package eti.com.abellopes.data.api

import eti.com.abellopes.data.api.model.response.HeroiResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiMarvel {

    @GET("users/{id}")
    fun getUser(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Query("version") version: String
    ): Call<HeroiResponse>
}