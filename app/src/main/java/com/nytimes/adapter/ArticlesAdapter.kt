package com.twobasetechnologies.workit.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.newyorktimes.model.Response
import com.nytimes.R
import com.nytimes.interfaces.ReacyclerListener
import kotlinx.android.synthetic.main.content_row.view.*


class ArticlesAdapter(var context: Context, var listener: ReacyclerListener, var contentList:
ArrayList<Response.Result>) :
        RecyclerView
        .Adapter<ArticlesAdapter.ViewHolder>() {

    private val TAG = ArticlesAdapter::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 2 == 0) {
            holder.linlay_main.setBackgroundColor(ContextCompat.getColor(context, R.color.white))

        } else {
            holder.linlay_main.setBackgroundColor(ContextCompat.getColor(context, R.color.offwhite))
        }
        bindView(position, holder)
    }

    private fun bindView(position: Int, holder: ViewHolder) {
        val content = contentList[position]
        holder.txtHeader.text = content.title
        holder.date.text = content.published_date
        holder.source.text = content.source
        holder.byLine.text = content.byline

        Glide.with(context).load(content.media!![0].media_metadata!![0].url)
                .into(holder.img_article_icon)

        holder.linlay_main.setOnClickListener { listener.onItemClicked(position) }

    }

    override fun getItemCount(): Int {
        return contentList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtHeader = itemView.title!!
        var date = itemView.date!!
        var img_article_icon = itemView.img_article_icon!!
        var source = itemView.source!!
        var byLine = itemView.byLine!!
        var linlay_main = itemView.linlay_main!!
    }

}





