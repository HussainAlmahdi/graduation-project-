package com.hussainalmahdi.android.matloob.remotesource

data class MyRequests(
    val closed :Boolean = false,
    val title:String = "",
    val date:String="",
    val userName:String = "",
    val description:String = "",
    val owner:String = "",
    val hashtagsTitle: List<String> =listOf("one", "two", "one"),
    )