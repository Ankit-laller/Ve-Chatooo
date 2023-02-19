package com.example.vechatooo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vechatooo.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity(){
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef : DatabaseReference
//    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("User")
        binding.signUpButton.setOnClickListener{
            val email = binding.etemail.text.toString()
            val pass = binding.etpass.text.toString()
            val pass2 = binding.etpass2.text.toString()
            val name = binding.etName.text.toString()
//            sharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE)
//
//            if(sharedPreferences.getString("username"," ")!=" "){
//                val intent = Intent(this, HomePage::class.java)
//                startActivity(intent)
//            }

            loginFunction(email, pass, name,pass2)


        }
    }


    private fun loginFunction(email:String, pass:String, name:String, pass2:String){
        if(email.isNotEmpty() && pass.isNotEmpty() && pass2.isNotEmpty()){
            if(pass==pass2){
                if(pass.length>5){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if(it.isSuccessful){
//                            val editor = sharedPreferences.edit()
//                            editor.putString("username", name)
//                            editor.commit()
                            insertData(name, email,pass,firebaseAuth.uid)
                            val intent = Intent(this, MainActivity::class.java)
                            finish()
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            print(it.exception.toString())
                        }
                    }
                }
            }else{
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Don't leave anything empty", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertData(name:String?, email:String?,pass:String?, uid:String?){

        var userData =Users(name,email,pass,uid)
        dbRef.child(uid!!).setValue(userData).addOnCompleteListener{
            Toast.makeText(this,"Done:)",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {err->
            Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }
}