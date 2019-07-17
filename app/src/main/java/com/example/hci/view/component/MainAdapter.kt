package com.example.hci.view.component

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.view.model.ItemDataModel

class MainAdapter: RecyclerView.Adapter<GenericViewHolder>(){
    private var data:MutableList<ItemDataModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        return GenericViewHolder.createViewHolder(parent,viewType,parent.context) ?:
                throw NullPointerException("viewType not maintained in creator (GenericViewHolder.createViewHolder")

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if(data[position].type == "product_list")
            R.layout.grid_view_holder
        else
            R.layout.article_view_holder
    }

    fun setItems(newData:List<ItemDataModel>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

}