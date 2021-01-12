package com.hussainalmahdi.android.matloob.remotesource

data class ProfileInfo(
    val token:String = "",
    var responseCode:Int = 400,
    val email:String ="",
    val phoneNumber:String="",
    val name:String="",
    val description:String="",
    val service: String="",
    val _id:String=""
)