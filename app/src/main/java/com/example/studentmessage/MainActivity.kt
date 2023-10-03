package com.example.studentmessage

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmessage.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setUpActionBar()
        val database = Firebase.database
        val myRef = database.getReference("message")
        binding.button2.setOnClickListener{
            myRef.setValue(binding.edMsg.text.toString())
        }
        onChangeListener(myRef)

    }
    private fun onChangeListener(dRef: DatabaseReference){
        dRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.rcView.append("\n")
                binding.rcView.append("Sayat: ${snapshot.value.toString()}")
            }


            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setUpActionBar(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val ab = supportActionBar
        Thread{
            val bMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val dIcon = BitmapDrawable(resources, bMap)
            runOnUiThread{
                ab?.setDisplayHomeAsUpEnabled(true)
                ab?.setHomeAsUpIndicator(dIcon)
                ab?.title = auth.currentUser?.displayName
            }

        }.start()
    }
}