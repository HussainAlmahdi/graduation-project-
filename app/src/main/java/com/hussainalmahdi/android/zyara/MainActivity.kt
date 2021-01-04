package com.hussainalmahdi.android.zyara

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val joinLayout:LinearLayout = findViewById(R.id.join_layout)
        val signInLayout:LinearLayout = findViewById(R.id.sign_in_layout)
        val joinButton:Button =findViewById(R.id.join_Button)
        val signInButton:Button =findViewById(R.id.sign_in_button)


        joinButton.setOnClickListener {
            val animationIn: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in )
            val animationOut: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_out )

            joinLayout.visibility = View.VISIBLE
            joinLayout.startAnimation(animationIn)

            signInLayout.startAnimation(animationOut)
            signInLayout.visibility = View.GONE
        }

        signInButton.setOnClickListener {
            val animationIn: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in )
            val animationOut: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_out )


            signInLayout.visibility = View.VISIBLE
            signInLayout.startAnimation(animationIn)

            joinLayout.startAnimation(animationOut)
            joinLayout.visibility = View.GONE
        }





    }
}