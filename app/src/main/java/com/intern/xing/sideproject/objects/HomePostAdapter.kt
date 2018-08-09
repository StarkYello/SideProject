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


class HomePostAdapter(var context: Context, var listOfPost:List<Post>, var navController: NavController): RecyclerView.Adapter<HomePostAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.home_post_title)
        var owner: TextView = view.findViewById(R.id.home_post_owner)
        var description: TextView = view.findViewById(R.id.post_home_description)
        var timeStamp: TextView = view.findViewById(R.id.home_post_timestamp)
        var tagList:LinearLayout = view.findViewById(R.id.tag_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.home_post, parent, false))
    }
    override fun getItemCount(): Int {
        return listOfPost.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var post:Post=listOfPost[position]
        if(post.timestamp!=null){
            holder.timeStamp.text= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(post.timestamp!!))
        }
        holder.title.text=post.postTitle
        holder.owner.text="By: ${post.ownerName}"
        holder.description.text=post.postDescription
        var i=0
        holder.tagList.removeAllViews()
        while(i<3&&i<post.postTags!!.size){
            if(post.postTags!![i]!=null){
                var tagText=LayoutInflater.from(context).inflate(R.layout.home_post_sample,null) as TextView
//                tagText.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT).
                var params=LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(0,0,20,0)
                tagText.layoutParams=params
                tagText.text= post.postTags!![i]
                holder.tagList.addView(tagText)
            }else{
                break
            }
            i++
        }
        holder.itemView.setOnClickListener{
            var bundle: Bundle =bundleOf(
                    "postUID" to post.postUID
            )
            navController.navigate(R.id.action_homeScreen_to_postScreen,bundle)
        }
    }

}