package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edlocation: EditText
    private lateinit var edlong:EditText
    private lateinit var edlati:EditText

    private lateinit var btnAdd: Button
    private lateinit var btnView:Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : LocationAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecycleView()
        sqLiteHelper= SQLiteHelper(this)
        btnAdd.setOnClickListener { addLocation() }
        btnView.setOnClickListener { getLocations() }
        adapter?.setOnClickItem { Toast.makeText(this, it.loc, Toast.LENGTH_SHORT).show() }

        adapter?.setOnClickDeleteItem {
            deleteLocation(it.id)
        }
    }



    private fun getLocations() {
        val locList=sqLiteHelper.getAllLocations()
        Log.e("pppp", "${locList.size}")

        adapter?.addItems(locList)
    }

    private fun addLocation()
    {
        val loc = edlocation.text.toString()
        val longitude = edlong.text.toString()
        val latitude = edlati.text.toString()

        if(loc.isEmpty()||longitude.isEmpty()||latitude.isEmpty())
        {

            Toast.makeText(this,"Please enter fields",Toast.LENGTH_SHORT).show()
        }else
        {

            val loc=LocationModel(loc =loc,longitude = longitude,latitude = latitude)
            val status=sqLiteHelper.insertLocation(loc)

            if(status >-1)
            {
                Toast.makeText(this,"Location Added....",Toast.LENGTH_SHORT).show()
                clearEditText()
            }
            else
            {
                Toast.makeText(this,"Not Added....",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteLocation(id:Int)
    {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure to DELETE ?")
        builder.setCancelable(true)
        builder.setPositiveButton("YES")
        {dialog,_->
            sqLiteHelper.deleteLocationById(id)
            getLocations()

            dialog.dismiss()

        }
        builder.setNegativeButton("No")
        {
                dialog,_->
            dialog.dismiss()

        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText()
    {
        edlocation.setText("")
        edlong.setText("")
        edlati.setText("")

        edlocation.requestFocus()
    }

    private fun initRecycleView()
    {

        recyclerView.layoutManager= LinearLayoutManager(this)
        adapter = LocationAdapter()
        recyclerView.adapter = adapter
    }
    private fun initView()
    {
        edlocation=findViewById(R.id.editLocation)
        edlong=findViewById(R.id.editLongitude)
        edlati=findViewById(R.id.editLatitude)

        btnAdd=findViewById(R.id.btnAdd)
        btnView=findViewById(R.id.btnView)
        recyclerView=findViewById(R.id.recyclerView)
    }
}