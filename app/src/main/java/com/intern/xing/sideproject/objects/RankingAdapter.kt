package com.intern.xing.sideproject.objects


import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.intern.xing.sideproject.R
import java.text.SimpleDateFormat
import java.util.*



class RankingAdapter(var context: Context, var listOfUser:List<User>, var navController: NavController): RecyclerView.Adapter<RankingAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ranking_number: TextView = view.findViewById(R.id.ranking_number)
        var ranking_name: TextView = view.findViewById(R.id.ranking_name)
        var ranking_points: TextView =view.findViewById(R.id.ranking_points)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.leaderboard_ranking_sample, parent, false))
    }
    override fun getItemCount(): Int {
        listOfUser=listOfUser.sortedWith(compareBy {it.totalScore}).reversed()
        return listOfUser.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var user:User=listOfUser[position]
        holder.ranking_name.text=user.username
        holder.ranking_points.text= user.totalScore.toString()
        holder.ranking_number.text= (position+1).toString()
    }

}