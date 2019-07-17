package com.example.hci.view.component

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hci.R
import com.example.hci.util.GlideApp
import com.example.hci.view.model.ItemDataModel

class GridButtonViewHolder(val view: View): GenericViewHolder(view){
    private var adapter:GridButtonsAdapter = GridButtonsAdapter()
    override fun bind(data: ItemDataModel) {
        if(data.child==null) return
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerGrid)
        if(recyclerView.adapter == null) {
            recyclerView.layoutManager = GridLayoutManager(view.context, 3)
            recyclerView.adapter = adapter
        }
        adapter.setItems(data.child)
    }
}

class GridButtonsAdapter : RecyclerView.Adapter<GridItemViewHolder>(){
    private var data:MutableList<ItemDataModel> = mutableListOf()
    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_view_holder, null, false)
        return GridItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setItems(newData:List<ItemDataModel>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}


class GridItemViewHolder(private val view:View): RecyclerView.ViewHolder(view){
    private var url:String = ""
    fun bind(data:ItemDataModel){
        val image = view.findViewById<ImageView>(R.id.image_grid_item)
        val title = view.findViewById<TextView>(R.id.text_grid_item)
        GlideApp
            .with(view.context)
            .load(data.image)
            .fitCenter()
            .into(image)
        title.text = data.title
        url = data.url
        view.setOnClickListener {
            itemClicked()
        }
    }

    private fun itemClicked(){
        val ctx = this.view.context
        if(url.isEmpty()) return
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        ctx.startActivity(i)
    }
}