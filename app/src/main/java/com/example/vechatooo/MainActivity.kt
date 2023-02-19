package com.example.vechatooo

import android.content.Context
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
            finish()
        }
        check()

        firebaseAuth = FirebaseAuth.getInstance()
        binding.Loginbutton.setOnClickListener{
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.password.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        var intent = Intent(this, HomePage::class.java)
                         var sharedPreferences = getSharedPreferences("App", Context.MODE_PRIVATE)
                        var editor = sharedPreferences.edit()
                        editor.putString("login","true")
                        editor.apply()
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
    private fun check(){
         var sharedPreferences = getSharedPreferences("App", Context.MODE_PRIVATE)
        var check = sharedPreferences.getString("login","")
        if(check.equals("true")){
            startActivity(Intent(this,HomePage::class.java))
            finish()
        }

    }
}