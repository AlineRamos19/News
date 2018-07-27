package com.aline.android.news.adapter

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aline.android.news.R
import com.aline.android.news.activity.OpenListenerActivity
import com.aline.android.news.models.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*


class AdapterNews(val context: Context, val listNews: List<Article>?) : RecyclerView.Adapter<AdapterNews.ViewHolderNews>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNews {

        val view = LayoutInflater.from(context).inflate(R.layout.row_news, parent, false)
        return ViewHolderNews(view)
    }

    override fun getItemCount(): Int {
        return listNews!!.size
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolderNews, position: Int) {

        val list = listNews!![position]
        val dateApi = list.publishedAt
        val sdf1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.forLanguageTag("pt"))
        val sdf2 = SimpleDateFormat("dd-MM-yyyy hh:mma")
        holder.dateNews.text = sdf2.format(sdf1.parse(dateApi))

        holder.authorNews.text = list.author?.toString() ?: "Fonte Desconhecida"
        holder.titleNews.text = list.title
        holder.descriptionNews.text = list.description?.toString() ?: "Descrição não disponível"

        val options =
                RequestOptions()
                        .centerCrop()
                        .error(R.drawable.ic_no_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
        Glide.with(context).load(list.urlToImage).apply(options).into(holder.imageNews)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, OpenListenerActivity::class.java)
            intent.putExtra("urlWebView", list.url)
            holder.itemView.context.startActivity(intent)
        }

    }


    class ViewHolderNews(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val dateNews = itemView!!.findViewById<TextView>(R.id.date_new)
        val authorNews = itemView!!.findViewById<TextView>(R.id.author_new)
        val titleNews = itemView!!.findViewById<TextView>(R.id.title_new)
        val descriptionNews = itemView!!.findViewById<TextView>(R.id.description_new)
        val imageNews = itemView!!.findViewById<ImageView>(R.id.image_new)

    }
}