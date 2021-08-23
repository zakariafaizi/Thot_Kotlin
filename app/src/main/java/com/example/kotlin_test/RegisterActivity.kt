package com.example.kotlin_test

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Config
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_test.api.RetrofitClient
import com.example.kotlin_test.models.Credentials
import com.example.kotlin_test.models.Credentials.Companion.EMAIL
import com.example.kotlin_test.models.DefaultResponse
import com.example.kotlin_test.models.Student
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class RegisterActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    lateinit var prenom : String
    lateinit var nom : String
    lateinit var age : String
    lateinit var niveau : String
    lateinit var util : String
    lateinit var mdp : String
    lateinit var courriel : String
    lateinit var image : String
    lateinit  var appExecutors : AppExecutors

    lateinit var nav : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        toggle = ActionBarDrawerToggle(this, drawerLayout3, R.string.open, R.string.close)
        drawerLayout3.addDrawerListener(toggle)
        toggle.syncState()


        nav = findViewById(R.id.navView3)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        appExecutors = AppExecutors()

        picture.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else
                {
                    pickImage()
                }
            }

            else
            {
                pickImage()

            }
        }

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


        registerok.setOnClickListener {

            prenom = firstname.text.trim().toString()
            nom    = lastname.text.trim().toString()
            age = Age.text.trim().toString()
            niveau = level.text.trim().toString()
            courriel = email.text.trim().toString()

            if(courriel.isEmpty())
            {
                email.error = "Email Required"
                email.requestFocus()
                return@setOnClickListener

            }


            registerStudent()
        }







    }


    private fun sendEmail(prm:String,usr:String,pwd:String,email:String)
    {
        appExecutors.diskIO().execute {
            val props = System.getProperties()
            props.put("mail.smtp.host", "smtp.gmail.com")
            props.put("mail.smtp.socketFactory.port", "465")
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", "465")

            val session =  Session.getInstance(props,
                    object : javax.mail.Authenticator() {
                        //Authenticating the password
                        override fun getPasswordAuthentication(): PasswordAuthentication {
                            return PasswordAuthentication(EMAIL, Credentials.PASSWORD)
                        }
                    })

            try {
                //Creating MimeMessage object
                val mm = MimeMessage(session)

                //Setting sender address
                mm.setFrom(InternetAddress(EMAIL))
                //Adding receiver
                mm.addRecipient(Message.RecipientType.TO,
                        InternetAddress(email))
                //Adding subject
                mm.subject = "Your Thot Login Credentials"
                //Adding message
                val mess :String = "Hello "+ prm + ", \n" + "This is your Username : " + usr  + "\n" + "This is your Password : " + pwd + "\n Have a good day !"
                mm.setText(mess)

                //Sending email
                Transport.send(mm)

                appExecutors.mainThread().execute {
                    //Something that should be executed on main thread.
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }

    }

    private fun registerStudent()
    {




        util = usr(nom,prenom,courriel)
        mdp = pswd(nom,prenom,courriel)





        RetrofitClient.instance.createUser(nom,prenom,age.toInt(),courriel,niveau,image,util,mdp,0)
                .enqueue(object:Callback<DefaultResponse>{
                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {

                        if(!response.body()?.error!!)
                        {
                            val id = response.body()?.id

                            if(response.body()?.message == "User created successfully" )
                            {

                                    sendEmail(prenom,util,mdp,courriel)
                                    Toast.makeText(applicationContext, response.body()?.message + " and email sent !", Toast.LENGTH_LONG).show()
                                    redirectStudent(id.toString().toInt())

                            }

                            else{
                                Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                            }
                        }


                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                })


    }

    fun redirectStudent( id:Int)
    {
            val intent : Intent

            //have not changed pw and username
            //  redirect to LoginActivity

            intent = Intent(this,LoginActivity::class.java)
            intent.putExtra("register" , "yes")
            intent.putExtra("idEtudiant" , id)
            intent.putExtra("username" , util)
            intent.putExtra("password" , mdp)

            startActivity(intent)


    }

    private fun usr(n:String,p:String,c:String): String {
        util = n.substring(0,2).toUpperCase() +"" + p.substring(1,3) + "" + c.substring(2,3)
        return util
    }


    private fun pswd(n:String,p:String,c:String): String {
        mdp = n.substring(1,2).toUpperCase() +"@" + p.substring(2,3).toUpperCase() + "" + c.substring(3,5)
        return mdp
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


    private fun pickImage()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE)
        {
            image = data?.data.toString()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;
    }



    override fun onOptionsItemSelected(item: MenuItem):Boolean
    {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }




}