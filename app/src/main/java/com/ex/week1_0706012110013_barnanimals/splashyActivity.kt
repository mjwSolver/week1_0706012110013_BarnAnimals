package com.ex.week1_0706012110013_barnanimals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ex.week1_0706012110013_barnanimals.databinding.ActivitySplashyBinding

class splashyActivity : AppCompatActivity() {

    private lateinit var bind:ActivitySplashyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashyBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar!!.hide()

        bind.animalProtectionImageView.animate().setDuration(2800).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }


    }
}