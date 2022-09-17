package com.ex.week1_0706012110013_barnanimals

import adapter.AnimalListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ex.week1_0706012110013_barnanimals.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import database.GlobalVar.Companion.animalList
import model.Animal

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private lateinit var animalListRV:RecyclerView
    private var animalAdapter:AnimalListAdapter = AnimalListAdapter(animalList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.title = "Animal Data"

        var animalListRV = bind.animalListRecyclerView
        animalListRV.adapter = animalAdapter
        animalListRV.layoutManager = LinearLayoutManager(baseContext)
        if(animalList.size<2){addSomeDummy()}
//        animalListRV.layoutManager.removeAllViews()

        bind.addAnimalFloatingActionButton.setOnClickListener {
            val myIntent = Intent(this, formActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun addSomeDummy() {
        animalList.add(Animal("", "Henry", "Chicken", 2))
        animalList.add(Animal("", "Herald", "Chicken", 1))
    }


    override fun onResume() {
        super.onResume()
        animalAdapter.notifyDataSetChanged()
    }

}