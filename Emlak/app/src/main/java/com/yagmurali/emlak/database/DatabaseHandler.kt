package com.yagmurali.emlak.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.yagmurali.emlak.models.RealEstateModel

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "RealEstateDatabase"
        private const val TABLE_REAL_ESTATE = "RealEstateTable"

        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_REAL_ESTATE = ("CREATE TABLE " + TABLE_REAL_ESTATE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)")
        db?.execSQL(CREATE_REAL_ESTATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_REAL_ESTATE")
        onCreate(db)
    }

    fun addRealEstate(realestate: RealEstateModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, realestate.title)
        contentValues.put(KEY_IMAGE, realestate.image)
        contentValues.put(
            KEY_DESCRIPTION,
            realestate.description
        )
        contentValues.put(KEY_DATE, realestate.date)
        contentValues.put(KEY_LOCATION, realestate.location)
        contentValues.put(KEY_LATITUDE, realestate.latitude)
        contentValues.put(KEY_LONGITUDE, realestate.longitude)

        // Inserting Row
        val result = db.insert(TABLE_REAL_ESTATE, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return result
    }

    fun updateRealEstate(realestate: RealEstateModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, realestate.title)
        contentValues.put(KEY_IMAGE, realestate.image)
        contentValues.put(
            KEY_DESCRIPTION,
            realestate.description
        )
        contentValues.put(KEY_DATE, realestate.date)
        contentValues.put(KEY_LOCATION, realestate.location)
        contentValues.put(KEY_LATITUDE, realestate.latitude)
        contentValues.put(KEY_LONGITUDE, realestate.longitude)

        // Inserting Row
        val result = db.update(TABLE_REAL_ESTATE, contentValues, KEY_ID +" = " + realestate.id, null)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return result
    }

    @SuppressLint("Range")
    fun getRealEstateList(): ArrayList<RealEstateModel> {

        // A list is initialize using the data model class in which we will add the values from cursor.
        val list: ArrayList<RealEstateModel> = ArrayList()

        val selectQuery = "SELECT  * FROM $TABLE_REAL_ESTATE" // Database select query

        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val place = RealEstateModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID) ),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE))
                    )
                    list.add(place)

                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return list
    }
}
// END