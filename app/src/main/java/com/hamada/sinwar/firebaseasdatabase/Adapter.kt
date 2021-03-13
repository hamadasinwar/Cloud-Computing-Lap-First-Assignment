package com.hamada.sinwar.firebaseasdatabase

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val activity: Activity, private val data:MutableList<Model>, private val onClickItem: OnClickItem)
    :RecyclerView.Adapter<Adapter.MyViewHolder>() {

    inner class MyViewHolder(item: View): RecyclerView.ViewHolder(item){
        val name: TextView = item.findViewById(R.id.name)
        val number: TextView = item.findViewById(R.id.number)
        val address: TextView = item.findViewById(R.id.address)
        val card: CardView = item.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.number.text = data[position].number.toString()
        holder.address.text = data[position].address
        holder.card.setOnClickListener {
            onClickItem.onClick(position)
        }
    }

    interface OnClickItem{
        fun onClick(position: Int)
    }

}