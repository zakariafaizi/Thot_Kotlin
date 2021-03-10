package com.example.kotlin_test


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.example.kotlin_test.api.RetrofitClient
import com.example.kotlin_test.models.Cours
import com.example.kotlin_test.models.LoginResponse
import com.example.kotlin_test.models.MyAdapter
import com.example.kotlin_test.models.coursResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        try{
            val bundle: Bundle? = intent.extras
            val idEtudiant = bundle?.get("idEtudiant")
            showClasses(idEtudiant.toString().toInt())
        }
        catch(ex:Exception)
        {
            this.startActivity(Intent(this,LoginActivity::class.java))
        }






        navView2.setNavigationItemSelectedListener{
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

    }


    private fun showClasses(id:Int)
    {

        RetrofitClient.instance.showClasses(id)
                .enqueue(object: Callback<coursResponse>
                {
                    override fun onResponse(call: Call<coursResponse>, response: Response<coursResponse>) {

                        if(!response.body()?.error!!)
                        {

                            var listview = findViewById<ListView>(R.id.listview0)

                            var list = mutableListOf<Cours>()


                            for(element in  arrayOf(response.body()?.cours))
                            {

                                list.add(Cours(response.body()?.cours!!.nom ,response.body()?.cours!!.laboratoire,response.body()?.cours!!.exercice ,response.body()?.cours!!.quiz ,response.body()?.cours!!.video,response.body()?.cours!!.niveau ,response.body()?.cours!!.NotesDeCours  ))
                            }


                            listview.adapter = MyAdapter(applicationContext,R.layout.row, list)


                        }
                        else
                        {
                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()

                        }


                    }

                    override fun onFailure(call: Call<coursResponse>, t: Throwable) {

                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                })
    }

}

