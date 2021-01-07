package com.hussainalmahdi.android.matloob.remotesource

data class ProfileInfo(
    val token:String = "",
    var responseCode:Int = 400,
    val email:String ="",
    val phoneNumber:Double=0.0,
    val name:String="",
    val description:String="",
    val service: String="",
)