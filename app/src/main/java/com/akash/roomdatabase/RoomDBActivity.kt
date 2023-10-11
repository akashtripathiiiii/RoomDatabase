package com.akash.roomdatabase

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomDBActivity : AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var display:Button
    lateinit var database: ContactDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_dbactivity)

        database=Room.databaseBuilder(applicationContext,ContactDatabase::class.java,"contact").build()
        display=findViewById(R.id.displayBtn)
        listView=findViewById(R.id.listView)
        listView.setOnItemLongClickListener { parent, view, position, id ->
            val view=parent.get(position)


            val id=view.findViewById<TextView>(R.id.idListItem).text.toString().toLong()
            val name=view.findViewById<TextView>(R.id.nameListItem).text.toString()
            val phone=view.findViewById<TextView>(R.id.phoneListItem).text.toString().toLong()
            val address=view.findViewById<TextView>(R.id.addressText).text.toString()

            var builder=AlertDialog.Builder(this)
            builder.setTitle("Edit")

            var linearLayout=LinearLayout(this)
            linearLayout.orientation=LinearLayout.VERTICAL
            val idView= EditText(this)
            idView.setText(id.toString())
            linearLayout.addView(idView)

            val nameView=EditText(this)
            nameView.text.clear()
            nameView.setText(name)
            linearLayout.addView(nameView)

            val phoneView=EditText(this)
            phoneView.text.clear()
            phoneView.setText(phone.toString())
            linearLayout.addView(phoneView)

            val addressView=EditText(this)
            addressView.text.clear()
            addressView.setText(address)
            linearLayout.addView(addressView)

            builder.setView(linearLayout)
            builder.setPositiveButton("Edit",DialogInterface .OnClickListener{
                dialog, which ->
                val updatedName = nameView.text.toString()
                val updatedPhone = phoneView.text.toString().toLong()
                val updatedAddress = addressView.text.toString()
                GlobalScope.launch{
                    database.ContactDao().update(Contact(id, updatedName,updatedPhone,updatedAddress))
                }
                Toast.makeText(this,"Updated $updatedName"+"$updatedPhone"+"$updatedAddress",Toast.LENGTH_SHORT).show()
            })
            builder.setNegativeButton("Cancel",DialogInterface .OnClickListener{
                dialog, which -> dialog.cancel()
            })
            builder.show()
            return@setOnItemLongClickListener true
        }
        display.setOnClickListener(){
            getData(it)
        }
        var btn = findViewById<Button>(R.id.addBtn)
        btn.setOnClickListener(){
            var id=findViewById<EditText>(R.id.idText)
            var name=findViewById<EditText>(R.id.nameText)
            var phone=findViewById<EditText>(R.id.phoneText)
            var address=findViewById<EditText>(R.id.addressText)
            GlobalScope.launch {
                database.ContactDao().insert(Contact
                    (id.text.toString().toLong(),
                    name.text.toString(),
                    phone.text.toString().toLong(),
                    address.text.toString()))
            } } }
    fun getData(view: View) {
        database.ContactDao().getContact().observe(this){
            val adapter = MyAdapter(this,
                R.layout.list_item,it)
            listView.adapter = adapter
            }
        }
    }
