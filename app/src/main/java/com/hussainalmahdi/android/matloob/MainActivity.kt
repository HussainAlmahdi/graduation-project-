package com.hussainalmahdi.android.matloob

import android.R.attr
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Attendees.query
import android.provider.CalendarContract.EventDays.query
import android.provider.CalendarContract.Instances.query
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hussainalmahdi.android.matloob.remotesource.DataBaseService
import com.hussainalmahdi.android.matloob.remotesource.RemoteSource
import com.hussainalmahdi.android.zyara.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream


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
        val LoginEmailEditText: EditText = findViewById(R.id.login_email_edit_text)
        val LoginPasswordEditText: EditText = findViewById(R.id.login_password_edit_text)

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

               val responseCode = RemoteSource().login( LoginEmailEditText.text.toString(),
                       LoginPasswordEditText.text.toString())
                responseCode.observe(
                    this, Observer {code ->
                        if (code ==200){
                            val intent = Intent(this, ActivityAfterLogIn::class.java)
                            startActivity(intent)
                        }
                        else {
                        Toast.makeText(this,"Wrong Email/Password", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

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
                register(selectedImageURI)
                Glide.with(businessLogo).load(selectedImageURI).into(businessLogo)
            }
        }
    }





    fun register(selectedImageURI: Uri?){
        var filePath = ""
        filePath= selectedImageURI?.path.toString()

        var file = File(filePath)

        Log.d("path",filePath)

        MultipartBody.Part.createFormData(file.name,filePath)

        val inputStream = this@MainActivity.contentResolver.openInputStream(selectedImageURI!!)

        val part =MultipartBody.Part.createFormData(file.name,filePath,
            RequestBody.create(MediaType.parse("image/*"),inputStream?.readBytes()))

        var requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", "param1")
            .addFormDataPart("password", "param2")
            .addFormDataPart("phoneNumber", "param1")
            .addFormDataPart("name", "param2")
            .addFormDataPart("description", "param1")
            .addFormDataPart("services", "param2")
            .build()


        RemoteSource().dataBaseService.register(requestBody,).enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d("register response", response.code().toString())
                }
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("register response", "fgfgfgt $t $filePath")
                }
            })
    }

}


