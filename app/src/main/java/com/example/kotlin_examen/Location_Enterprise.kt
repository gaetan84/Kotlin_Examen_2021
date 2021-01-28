package com.example.kotlin_examen

import java.io.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
@Entity
data class Location_Enterprise(@PrimaryKey(autoGenerate =true)var id:Int?=null,var recherche: String="test" ,var siret: String="0",var name : String="0", var departement: String="aucun departement renseigné") : Serializable
{
    override fun toString(): String
    {
        return "$name $departement"
    }



}

