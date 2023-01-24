package com.example.vechatooo

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.System.err
import kotlin.math.log

class HomePage : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var dbref : DatabaseReference
    private lateinit var userList :ArrayList<Users>
    private lateinit var userName :Array<String>
    private lateinit var userPass :Array<String>
    private lateinit var mAdapter: adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)


        firebaseAuth = FirebaseAuth.getInstance()
        userRecyclerView = findViewById(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.hasFixedSize()
        userList = arrayListOf()
//
//        userName = arrayOf(
//            "Ankit","Manish"
//        )
//        userPass = arrayOf(
//            "12","123"
//        )

        getUserList()
         mAdapter = adapter(this,userList)
        mAdapter.notifyDataSetChanged()

//        for (i in userName.indices){
//            var data = Users(userName[i],userPass[i])
//            userList.add(data)
//        }
//

    }
    private fun getUserList() {
        dbref = FirebaseDatabase.getInstance().getReference("User")
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if(snapshot.exists()){
                    for (snaps in snapshot.children){
                        Log.d("BCCCCC","$snaps")
                        var userData = snaps.getValue(Users::class.java)
                        if(firebaseAuth?.currentUser?.email !=userData?.userId ){
                            userList.add(userData!!)
                        }
                        userRecyclerView.adapter = mAdapter

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.logoutBtn){
            firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}
