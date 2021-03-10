package com.example.kotlin_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlin_test.api.RetrofitClient
import com.example.kotlin_test.models.DefaultResponse
import com.example.kotlin_test.models.Student
import com.example.kotlin_test.models.updateResponse
import kotlinx.android.synthetic.main.activity_changepw.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


lateinit var username : String
lateinit var password : String

class ChangepwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepw)

        val bundle: Bundle? = intent.extras
        val idEtudiant = bundle?.get("idEtudiant")




        changeok.setOnClickListener{

            username = newusername.text.trim().toString()
            password    = newpassword.text.trim().toString()


            if(username.isEmpty())
            {
                newusername.error = "username Required !"
                newusername.requestFocus()
                return@setOnClickListener

            }
            if(password.isEmpty())
            {
                newpassword.error = "Password Required !"
                newpassword.requestFocus()
                return@setOnClickListener

            }


            updateStudent(idEtudiant.toString().toInt(), username,password)
        }





    }


    private fun updateStudent(id:Int,usr:String,pswd:String)
    {




        val intent: Intent
        intent = Intent(this,MainActivity::class.java)

        RetrofitClient.instance.updateUser(id,usr,pswd)
            .enqueue(object: Callback<updateResponse> {
                override fun onResponse(call: Call<updateResponse>, response: Response<updateResponse>) {

                    if(!response.body()?.error!!)
                    {

                        val id = response.body()?.user!!.idEtudiant
                        val changedpw = response.body()?.user!!.changedpw
                            if (changedpw == 1)
                            {
                                //Already changed pw and username
                                //  redirect to Main Activity

                                intent.putExtra("idEtudiant", id)
                                startActivity(intent)
                            }

                    }

                }

                override fun onFailure(call: Call<updateResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })


    }



}