package com.example.hazardhub

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hazardhub.databinding.ActivityMainBinding
import com.example.hazardhub.verify.LogInFragment
import com.example.hazardhub.verify.VerifyActivity

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("user_email",null)

        if(userEmail != null){
            val intent = Intent(this, ProjectActivity::class.java)
            startActivity(intent)
            finish()
        }

        mainBinding.getStarted.setOnClickListener {
            getInternetPermission()
            val intent = Intent(this,VerifyActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getInternetPermission() {

    }
}