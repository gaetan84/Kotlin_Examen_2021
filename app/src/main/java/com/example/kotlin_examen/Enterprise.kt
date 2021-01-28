package com.example.kotlin_examen

import com.google.android.gms.maps.model.LatLng
import java.util.*
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Enterprise(@PrimaryKey(autoGenerate =true)var id:Long?=null, var date_crea: String="Aucune date renseignée", var Name_enterprise: String="Aucun nom renseigné" , var adresse : String="Aucune adresse renseignée", var type : String="Aucun type d'activité renseigné",
                      var activité: String="Aucune activté renseigné", var lata: Double=1.0, var lon: Double=1.0 , var siret:String="", var connectinternet: Boolean=false ): Serializable

{



}
