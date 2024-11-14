package com.example.projectpdm

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class listaRecyclerAdapter(
    private val context: Context,
    private val listaModel:ArrayList<listaModel> = ArrayList()
) : RecyclerView.Adapter<listaRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): listaRecyclerAdapter.MyViewHolder {
        //Log.d("onCreateViewHolder", "onCreateViewHolder")
        val inflater:LayoutInflater  = LayoutInflater.from(context)
        val view:View = inflater.inflate(R.layout.recyclerview,parent,false)

        return MyViewHolder(view)
    }

    //por nas textViews o nome, a data e o preco de cada lista
    override fun onBindViewHolder(holder: listaRecyclerAdapter.MyViewHolder, position: Int) {
        //Log.d("onBindViewHolder", "onBindViewHolder $position")
        holder.name.text = listaModel[position].nome
        holder.date.text = listaModel[position].data
        holder.price.text = listaModel[position].price.toString()
    }

    //return ao n de listas q estao na db
    override fun getItemCount(): Int {
        Log.d("getItemCount","${listaModel.size}")
        return listaModel.size
    }

    // associar os textViews do xml com o viewHolder
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name= itemView.findViewById<TextView >(R.id.name)
        val date = itemView.findViewById<TextView >(R.id.date)
        val price= itemView.findViewById<TextView >(R.id.price)
    }
}