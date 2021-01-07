package com.hussainalmahdi.android.matloob.remotesource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class RemoteSource {

     val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://matloob.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

     val dataBaseService: DataBaseService = retrofit.create(DataBaseService::class.java)

        fun login(username: String, password: String):LiveData<ProfileInfo>{

            val responseLiveData: MutableLiveData<ProfileInfo> = MutableLiveData()

            dataBaseService.login(Auth(username, password)).enqueue(
                object : Callback<ProfileInfo> {
                    override fun onResponse(call: Call<ProfileInfo>, response: Response<ProfileInfo>) {
                               response.body()?.responseCode =response.code()
                        responseLiveData.value = response.body()
                        Log.d("response", response.body().toString())
                    }

                    override fun onFailure(call: Call<ProfileInfo>, t: Throwable) {
                    }
                })
            return responseLiveData
        }

    fun addRequest(token:String,post:Post){
        dataBaseService.addRequest(token,post).enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d("request response ",response.toString())
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("error",t.toString())

                }
            })
    }

    fun autocomplete(token:String,text:String):MutableLiveData<List<Hshtags>> {
        val autocompleteResult: MutableLiveData<List<Hshtags>> = MutableLiveData()

        dataBaseService.autocomplete(token,text).enqueue(
            object : Callback<List<Hshtags>> {
                override fun onResponse(call: Call<List<Hshtags>>, response: Response<List<Hshtags>>) {


                    val SearchResult=response.body()
                    if(SearchResult!=null){
                        autocompleteResult.value = SearchResult}
                    Log.d("autocomplete response ",response.toString())
                }

                override fun onFailure(call: Call<List<Hshtags>>, t: Throwable) {
                    Log.e("error",t.toString())

                }
            })
        return autocompleteResult
    }


}