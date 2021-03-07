package com.example.kotlin_test.api

import com.example.kotlin_test.models.DefaultResponse
import com.example.kotlin_test.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("createuser")
    fun createUser(
            @Field("nom") nom:String,
            @Field("prenom") prenom:String,
            @Field("age") age:Int,
            @Field("email") email:String,
            @Field("niveau") niveau:String,
            @Field("image") image:String,
            @Field("username") username:String,
            @Field("password") password:String,
            @Field("changedpw") changedpw:Int

     ):Call<DefaultResponse>



    @FormUrlEncoded
    @POST("userlogin")
    fun userLogin(
            @Field("username") username:String,
            @Field("password") password:String


    ):Call<LoginResponse>

}