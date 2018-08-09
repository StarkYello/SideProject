package com.intern.xing.sideproject.objects

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import com.intern.xing.sideproject.R
import java.text.SimpleDateFormat
import java.util.*
import androidx.navigation.NavController
import com.intern.xing.sideproject.PostScreen


class SolutionAdapter(var context: Context, var listOfSolutions:List<Solution>?, var navController: NavController, var postUID:String, var post:Post): RecyclerView.Adapter<SolutionAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var owner: TextView = view.findViewById(R.id.post_solution_owner)
        var description: TextView = view.findViewById(R.id.post_solution_description)
        var timeStamp: TextView = view.findViewById(R.id.post_solution_timestamp)
        var replies: TextView = view.findViewById(R.id.post_solution_replies)
        var score:TextView=view.findViewById(R.id.post_solution_score)
        var upvote:ImageButton=view.findViewById(R.id.up_vote)
        var downvote:ImageButton=view.findViewById(R.id.down_vote)
        var check: ImageView = view.findViewById(R.id.post_solution_check)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.solution_sample, parent, false))
    }
    override fun getItemCount(): Int {
        return listOfSolutions!!.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var solution:Solution=listOfSolutions!![position]
        if(solution.timestamp!=null){
        holder.timeStamp.text= SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(solution.timestamp!!))}
        holder.owner.text="${solution.ownerName}"
        holder.description.text=solution.solution
        if(solution.replies!=null){
            holder.replies.text="${solution.replies!!.size} Replies"}
        else{
            holder.replies.text="0 Replies"}
        holder.replies.paintFlags = holder.replies.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        holder.score.text=solution.score.toString()
        if(solution.score!=0){
            if(!solution.scorers!!.containsKey(PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid)){
                holder.downvote.setImageResource(R.drawable.gray_down_vote)
                holder.upvote.setImageResource(R.drawable.gray_up_vote)
            }else{
                if(solution.scorers!![PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid]==1){
                    holder.upvote.setImageResource(R.drawable.upvote_icon)
                    holder.downvote.setImageResource(R.drawable.gray_down_vote)
                }
                else if(solution.scorers!![PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid]==-1){
                    holder.downvote.setImageResource(R.drawable.downvote_icon)
                    holder.upvote.setImageResource(R.drawable.gray_up_vote)
                }
            }
        }

        holder.upvote.setOnClickListener {
            holder.upvote.setOnClickListener(null)
            if(solution.score==0){
                PostScreen.viewModel.fireBaseUtils.updateScorers(postUID,solution.ownerUID!!,PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid,1)
                PostScreen.viewModel.fireBaseUtils.incrementScore(postUID,solution.ownerUID!!,1)
            }
            else {
                if (!solution.scorers!!.containsKey(PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid)) {
                    PostScreen.viewModel.fireBaseUtils.updateScorers(postUID, solution.ownerUID!!, PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid, 1)
                    PostScreen.viewModel.fireBaseUtils.incrementScore(postUID, solution.ownerUID!!, 1)
                } else {
                    if (solution.scorers!![PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid] != 1) {
                        PostScreen.viewModel.fireBaseUtils.updateScorers(postUID, solution.ownerUID!!, PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid, 1)
                        PostScreen.viewModel.fireBaseUtils.incrementScore(postUID, solution.ownerUID!!, 2)
                    }
                }
            }
        }
        holder.downvote.setOnClickListener {
            holder.downvote.setOnClickListener(null)
            if(solution.score==0){
                PostScreen.viewModel.fireBaseUtils.updateScorers(postUID, solution.ownerUID!!, PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid, -1)
                PostScreen.viewModel.fireBaseUtils.decrementScore(postUID, solution.ownerUID!!, 1)
            }
            else {
                if (!solution.scorers!!.containsKey(PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid)) {
                    PostScreen.viewModel.fireBaseUtils.updateScorers(postUID, solution.ownerUID!!, PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid, -1)
                    PostScreen.viewModel.fireBaseUtils.decrementScore(postUID, solution.ownerUID!!, 1)
                } else {
                    if (solution.scorers!![PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid] != -1) {
                        PostScreen.viewModel.fireBaseUtils.updateScorers(postUID, solution.ownerUID!!, PostScreen.viewModel.fireBaseUtils.mAuth.currentUser!!.uid, -1)
                        PostScreen.viewModel.fireBaseUtils.decrementScore(postUID, solution.ownerUID!!, 2)
                    }
                }
            }
        }
        holder.replies.setOnClickListener {
            var bundle:Bundle=bundleOf(
                    "postUID" to postUID,
                    "solutionUID" to solution.ownerUID)
            navController!!.navigate(R.id.action_postScreen_to_replyPopup,bundle)
        }

    }
//        holder.itemView.setOnClickListener{
//            var bundle: Bundle =bundleOf(
//                    "postUID" to post.uid
//            )
//            navController.navigate(R.id.action_homeScreen_to_postScreen,bundle)
//        }
//    }

}