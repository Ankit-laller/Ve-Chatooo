package com.example.vechatooo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class msgAdapter(val context: Context, val msgList:ArrayList<Message>):RecyclerView.Adapter<ViewHolder>() {

    val itemReceive = 1
    val itemSent = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 1){
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recieve,parent,false)
            return receiveViewHolder(itemView)
        }else{
            val itemView =LayoutInflater.from(parent.context).inflate(R.layout.send,parent,false)
            return sentViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMsg = msgList[position]
        if(holder.javaClass == sentViewHolder::class.java){

            val viewHolder = holder as sentViewHolder
            viewHolder.sentTv.text = currentMsg.message
        }else{
            // receive view holder work
            val viewHolder = holder as receiveViewHolder
            viewHolder.receiveTv.text = currentMsg.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMsg = msgList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMsg.senderId)){
            return itemSent
        }else{
            return itemReceive
        }
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    class sentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentTv = itemView.findViewById<TextView>(R.id.sentMsg)
    }

    class receiveViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val receiveTv = itemView.findViewById<TextView>(R.id.receiveMsg)
    }
}