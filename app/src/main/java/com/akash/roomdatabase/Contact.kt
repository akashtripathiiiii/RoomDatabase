package com.akash.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val name: String,
    val phone: Long,
    val address: String
)