package com.gaelectronica.infotrack_prueba.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaelectronica.infotrack_prueba.R
import com.gaelectronica.infotrack_prueba.SecondActivity
import com.gaelectronica.infotrack_prueba.model.User

class UserListAdapter (private val list: ArrayList<User>, private val context: Context):
    RecyclerView.Adapter<UserListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_user, parent, false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder != null){
            holder.bindView(list[position])
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name = itemView.findViewById<TextView>(R.id.txtName)
        var email = itemView.findViewById<TextView>(R.id.txtEmail)

        fun bindView(user:User) {
            name.text = user.name
            email.text = user.email

            itemView.setOnClickListener {
                var intent = Intent(context, SecondActivity::class.java )
                intent.putExtra("name", user.name)
                context.startActivity(intent)
            }
        }
    }
}