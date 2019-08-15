package com.hamed.sportsticket.adapter


import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamed.sportsticket.DetailsActivity
import com.hamed.sportsticket.Game
import com.hamed.sportsticket.MainActivity
import com.hamed.sportsticket.R

class RecyclerViewAdapter(var gamesList: ArrayList<Game>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = gamesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.logo1.setImageResource(gamesList[position].logo1)
        holder.logo2.setImageResource(gamesList[position].logo2)
        holder.team1.text = gamesList[position].team1
        holder.team2.text = gamesList[position].team2
        holder.logoVS.setImageResource(R.drawable.vs)
        holder.itemView.setOnClickListener{
            var intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){


        val logoVS: ImageView = view.findViewById(R.id.imageViewVS)
        val logo1: ImageView = view.findViewById(R.id.imageViewLogo1)
        val logo2: ImageView = view.findViewById(R.id.imageViewLogo2)
        val team1: TextView = view.findViewById(R.id.textView1)
        val team2: TextView = view.findViewById(R.id.textView2)
    }
}