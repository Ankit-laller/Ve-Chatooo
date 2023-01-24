package com.example.vechatooo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.example.vechatooo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button2.setOnClickListener {
            val intent = Intent(this,SignUp::class.java )
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        binding.Loginbutton.setOnClickListener{
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.password.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        var intent = Intent(this, HomePage::class.java)
                        finish()
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "Either email or password is wrong", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            else{
                Toast.makeText(this, "Can not live blank email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}