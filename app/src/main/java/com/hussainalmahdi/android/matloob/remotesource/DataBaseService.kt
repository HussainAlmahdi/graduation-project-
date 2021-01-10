package com.hussainalmahdi.android.matloob.remotesource

import okhttp3.MultipartBody
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

    /////array of hashtags
    @POST("hashtag")
    fun addHashtags(
        @Header("token") authHeader:String,
        @Body addHashtag:AddHashtag,
    ): Call<Any>

    @GET("user/hashtag")
    fun getUserHashtags(
        @Header("token") authHeader:String,
    ): Call<List<Hashtags>>

    @GET("/user/requests")
    fun getUserTimeLine(
        @Header("token") authHeader:String,
    ): Call<List<TimeLineItem>>


    @GET("/myrequests")
    fun getMyRequests(
        @Header("token") authHeader:String,
    ): Call<List<MyRequests>>

    @GET("/hashtag/autocomplete/{text}")
    fun autocomplete(
        @Header("token") authHeader:String,
        @Path("text") text:String) :Call<List<Hshtags>>

    @Multipart
    @POST("register")
    fun register(
        @Part("email") email:RequestBody,
        @Part("password") password:RequestBody,
        @Part("phoneNumber") phoneNumber:RequestBody,
        @Part("name") name:RequestBody,
        @Part("description") description:RequestBody,
        @Part("services") services:RequestBody,
        @Part logo  : MultipartBody.Part
                ): Call<Any>

}