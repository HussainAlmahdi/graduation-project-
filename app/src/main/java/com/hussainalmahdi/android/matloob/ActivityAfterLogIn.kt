package com.hussainalmahdi.android.matloob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hussainalmahdi.android.zyara.R

class ActivityAfterLogIn : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_log_in)
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
                    val fragment = ChatFragment()
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
                R.id.profile ->{
                    val fragment = ProfileFragment()
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

                R.id.request ->{
                    val fragment = RequestFragment()
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

                R.id.timeline ->{
                    val fragment = TimeLineFragment()
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