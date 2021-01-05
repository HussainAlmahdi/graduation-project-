package com.hussainalmahdi.android.matloob.remotesource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteSource {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://matloob.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val dataBaseService: DataBaseService = retrofit.create(DataBaseService::class.java)

        fun login(username:String,password:String):LiveData<Int>{
            val responseLiveData: MutableLiveData<Int> = MutableLiveData()

            dataBaseService.login(Auth(username,password)).enqueue(
                object :Callback<Any>{
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        responseLiveData.value =response.code()
                        Log.d("response",response.code().toString())
                    }
                    override fun onFailure(call: Call<Any>, t: Throwable) {
                    }
                })
            return responseLiveData
        }

}