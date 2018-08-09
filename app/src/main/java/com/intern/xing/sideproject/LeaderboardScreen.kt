package com.intern.xing.sideproject


import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.intern.xing.sideproject.objects.RankingAdapter
import com.intern.xing.sideproject.objects.RewardAdapter
import com.intern.xing.sideproject.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_leaderboard_screen.*
import kotlinx.android.synthetic.main.fragment_post_screen.*
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat






class LeaderboardScreen : Fragment() {
    companion object {
        lateinit var viewModel: MainViewModel
    }
    var navController: NavController?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        navController= Navigation.findNavController(view)
        viewModel.fireBaseUtils.getCurrentPoint()
        viewModel.getCurrentUserScore().observe(this,
                Observer {
                    user_current_point.text=it.toString()
                })
        reward_recycler.layoutManager= LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        reward_recycler.adapter=RewardAdapter(context!!,
                listOf(resources.getDrawable(R.drawable.targetlogo),
                        resources.getDrawable(R.drawable.citilogo),
                        resources.getDrawable(R.drawable.bestbuylogo),
                        resources.getDrawable(R.drawable.starbuckslogo)
                        ),navController!!)
        viewModel.fireBaseUtils.getAllUser()
        viewModel.getAllUser().observe(this,
                Observer {
                    leaderboard_recycler.layoutManager=LinearLayoutManager(activity)
                    leaderboard_recycler.adapter=RankingAdapter(context!!,it,navController!!)
//                    val dividerItemDecoration = DividerItemDecorator(ContextCompat.getDrawable(context!!, R.drawable.divider)!!)
//                    leaderboard_recycler.addItemDecoration(dividerItemDecoration);
                    leaderboard_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

                })

//        NavigationUI.setupWithNavController(bottom_navigation_leaderboard,navController!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}

class DividerItemDecorator(private val mDivider: Drawable) : RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + mDivider.intrinsicHeight

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            mDivider.draw(canvas)
        }
    }
}