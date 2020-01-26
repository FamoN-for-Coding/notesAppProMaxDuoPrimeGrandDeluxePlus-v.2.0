package com.example.notesapppromaxdueprimegrandplusv20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private  lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)


        mAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            loginUser()
        }


        registerInstead.setOnClickListener {
            val intent = Intent(this, activity_register::class.java)
            startActivity(intent)
        }

        recoverPassword.setOnClickListener {
            val intent = Intent(this, activity_recover::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(){

        if(emailLogIn.text.toString().isEmpty()) {
            Toast.makeText(baseContext, "Enter your credentials",Toast.LENGTH_SHORT).show()
            return
        }else if (passwordLogIn.text.toString().isEmpty()) {

            Toast.makeText(baseContext, "Fill This", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.signInWithEmailAndPassword(emailLogIn.text.toString(), passwordLogIn.text.toString()).addOnCompleteListener(this) {task ->
            if(task.isSuccessful) {
                Toast.makeText(baseContext, "Success",Toast.LENGTH_SHORT).show()
                val user = mAuth.currentUser
                updateActivity(user)
            }else{
                Toast.makeText(baseContext, "Login Failed! Try Again",Toast.LENGTH_SHORT).show()
               updateActivity(null)
            }

        }

    }

    private fun updateActivity(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this, notes::class.java))
            finish()
        }

    }


}
