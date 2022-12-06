package com.example.farmmanagment

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.farmmanagment.ui.MainScreen
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        signin.setOnClickListener {
            updatesUi( email.text.toString(),password.text.toString() )
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
    }


    fun updatesUi(email:String, password:String){
        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainScreen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                }
            })



    }
}