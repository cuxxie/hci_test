package com.example.hci.view.component

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.view.model.ItemDataModel


abstract class GenericViewHolder(view:View):RecyclerView.ViewHolder(view){
    abstract fun bind(data:ItemDataModel)

    companion object {
        fun createViewHolder(parent:ViewGroup, viewType:Int, context:Context):GenericViewHolder?{
            val view = LayoutInflater.from(context).inflate(viewType, parent, false)
            if(viewType == R.layout.article_view_holder) {
                return CardListViewHolder(view)
            }
            else if(viewType == R.layout.grid_view_holder) {
                return GridButtonViewHolder(view)
            }
            return null
        }
    }
}