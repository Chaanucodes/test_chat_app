package com.example.yexperi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

const val REQ_CODE_AUTH = 1190

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (FirebaseAuth.getInstance().currentUser != null) {
            Intent(this, MainActivity2::class.java).apply {
                FirebaseDatas.assignUserID()
                startActivity(this)
                finish()
            }
        }

    }

    fun logon(v: View) {

        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build(),
            REQ_CODE_AUTH
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_AUTH) {
            Intent(this, MainActivity2::class.java).apply {
                FirebaseDatas.assignUserID()
                startActivity(this)
                finish()
            }
        }
    }


}