package com.hussainalmahdi.android.matloob.remotesource

import com.google.gson.annotations.SerializedName

class AddHashtag (
    @SerializedName("hashtags")  val hashtags:List<String> =listOf("one", "two", "one"),
        )