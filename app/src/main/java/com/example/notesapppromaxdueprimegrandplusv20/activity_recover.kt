package com.example.notesapppromaxdueprimegrandplusv20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recover.*

class activity_recover : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover)


        mAuth = FirebaseAuth.getInstance()

        recoverButton.setOnClickListener {
            resetPassword()
        }



        loginInstead.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        register.setOnClickListener {
            val intent = Intent(this, activity_register::class.java)
            startActivity(intent)
        }

    }

    private fun resetPassword(){
        mAuth.sendPasswordResetEmail(mailRecover.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check your email", Toast.LENGTH_LONG)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "something went wrong, Try again later", Toast.LENGTH_SHORT)
                }
            }
    }
}
