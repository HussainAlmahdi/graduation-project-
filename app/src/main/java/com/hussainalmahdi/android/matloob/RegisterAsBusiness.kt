package com.hussainalmahdi.android.matloob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.hussainalmahdi.android.zyara.R

class RegisterAsBusiness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_as_business)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }
}