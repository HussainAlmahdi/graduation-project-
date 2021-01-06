package com.hussainalmahdi.android.matloob.remotesource

import com.google.gson.annotations.SerializedName

data class Auth (
    @SerializedName("email") val  email:String = "",
    @SerializedName("password") val password:String = ""
)