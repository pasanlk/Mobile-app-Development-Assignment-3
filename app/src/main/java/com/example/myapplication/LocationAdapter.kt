package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>(){

    private var locList : ArrayList<LocationModel> = ArrayList()
    private var onClickItem:((LocationModel)-> Unit)? = null
    private var onClickDeleteItem:((LocationModel)-> Unit)? = null

    fun addItems(items:ArrayList<LocationModel>)
    {
        this.locList=items
        notifyDataSetChanged()
    }


    fun setOnClickItem(callback: (LocationModel)->Unit)
    {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (LocationModel)->Unit)
    {
        this.onClickDeleteItem= callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= LocationViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.location_list_loc,parent,false)
    )
    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val loc= locList[position]
        holder.bindView(loc)
        holder.itemView.setOnClickListener { onClickItem?.invoke(loc)}
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(loc)}
    }

    override fun getItemCount(): Int {
        return locList.size
    }

    class LocationViewHolder(var view: View):RecyclerView.ViewHolder(view)
    {
        private var id=view.findViewById<TextView>(R.id.tvId)
        private var loc=view.findViewById<TextView>(R.id.tvLocation)
        private var long=view.findViewById<TextView>(R.id.tvLongitude)
        private var lati=view.findViewById<TextView>(R.id.tvLatitude)
        var btnDelete=view.findViewById<Button>(R.id.btnDelete)


        fun bindView(locationModel: LocationModel)
        {
            id.text=locationModel.id.toString()
            loc.text=locationModel.loc
            long.text=locationModel.longitude
            lati.text=locationModel.latitude


        }
    }
}