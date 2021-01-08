package com.hussainalmahdi.android.matloob

import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.hussainalmahdi.android.matloob.remotesource.RemoteSource
import com.hussainalmahdi.android.zyara.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Part
import java.io.File


private const val SELECT_PICTURE = 1
private lateinit var businessLogo: ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val app: SocketInstance = application as SocketInstance
        val mSocket = app.getMSocket()
//connecting socket
        mSocket?.connect()
        val options = IO.Options()
        options.reconnection = true //reconnection
        options.forceNew = true


        if(mSocket?.connected()!!)
        {
            Toast.makeText(this,"Socket is connected",Toast.LENGTH_SHORT).show()
        }


        // wait for sultan to complete this
        mSocket?.on("message") { args ->
            if (args[0] != null)
            {
                val data = args[0] as String
                Log.d("output",data.toString())
                runOnUiThread {
                    Log.d("message",args.toString())
                    Toast.makeText(this,"Data received from socket",Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Disconnect
        mSocket?.on(Socket.EVENT_DISCONNECT) {
            runOnUiThread {
                Toast.makeText(this, "Socket Disconnected", Toast.LENGTH_SHORT).show()
            }
        }
//Reconnect
            mSocket?.on(Socket.EVENT_RECONNECT) {
                mSocket?.connect()
                runOnUiThread {
                    Toast.makeText(this, "Socket Reconnected", Toast.LENGTH_SHORT).show()
                }
            }

                //send data to server
                //       mSocket?.emit("eventName", dataObject)
                // on() method is for listening

                fun onDestroy() {
                    super.onDestroy()
                    mSocket?.disconnect()
//                    mSocket?.off("EVENT_NAME",dataObject)
                }


                val joinLayout: LinearLayout = findViewById(R.id.join_layout)
                val signInLayout: LinearLayout = findViewById(R.id.sign_in_layout)
                val joinButton: Button = findViewById(R.id.join_Button)
                val signInButton: Button = findViewById(R.id.sign_in_button)
                val LoginEmailEditText: EditText = findViewById(R.id.login_email_edit_text)
                val LoginPasswordEditText: EditText = findViewById(R.id.login_password_edit_text)

                businessLogo = findViewById(R.id.business_logo)


                businessLogo.setOnClickListener {
                    val i = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
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
                            this, Observer {response ->
                                if (response.responseCode ==200){
                                    val intent = Intent(this, ActivityAfterLogIn::class.java)
                                    intent.apply {
                                        putExtra("name",response.name)
                                        putExtra("phone",response.phoneNumber)
                                        putExtra("email",response.email)
                                        putExtra("description",response.description)
                                        putExtra("token",response.token)
                                    }
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
                val path = this.filesDir.absolutePath
                Log.d("path",filePath)
                var file = File(filePath)

                 val requestFile:RequestBody =RequestBody.create(MediaType.parse("multipart/form-data"), file)

                val email:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"email")
                val name:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"name")
                val password:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"password")
                val phoneNumber:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"phoneNumber")
                val description:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"description")
                val services:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"services")
                val image:MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestFile)
                RemoteSource().register(email,name,password,phoneNumber,description,services,image)


            }

        }


