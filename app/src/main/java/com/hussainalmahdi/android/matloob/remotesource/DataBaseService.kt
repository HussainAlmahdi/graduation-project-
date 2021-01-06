package com.hussainalmahdi.android.matloob.remotesource

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DataBaseService {
    @POST("login")
    fun login(@Body auth:Auth): Call<Any>

@Multipart
    @POST("register")
    fun register(@Part("body")body: RequestBody): Call<Any>
}