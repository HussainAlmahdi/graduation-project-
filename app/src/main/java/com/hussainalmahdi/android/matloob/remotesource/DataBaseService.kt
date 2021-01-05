package com.hussainalmahdi.android.matloob.remotesource

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DataBaseService {
    @POST("login")
    fun login(@Body auth:Auth): Call<Any>
}