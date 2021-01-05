package com.hussainalmahdi.android.matloob

import android.R.attr
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hussainalmahdi.android.zyara.R


private const val SELECT_PICTURE = 1
private lateinit var businessLogo: ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val joinLayout: LinearLayout = findViewById(R.id.join_layout)
        val signInLayout: LinearLayout = findViewById(R.id.sign_in_layout)
        val joinButton: Button = findViewById(R.id.join_Button)
        val signInButton: Button = findViewById(R.id.sign_in_button)
        businessLogo = findViewById(R.id.business_logo)


        businessLogo.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }



        joinButton.setOnClickListener {
            val animationIn: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
            val animationOut: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_out)

            joinLayout.visibility = View.VISIBLE
            joinLayout.startAnimation(animationIn)

            signInLayout.startAnimation(animationOut)
            signInLayout.visibility = View.GONE
        }

        signInButton.setOnClickListener {
            val animationIn: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
            val animationOut: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_out)

            if (signInLayout.visibility == View.VISIBLE) {
                val intent = Intent(this, ActivityAfterLogIn::class.java)
                startActivity(intent)
            }

            else{
            signInLayout.visibility = View.VISIBLE
            signInLayout.startAnimation(animationIn)

            joinLayout.startAnimation(animationOut)
            joinLayout.visibility = View.GONE}
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageURI: Uri? = data?.data

                Glide.with(businessLogo).load(selectedImageURI).into(businessLogo)
            }
        }
    }
}
