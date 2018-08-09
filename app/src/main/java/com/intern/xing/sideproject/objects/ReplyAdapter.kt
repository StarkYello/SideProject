package com.intern.xing.sideproject.objects

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import com.intern.xing.sideproject.R
import java.text.SimpleDateFormat
import java.util.*
import androidx.navigation.NavController


class ReplyAdapter(var context: Context, var listOfReplies:List<Reply>, var navController: NavController): RecyclerView.Adapter<ReplyAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var owner: TextView = view.findViewById(R.id.reply_owner)
        var description: TextView = view.findViewById(R.id.reply_description)
        var timeStamp: TextView = view.findViewById(R.id.reply_timestamp)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.reply_sample, parent, false))
    }
    override fun getItemCount(): Int {
//        Collections.sort(listOfReplies)
        return listOfReplies.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var reply:Reply=listOfReplies[position]
        if(reply.timestamp!=null){
        holder.timeStamp.text= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(reply.timestamp!!))}
        holder.owner.text="${reply.username}"
        holder.description.text=reply.replyText

    }

}