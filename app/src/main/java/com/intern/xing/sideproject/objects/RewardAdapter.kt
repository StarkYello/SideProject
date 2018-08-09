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



class RewardAdapter(var context: Context, var listOfDrawable:List<Drawable>, var navController: NavController): RecyclerView.Adapter<RewardAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var description: TextView = view.findViewById(R.id.reward_description)
        var point: TextView = view.findViewById(R.id.reward_points)
        var picture: ImageView =view.findViewById(R.id.reward_picture)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.reward_sample, parent, false))
    }
    override fun getItemCount(): Int {
        return listOfDrawable.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var drawable:Drawable=listOfDrawable[position]
        if(position==0) {
            holder.description.text ="Target $25\nGift Card"
            holder.point.text="150 Points"
            holder.picture.setImageDrawable(drawable)
        }
        if(position==1) {
            holder.description.text ="VIP Parking\nSpace"
            holder.point.text="300 Points"
            holder.picture.setImageDrawable(drawable)
        }
        if(position==2) {
            holder.description.text ="25 Best Buy\nGift Card"
            holder.point.text="150 Points"
            holder.picture.setImageDrawable(drawable)
        }
        if(position==3) {
            holder.description.text ="Starbucks $15\nGift Card"
            holder.point.text="100 Points"
            holder.picture.setImageDrawable(drawable)
        }

    }

}