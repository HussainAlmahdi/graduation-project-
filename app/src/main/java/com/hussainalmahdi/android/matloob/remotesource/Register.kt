package com.hussainalmahdi.android.matloob.remotesource

import com.google.gson.annotations.SerializedName

data class Register (
    @SerializedName("email") val  email:String = "iohgteriogjed@fggg.com",
    @SerializedName("password") val password:String = "54675436",
    @SerializedName("phoneNumber") val phoneNumber:String ="324523532",
    @SerializedName("name") val name:String = "hussain",
    @SerializedName("description") val description:String = "fsdhgsfddgs",
    @SerializedName("services") val services:String = "fdhhdfbvxcnhvxc",
    @SerializedName("logo") val logo:String = "http://hussainalmahdi.com/images/logo.png",
        )