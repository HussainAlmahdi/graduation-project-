package com.hussainalmahdi.android.matloob.remotesource

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("title") val title:String ="",
    @SerializedName("description") val description:String = "",
    @SerializedName("hashtags") val hashtags:List<String> = mutableListOf("")
        )