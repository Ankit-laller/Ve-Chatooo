package com.example.vechatooo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var msgRecyclerView:RecyclerView
    private lateinit var msgTv : EditText
    private lateinit var sendBtn :ImageButton
    private lateinit var msgList :ArrayList<Message>
    private lateinit var dbref :DatabaseReference
    private lateinit var mAdapter: msgAdapter
    private  var senderRoom :String? =null
    private var receiverRoom :String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverId = intent.getStringExtra("userId")
        supportActionBar?.title =name

        msgRecyclerView = findViewById(R.id.msgRecyclerView)
        msgTv = findViewById(R.id.msgEt)
        sendBtn = findViewById(R.id.sendBtn)

        val senderId = FirebaseAuth.getInstance().currentUser?.email
        dbref = FirebaseDatabase.getInstance().getReference()
        senderRoom = receiverId + senderId
        receiverRoom= senderRoom + receiverId

        msgList = ArrayList()
        mAdapter = msgAdapter(this, msgList)
        msgRecyclerView.layoutManager = LinearLayoutManager(this)
        msgRecyclerView.adapter = mAdapter

        dbref.child("Chats").child("sender")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    msgList.clear()
                    for(snaps in snapshot.children){
                        val message = snaps.getValue(Message::class.java)
                        msgList.add(message!!)
                    }
                        mAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        sendBtn.setOnClickListener {

            val msg = msgTv.text.toString()
            val msgObject = Message(msg,senderId)
            val userId =dbref.push().key!!
            dbref.child("Chats").child("sender").child(userId)
                .setValue(msgObject).addOnSuccessListener {
                    dbref.child("Chats").child("receiver").push()
                        .setValue(msgObject).addOnCanceledListener {
                            Toast.makeText(this,"msg sent:)", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {err->
                            Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            msgTv.text.clear()
        }



    }
}