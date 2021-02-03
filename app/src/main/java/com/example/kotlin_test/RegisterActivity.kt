package com.example.kotlin_test

import android.R.attr
import android.R.string
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
import kotlin.random.Random


class RegisterActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    lateinit var prenom : String
    lateinit var nom : String
    lateinit var niveau : String
    lateinit var util : String
    lateinit var mdp : String
    lateinit var courriel : String
    lateinit var image : String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        toggle = ActionBarDrawerToggle(this, drawerLayout3, R.string.open, R.string.close)
        drawerLayout3.addDrawerListener(toggle)
        toggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


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




        registerok.setOnClickListener {
            registerStudent()
        }







    }


    private fun registerStudent()
    {
        prenom = firstname.text.trim().toString()
        nom    = lastname.text.trim().toString()
        niveau = level.text.trim().toString()
        courriel = email.text.trim().toString()


        util = usr(nom,prenom,courriel)
        mdp = pswd(nom,prenom,courriel)

        val student = Student(prenom, nom, niveau, util, mdp, courriel, image, false)


    }


    private fun usr(n:String,p:String,c:String) {
        util = n.split(0,"f")
        return util
    }


    private fun pswd(nom: String, prenom: String) {
        val rnd = Random()
        mdp =
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