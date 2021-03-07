package com.example.kotlin_test.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.56.1/session5/kotlinp1/myapi/public/"


    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                        .addHeader("Authorization" , "")
                        .method(original.method() , original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()


    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        retrofit.create(Api::class.java)

    }


}