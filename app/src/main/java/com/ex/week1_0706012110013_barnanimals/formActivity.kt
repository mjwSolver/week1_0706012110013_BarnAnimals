package com.ex.week1_0706012110013_barnanimals

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ThemedSpinnerAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doOnTextChanged
import com.ex.week1_0706012110013_barnanimals.databinding.ActivityFormBinding
import com.google.android.material.snackbar.Snackbar
import database.GlobalVar.Companion.animalList
import model.Animal

class formActivity : AppCompatActivity() {

    private lateinit var bind:ActivityFormBinding
    private var theLink:String = ""

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val url = it.data?.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (url != null) { baseContext.getContentResolver().takePersistableUriPermission( url,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION )
                }
                bind.animalImageView2.setImageURI(url)
                theLink = url.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFormBinding.inflate(layoutInflater)
        setContentView(bind.root)
        var theBar = supportActionBar
        theBar?.setDisplayHomeAsUpEnabled(true)
        theBar?.title = "Create New Animal"

        // Pre-Processing
        // Receiving Intents for edit, for create ya udah deh
        var theIndex = intent.getIntExtra("list_index", -1)
        var theURIPlease = intent.getStringExtra("theURI").toString()
        var theName = intent.getStringExtra("theName").toString()
        var theType = intent.getStringExtra("theType").toString()
        var theAge = intent.getIntExtra("theAge", -1)
//            Toast.makeText(this, "$theIndex", Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, "$theName", Toast.LENGTH_SHORT).show()


        if (theIndex!=-1) { // If it's Edit then...
            if (!theURIPlease.isNullOrBlank()) {
                bind.animalImageView2.setImageURI(Uri.parse(theURIPlease))
            }
            bind.namaHewanTextInputLayout.editText?.setText(theName)
            bind.jenisHewanTextInputLayout.editText?.setText(theType)
            bind.umurHewanTextInputLayout.editText?.setText("$theAge")
            theBar?.title = "Edit Animal Data"
            bind.saveButton.text = "Save Changes"
        }

        // Changing the Photo, connected to GetResult
        bind.editAnimalImageFloatingActionButton.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        var nameText = bind.namaHewanTextInputLayout.editText
        nameText?.doOnTextChanged{ text, _, _, _ ->
            if(text.isNullOrEmpty()) { bind.namaHewanTextInputLayout.error = "Can't be Empty" }
        }
        var typeText = bind.jenisHewanTextInputLayout.editText
        typeText?.doOnTextChanged{ text, _, _, _ ->
            if(text.isNullOrEmpty()) { bind.jenisHewanTextInputLayout.error = "Can't be Empty" }
        }
        var ageText = bind.umurHewanTextInputLayout.editText
        ageText?.doOnTextChanged{ text, _, _, _ ->
            if(text.isNullOrEmpty()) { bind.umurHewanTextInputLayout.error = "Can't be Empty" }
        }


        bind.saveButton.setOnClickListener{

            var uri:String
            uri = if(theLink.isNotEmpty()) { theLink }
            else if(theURIPlease.isNotEmpty()) { theURIPlease }
            else { "" }

            if(checkAnimalData()){
                var name = bind.namaHewanTextInputLayout.editText?.text.toString().trim()
                var type = bind.jenisHewanTextInputLayout.editText?.text.toString().trim()
                var age = bind.umurHewanTextInputLayout.editText?.text.toString().trim().toInt()

                var newAnimal = Animal(uri, name, type, age)

                //Save the new Data
                var message = "Animal?"
                if(theIndex==-1){ animalList.add(newAnimal); message = "Animal Added"}
                else if(theIndex!=-1) {
                    animalList[theIndex] = newAnimal; message = "Data Updated"
                }

                finish()
                Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun checkAnimalData():Boolean{
        var allClear = true

//        if(newAnimal.name.isNullOrEmpty()) {
//            allClear = false
//            Toast.makeText(this, "Name cannot be Empty", Toast.LENGTH_SHORT).show()}
//        if(newAnimal.type.isNullOrEmpty()) {
//            allClear = false
//            Toast.makeText(this, "Type cannot by Empty", Toast.LENGTH_SHORT).show()}
//        if(newAnimal.age<=0) {
//            allClear = false
//            Toast.makeText(this, "Invalid Age input", Toast.LENGTH_SHORT).show()}

        if(bind.namaHewanTextInputLayout.editText?.text.isNullOrEmpty()) {
            allClear = false
            Toast.makeText(this, "Name cannot be Empty", Toast.LENGTH_SHORT).show()}
        if(bind.jenisHewanTextInputLayout.editText?.text.isNullOrEmpty()) {
            allClear = false
            Toast.makeText(this, "Type cannot by Empty", Toast.LENGTH_SHORT).show()}
        if(bind.umurHewanTextInputLayout.editText?.text.isNullOrEmpty()) {
            allClear = false
            Toast.makeText(this, "Invalid Age input", Toast.LENGTH_SHORT).show()}

        return allClear

    }

}