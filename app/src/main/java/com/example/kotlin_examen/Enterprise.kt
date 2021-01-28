package com.example.kotlin_examen

import com.google.android.gms.maps.model.LatLng
import java.util.*
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Enterprise(@PrimaryKey(autoGenerate =true)var date_crea: String="r", var Name_enterprise: String="r" , var adresse : String="r", var type : String="r",
                      var activité: String="r", var lata: Double=1.0, var lon: Double=1.0 ):Serializable

{



}
