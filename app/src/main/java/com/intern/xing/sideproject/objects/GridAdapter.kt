package com.intern.xing.sideproject.objects

import android.app.Activity
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.intern.xing.sideproject.R
import com.intern.xing.sideproject.R.id.continue_btn
import com.intern.xing.sideproject.R.id.tag_text
import com.intern.xing.sideproject.TagScreen
import com.intern.xing.sideproject.ui.main.MainViewModel

class GridAdapter(  var context: Context?=null,
                    var list: MutableList<String>?=null,
                    var view: View?=null,
                    var button:View?=null,
                    var layoutInflater:LayoutInflater?=null
) : BaseAdapter()  {
    private var activitedCount=0


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        layoutInflater= LayoutInflater.from(context)
        val view=layoutInflater!!.inflate(R.layout.tag_sample,null)
        var text=view.findViewById<TextView>(R.id.tag_text)
        text.text= list!![position]
        var card=view.findViewById<CardView>(R.id.border)
        card.setOnClickListener {
            if(!card.isActivated){
                card.setCardBackgroundColor(context!!.resources.getColor(R.color.black))
                card.isActivated=true
                activitedCount++

            }else{
                card.setCardBackgroundColor(context!!.resources.getColor(R.color.android_transparent))
                card.isActivated=false
                activitedCount--

            }
            if(activitedCount>0){
                button!!.visibility=View.VISIBLE
            }
            else{
                button!!.visibility=View.INVISIBLE
            }
        }

        return card!!
    }

    override fun getItem(position: Int): Any {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list!!.size
    }



}