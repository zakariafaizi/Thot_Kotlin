package com.example.kotlin_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.kotlin_test.api.RetrofitClient
import com.example.kotlin_test.models.LoginResponse
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_changepw.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle


    lateinit var nav : NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val bundle: Bundle? = intent.extras

        val etat = bundle?.get("register")

        val actualusername = bundle?.get("username")
        val actualpassword = bundle?.get("password")



        if(etat == "yes")
        {
            textview2.text =
                    "\n" + "----------" +
                    "\n" + "Your Actual Username : " + actualusername +
                    "\n" + "Your Actual Password : " + actualpassword +
                            "\n" + "----------"


        }



        nav = findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)




        nav.setNavigationItemSelectedListener{
            when(it.itemId){

                R.id.itemRegister ->{
                    this.startActivity(Intent(this,RegisterActivity::class.java))
                }

                R.id.itemLogin -> {
                    this.startActivity(Intent(this,LoginActivity::class.java))
                }

                R.id.itemHome -> {
                    this.startActivity(Intent(this,MainActivity::class.java))
                }
                else -> super.onOptionsItemSelected(it)
            }


            true
        }

        loginok.setOnClickListener {
            val username = usernamelg.text.toString().trim()
            val password = passwordlg.text.toString().trim()

            if(password.isEmpty())
            {
                passwordlg.error = "Password Required"
                passwordlg.requestFocus()
                return@setOnClickListener

            }

            if(username.isEmpty())
            {
                usernamelg.error = "Username Required"
                usernamelg.requestFocus()
                return@setOnClickListener

            }


            RetrofitClient.instance.userLogin(username ,password)
                    .enqueue(object:Callback<LoginResponse>
            {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                    if(!response.body()?.error!!)
                    {


                        val id = response.body()?.user!!.idEtudiant
                        val changedpw = response.body()?.user!!.changedpw




                        redirectStudent(id,changedpw)

                        Toast.makeText(applicationContext, "id" + id, Toast.LENGTH_LONG).show()

                    }
                    else
                    {
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()

                    }


                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }





    }

    fun redirectStudent( id:Int, chpw:Int)
    {
        val intent : Intent

        if(chpw == 1)
        {
            //Already changed pw and username
            //  redirect to Main Activity
            intent = Intent(this,MainActivity::class.java)
            intent.putExtra("idEtudiant" , id)
            startActivity(intent)
        }
        else if(chpw == 0)
        {
            //have not changed pw and username
            //  redirect to ChangepwActivity
            intent = Intent(this,ChangepwActivity::class.java)
            intent.putExtra("idEtudiant" , id)
            startActivity(intent)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }





}