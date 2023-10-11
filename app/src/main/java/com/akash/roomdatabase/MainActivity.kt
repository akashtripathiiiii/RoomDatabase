package com.akash.roomdatabase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addName: Button = findViewById(R.id.addName)
        val updateName: Button =findViewById(R.id.button)
        val deleteDetail: Button =findViewById(R.id.delete)
        val printName: Button = findViewById(R.id.printName)
        val enterName: EditText = findViewById(R.id.enterName)
        val enterAge: EditText = findViewById(R.id.enterAge)
        val Name: TextView = findViewById(R.id.Name)
        val Age: TextView = findViewById(R.id.Age)
        addName.setOnClickListener{
            // below we have created
            // a new DBHelper class,
            // and passed context to it
            val db = DBHelper(this, null)
            // creating variables for values
            // in name and age edit texts
            val name = enterName.text.toString()
            val age = enterAge.text.toString()
            // calling method to add
            // name to our database
            db.addName(name, age)
            Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()
            // at last, clearing edit texts
            enterName.text.clear()
            enterAge.text.clear()
        }

        // below code is to add on click
        // listener to our print name button
        printName.setOnClickListener{
            val db = DBHelper(this, null)
            val cursor = db.getName()
            Name.text="Name\n\n"
            Age.text="Age\n\n"
            cursor!!.moveToFirst()
            Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl))
                    + "\n")
            Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL))
                    + "\n")
            while(cursor.moveToNext()){
                Name.append(cursor.getString(cursor.getColumnIndex
                    (DBHelper.NAME_COl)) + "\n")
                Age.append(cursor.getString
                    (cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")
            }
            cursor.close()
        }
        updateName.setOnClickListener {
            val dbHandler = DBHelper(this, null)
            // inside this method we are calling an update course
            // method and passing all our edit text values.
            dbHandler.updateCourse(
                enterName.getText().toString(),
                enterAge.getText().toString())
            // displaying a toast message that our course has been updated.
            Toast.makeText(
                this@MainActivity,
                "Course Updated..",
                Toast.LENGTH_SHORT
            ).show()
        }
        deleteDetail.setOnClickListener {
            val dbHandler = DBHelper(this, null)
            // inside this method we are calling an update course
            // method and passing all our edit text values.
            dbHandler.deleteDetail(
                enterAge.getText().toString())
            // displaying a toast message that our course has been updated.
            Toast.makeText(
                this@MainActivity,
                "Data Deleted..",
                Toast.LENGTH_SHORT
            ).show()


        }
    }
}