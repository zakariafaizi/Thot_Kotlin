package com.example.kotlin_test


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bundle: Bundle? = intent.extras
        val idEtudiant = bundle?.get("idEtudiant")




        text1.text = text1.text.toString() + idEtudiant.toString()
    }



}