package com.hussainalmahdi.android.matloob.remotesource

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DataBaseService {
    @POST("login")
    fun login(@Body auth:Auth): Call<ProfileInfo>

    @POST("request")
    fun addRequest(
        @Header("token") authHeader:String,
        @Body post:Post): Call<Any>

    @GET("/hashtag/autocomplete/{text}")
    fun autocomplete(
        @Header("token") authHeader:String,
        @Path("text") text:String) :Call<List<Hshtags>>

@Multipart
    @POST("register")
    fun register(@Part("body")body: RequestBody): Call<Any>
}