package com.akash.roomdatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                AGE_COL + " TEXT" + ")")
        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
     }
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }// This method is for adding data in our database
    fun addName(name : String, age : String ){
        // below we are creating
        // a content values variable
        val values = ContentValues()
        // we are inserting our values // in the form of key-value pair
        values.put(NAME_COl, name)
        values.put(AGE_COL, age)
        // here we are creating a // writable variable of // our database as we want to
        // insert value in our database
        val db = this.writableDatabase
        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)
        // at last we are
        // closing our database
        db.close()
    }
    // below method is to get
    // all data from our database
    fun getName(): Cursor? {
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase
        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " +
                TABLE_NAME ,null)
        //return db.rawQuery("SELECT * FROM " +
                       //TABLE_NAME+"WHERE AGE>=50",null)
    }

    // below is the method for updating our courses
    fun updateCourse(
        name: String, age: String?
    ) {
        // calling a method to get writable database.
        val db = this.writableDatabase
        val values = ContentValues()
        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COl, name)
        values.put(AGE_COL, age)
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, values, "NAME=?", arrayOf(name))
        db.close()
    }
    fun deleteDetail(age: String?)
    {
        // calling a method to get writable database.
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "AGE=?", arrayOf(age))
        db.close()
    }
    companion object{
        // here we have defined variables for our database
        // below is variable for database name
        private val DATABASE_NAME = "CSE226"
        // below is the variable for database version
        private val DATABASE_VERSION = 1
        // below is the variable for table name
        val TABLE_NAME = "my_table1"
        // below is the variable for id column
        val ID_COL = "id"
        // below is the variable for name column
        val NAME_COl = "name"
        // below is the variable for age column
        val AGE_COL = "age"
    }
}

