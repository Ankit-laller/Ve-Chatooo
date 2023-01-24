package com.example.vechatooo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.jar.Attributes.Name

class adapter(val context: Context,private val userList:ArrayList<Users>)
    :RecyclerView.Adapter<adapter.ViewHolder>(){

//    private lateinit var mListener :onItemClickListener
//
//    interface onItemClickListener {
//        fun onItemClick(position: Int)
//    }
//    fun setOnClickListener2(clickListener:onItemClickListener){
//        mListener = clickListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.users,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = userList[position]
        holder.Name.text =current.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name",current.name)
            intent.putExtra("userId", current.userId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val Name :TextView= itemView.findViewById(R.id.userTv)
//        init {
//            itemView.setOnClickListener{
//                clickListener.onItemClick(adapterPosition)
//        }
    }
}