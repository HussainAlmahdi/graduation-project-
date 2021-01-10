package com.hussainalmahdi.android.matloob.remotesource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
                    override fun onResponse(
                        call: Call<ProfileInfo>,
                        response: Response<ProfileInfo>
                    ) {
                        response.body()?.responseCode = response.code()
                        responseLiveData.value = response.body()
                        Log.d("response", response.body().toString())
                    }

                    override fun onFailure(call: Call<ProfileInfo>, t: Throwable) {
                    }
                })
            return responseLiveData
        }

    fun addRequest(token: String, post: Post){
        dataBaseService.addRequest(token, post).enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d("request response ", response.toString())
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("error", t.toString())

                }
            })
    }


    fun getHashtag(token: String): MutableLiveData<List<Hashtags>> {
        val responseLiveData: MutableLiveData<List<Hashtags>> = MutableLiveData()
        dataBaseService.getUserHashtags(token).enqueue(
            object : Callback<List<Hashtags>> {
                override fun onResponse(call: Call<List<Hashtags>>, response: Response<List<Hashtags>>) {
                    Log.d("get hashtags response ", response.body().toString())
                    responseLiveData.value = response.body()

                }

                override fun onFailure(call: Call<List<Hashtags>>, t: Throwable) {
                    Log.e("error", t.toString())
                }

            }
        )
        return responseLiveData

    }

    fun getUserTimeLine(token: String):LiveData<List<TimeLineItem>>{
        val responseLiveData: MutableLiveData<List<TimeLineItem>> = MutableLiveData()
        dataBaseService.getUserTimeLine(token).enqueue(
            object : Callback<List<TimeLineItem>> {
                override fun onResponse(
                    call: Call<List<TimeLineItem>>,
                    response: Response<List<TimeLineItem>>
                ) {
                    Log.d("get timeLine response ", response.body().toString())
                    responseLiveData.value = response.body()
                }

                override fun onFailure(call: Call<List<TimeLineItem>>, t: Throwable) {
                    Log.e("error", t.toString())
                }

            })
        return responseLiveData
    }



    fun addHashtag(token: String, hashtag: String){
        dataBaseService.addHashtags(token, hashtag).enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d("add hashtag response ", response.toString())
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("error", t.toString())
                }

            }
        )
    }




    fun register(
        email: RequestBody,
        name: RequestBody,
        password: RequestBody,
        phoneNumber: RequestBody,
        description: RequestBody,
        services: RequestBody,
        image: MultipartBody.Part
    ){
        dataBaseService.register(email, name, password, phoneNumber, description, services, image).enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d(" register response ", response.toString())
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("sssserror", t.toString())
                }

            }
        )
    }

    fun autocomplete(token: String, text: String):MutableLiveData<List<Hshtags>> {
        val autocompleteResult: MutableLiveData<List<Hshtags>> = MutableLiveData()

        dataBaseService.autocomplete(token, text).enqueue(
            object : Callback<List<Hshtags>> {
                override fun onResponse(
                    call: Call<List<Hshtags>>,
                    response: Response<List<Hshtags>>
                ) {


                    val SearchResult = response.body()
                    if (SearchResult != null) {
                        autocompleteResult.value = SearchResult
                    }
                    Log.d("autocomplete response ", response.toString())
                }

                override fun onFailure(call: Call<List<Hshtags>>, t: Throwable) {
                    Log.e("error", t.toString())

                }
            })
        return autocompleteResult
    }


    fun getMyRequests(token: String) :MutableLiveData<List<MyRequests>>{
        val responseResult: MutableLiveData<List<MyRequests>> = MutableLiveData()
        dataBaseService.getMyRequests(token).enqueue(
            object : Callback<List<MyRequests>> {
                override fun onResponse(
                    call: Call<List<MyRequests>>,
                    response: Response<List<MyRequests>>
                ) {
                    responseResult.value = response.body()
                    Log.d("my requests response ", response.body().toString())
                }
                override fun onFailure(call: Call<List<MyRequests>>, t: Throwable) {
                    Log.e("error", t.toString())

                }
            })
        return responseResult
    }


}