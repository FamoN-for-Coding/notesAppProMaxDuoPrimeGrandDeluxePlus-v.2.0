package com.example.notesapppromaxdueprimegrandplusv20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.database.*

import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_notes.*
import android.content.Context.INPUT_METHOD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class notes : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val firstName =
                    dataSnapshot.child("users").child(mAuth.currentUser?.uid!!).child("firstName")
                        .value.toString().capitalize()

                val postTitle =
                    dataSnapshot.child("users").child(mAuth.currentUser?.uid!!).child("note")
                        .child("title").value.toString()
                val postBody =
                    dataSnapshot.child("users").child(mAuth.currentUser?.uid!!).child("note")
                        .child("body").value.toString()

                greeting.text = "Hello, $firstName"
                if (dataSnapshot.child("users").child(mAuth.currentUser?.uid!!).hasChild("note")) {
                    noteTitle.setText(postTitle)

                    noteBody.setText(postBody)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.w("loadPost:onCancelled", databaseError.toException())

            }


        })


        setContentView(R.layout.activity_notes)


        logout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        editButton.setOnClickListener {
            enableEditing()
            noteBody.requestFocus()
        }

        saveButton.setOnClickListener {
            disableEditing()
            addNoteToDB(noteTitle.text.toString(), noteBody.text.toString())
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT)
        }


        noteTitle.isEnabled = false
        noteBody.isEnabled = false


    }


    private fun enableEditing() {
        noteTitle.isEnabled = true
        noteBody.isEnabled = true
        Toast.makeText(this, "Edit Now", Toast.LENGTH_SHORT).show()
//        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(windowToken, 0)

    }

    private fun disableEditing() {
        noteTitle.isEnabled = false
        noteBody.isEnabled = false
    }

    private fun addNoteToDB(title: String, body: String) {
        val hashMap = HashMap<String, String>()
        hashMap.put("title", title)
        hashMap.put("body", body)

        database.child("users").child(mAuth.currentUser?.uid!!).child("note").setValue(hashMap)
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()


    }

}
