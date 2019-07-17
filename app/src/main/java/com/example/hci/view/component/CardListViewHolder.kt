package com.example.hci.view.component

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.hci.R
import com.example.hci.util.GlideApp
import com.example.hci.view.model.ItemDataModel

class CardListViewHolder(val view:View): GenericViewHolder(view){
    private var url:String = ""
    override fun bind(data: ItemDataModel) {
        val image = view.findViewById<ImageView>(R.id.image_article)
        val title = view.findViewById<TextView>(R.id.title_article)
        GlideApp
            .with(view.context)
            .load(data.image)
            .centerCrop()
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