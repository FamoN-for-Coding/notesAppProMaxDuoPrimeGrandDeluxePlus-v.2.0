package com.example.notesapppromaxdueprimegrandplusv20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class activity_register : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance().reference

        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        signup.setOnClickListener {
            LoginActivity()
        }


        signInInstead.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java )
            Toast.makeText(this, "pressed", Toast.LENGTH_LONG)
            startActivity(intent)
        }
    }
    private fun LoginActivity() {
        if(firstName.text.toString().isEmpty()) {
            firstName.error = "Please Enter First Name"
            firstName.requestFocus()
            return
        }

        if(lastName.text.toString().isEmpty()) {
            lastName.error = "Please Enter Last Name"
            lastName.requestFocus()
            return
        }
        if(email.text.toString().isEmpty()) {
            email.error = "Please Enter Email"
            email.requestFocus()
            return
        }

        if(passwordSignUp.text.toString().isEmpty()) {
            passwordSignUp.error = "Please Enter Password"
            passwordSignUp.requestFocus()
            return
        }

        if(confirmPassword.text.toString().isEmpty()) {
            confirmPassword.error = "Please Enter Password"
            confirmPassword.requestFocus()
            return
        }



        mAuth.createUserWithEmailAndPassword(email.text.toString(), passwordSignUp.text.toString()).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                addUserToDB(firstName.text.toString(), lastName.text.toString(), email.text.toString())
                Toast.makeText(baseContext, "Registration Successful, You can log in now",
                    Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(baseContext, "Registration Failed! Try Again",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
     private fun addUserToDB(firstName: String, lastName:  String, email: String){

//         val hashMap = HashMap<String,String>()
//         hashMap.put("firstName", firstName)
//         hashMap.put("lastName", lastName)
//         hashMap.put("email", email)
//         database.child("Users").setValue(hashMap)

    data class User(
        var firstName: String? = "",
        var lastName: String? = "",
        var email: String? = ""
    )
    val user = User(firstName, lastName, email)
    database.child("users").child(mAuth.currentUser?.uid!!).setValue(user)
     }

}
