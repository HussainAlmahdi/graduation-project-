package com.hussainalmahdi.android.matloob

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hussainalmahdi.android.zyara.R

class ActivityAfterLogIn : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_log_in)

        val name = intent.getStringExtra("name")
        val token = intent.getStringExtra("token")
        val phone = intent.getDoubleExtra("phone",0.0)
        val email = intent.getStringExtra("email")
        val description = intent.getStringExtra("description")
        bottomNavigationView = findViewById(R.id.bottom_navigation)


        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = TimeLineFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.chat -> {
                    val args = Bundle().apply {
                        putString("token",token)
                    }
                    val fragment = ChatFragment()
                    fragment.arguments =args

                    val fm = this.supportFragmentManager
                    fm.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                        )
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }
                R.id.profile -> {
                    val fragment = ProfileFragment()
                    val args = Bundle().apply {
                        putString("name",name)
                        putDouble("phone",phone)
                        putString("email",email)
                        putString("description",description)
                        putString("token",token)

                    }

                    fragment.arguments =args
                    val fm = this.supportFragmentManager
                    fm.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                        )
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }

                R.id.request -> {
                    val args = Bundle().apply {
                        putString("token",token)
                    }
                    val fragment = RequestFragment()
                    fragment.arguments =args
                    val fm = this.supportFragmentManager
                    fm.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                        )
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }

                R.id.timeline -> {
                    val args = Bundle().apply {
                        putString("token",token)
                    }
                    val fragment = TimeLineFragment()
                    fragment.arguments =args
                    val fm = this.supportFragmentManager
                    fm.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                        )
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }
            }
            true
        }
    }
}