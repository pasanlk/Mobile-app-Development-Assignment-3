package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper (context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    companion object{

        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="locations.db"
        private const val TBL_LOCATION="tbl_location"
        private const val ID="id"
        private const val LOCATION="loc"
        private const val LONG="longitude"
        private const val LATI="latitude"



    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblLocation =("CREATE TABLE " + TBL_LOCATION + "( "
                + ID + " INTEGER PRIMARY KEY," + LOCATION + " TEXT, " + LONG + " TEXT, " + LATI + " TEXT "+ ")")

        db?.execSQL(createTblLocation)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_LOCATION")
        onCreate(db)
    }

    fun insertLocation(loc :LocationModel): Long {

        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID,loc.id)
        contentValues.put(LOCATION,loc.loc)
        contentValues.put(LONG,loc.longitude)
        contentValues.put(LATI,loc.latitude)

        val success = db.insert(TBL_LOCATION,null,contentValues)
        db.close()
        return success

    }

    @SuppressLint("Range", "Recycle")
    fun getAllLocations():ArrayList<LocationModel>
    {

        val locList : ArrayList<LocationModel> = ArrayList()
        val selectQuery="SELECT * FROM $TBL_LOCATION "

        val db=this.readableDatabase
        val cursor : Cursor?


        try {

            cursor = db.rawQuery(selectQuery,null)
        }
        catch (e:Exception)
        {

            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var loc:String
        var longitude:String
        var latitude:String

        if(cursor.moveToFirst())
        {
            do {
                id=cursor.getInt(cursor.getColumnIndex("id"))
                loc=cursor.getString(cursor.getColumnIndex("loc"))
                latitude=cursor.getString(cursor.getColumnIndex("latitude"))
                longitude=cursor.getString(cursor.getColumnIndex("longitude"))

                val lo=LocationModel(id=id,loc=loc,latitude=latitude,longitude = longitude)
                locList.add(lo)

            }

            while (cursor.moveToNext())

        }

        return locList
    }

    fun deleteLocationById(id:Int): Int {
        val db=this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,id)

        val success = db.delete(TBL_LOCATION,"id=$id",null)
        db.close()
        return success
    }

}