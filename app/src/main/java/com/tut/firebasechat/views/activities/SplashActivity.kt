package com.tut.firebasechat.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.tut.firebasechat.viewmodels.AuthViewModel

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        if (!viewModel.isUserSignedIn()) {
            Intent(this, AuthActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } else{
            Intent(this, HomeActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }
}