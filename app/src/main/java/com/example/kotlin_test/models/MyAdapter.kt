package com.example.kotlin_test.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.kotlin_test.R
import kotlinx.android.synthetic.main.row.view.*

class MyAdapter(var mCtx: Context , var resources:Int,var items:List<Cours>): ArrayAdapter<Cours>(mCtx,resources,items)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        var layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources,null)

        val nom: TextView = view.findViewById(R.id.nom)
        val laboratoire: TextView = view.findViewById(R.id.laboratoire)
        val exercice: TextView = view.findViewById(R.id.exercice)
        val quiz : TextView = view.findViewById(R.id.quiz)
        val video: TextView = view.findViewById(R.id.video)
        val niveau: TextView = view.findViewById(R.id.niveau)
        val NotesDeCours: TextView = view.findViewById(R.id.notesdecours)

        var mItem:Cours = items[position]

        nom.text = nom.text.toString() + mItem.nom
        laboratoire.text = laboratoire.text.toString() + mItem.laboratoire
        exercice.text = exercice.text.toString() + mItem.exercice
        quiz.text = quiz.text.toString() + mItem.quiz
        video.text = video.text.toString() + mItem.video
        niveau.text = niveau.text.toString() +mItem.niveau
        NotesDeCours.text = NotesDeCours.text.toString() + mItem.NotesDeCours


        return view
    }
}