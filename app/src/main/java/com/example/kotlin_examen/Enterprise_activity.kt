package com.example.kotlin_examen

import android.content.Context
import android.graphics.BitmapFactory
import android.util.JsonReader
import android.util.JsonToken
import androidx.room.Database
import androidx.room.util.DBUtil
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Enterprise_Service(val entrepriseDAO: Location_EnterpriseDAO) {

    private val apiUrl = "https://entreprise.data.gouv.fr"
    private val queryUrl = "$apiUrl/api/sirene/v1/full_text/%s?page=1&per_page=100"

    fun getLocations(query1: String): List<Location_Enterprise> {
        val url = URL(String.format(queryUrl, query1))

        var url2: String = String.format(queryUrl, query1)
        var connection: HttpsURLConnection? = null


        val existe = entrepriseDAO.getIDbyurl(url2)

        if (existe == 0) {



            try {
                connection = url.openConnection() as HttpsURLConnection
                connection.connect()

                val code = connection.responseCode

                if (code != HttpsURLConnection.HTTP_OK) {
                    return emptyList()
                }
                val inputStream = connection.inputStream ?: return emptyList()
                val reader = JsonReader(inputStream.bufferedReader())
                val listLocation = mutableListOf<Location_Enterprise>()
                reader.beginObject()
                while (reader.hasNext()) {
                    if (reader.nextName() == "etablissement") {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            val location = Location_Enterprise()
                            reader.beginObject()
                            while (reader.hasNext()) {

                                when (reader.nextName()) {


                                    "siret" -> location.siret = reader.nextString()


                                    "nom_raison_sociale" -> location.name = reader.nextString()


                                    "departement" -> {
                                        if (reader.peek() == JsonToken.NULL) {

                                            reader.nextNull()
                                        } else {
                                            location.departement = reader.nextString()
                                        }
                                    }

                                    else -> reader.skipValue()

                                }

                            }
                            reader.endObject()
                            location.recherche = url2
                            listLocation.add(location)
                            entrepriseDAO.insert(location)
                        }

                        reader.endArray()
                    } else {

                        reader.skipValue()

                    }
                }
                reader.endObject()
                return listLocation
            } catch (e: IOException) {
                return emptyList()
            } finally {
                connection?.disconnect()
            }
        } else
        {


            val listLocation = mutableListOf<Location_Enterprise>()
            listLocation.addAll(entrepriseDAO.selectbyrecherche(url2))

            return listLocation

        }

    }



    fun getinformation(location: Location_Enterprise): Enterprise? {
        val querySiret = location.siret
        val queryurl = "https://entreprise.data.gouv.fr/api/sirene/v1/siret/$querySiret"


        val urlWeather = URL(String.format(queryurl))
        var connect: HttpsURLConnection? = null

        try {
            connect = urlWeather.openConnection() as HttpsURLConnection
            connect.connect()
            val code = connect.responseCode

            if (code != HttpsURLConnection.HTTP_OK) {
                return null
            }
            val inputStream = connect.inputStream ?: return null
            val reader = JsonReader(inputStream.bufferedReader())
            val entreprise = Enterprise()

            reader.beginObject()
            while (reader.hasNext()) {
                if (reader.nextName() == "etablissement") {

                    // val entreprise = Enterprise()
                    reader.beginObject()
                    while (reader.hasNext()) {

                        when (reader.nextName()) {
                            "nom_raison_sociale" -> {
                                if (reader.peek() == JsonToken.NULL) { reader.nextNull()}else{entreprise.Name_enterprise =
                                reader.nextString()}}
                            "geo_adresse" -> {
                                if (reader.peek() == JsonToken.NULL) { reader.nextNull()}else{entreprise.adresse = reader.nextString()}}

                            "date_creation" -> {
                                if (reader.peek() == JsonToken.NULL) { reader.nextNull()}else{entreprise.date_crea = reader.nextString()}}
                            "libelle_nature_juridique_entreprise" -> {
                                if (reader.peek() == JsonToken.NULL) { reader.nextNull()}else{entreprise.type =
                                reader.nextString()}}
                            "libelle_activite_principale" ->{
                                if (reader.peek() == JsonToken.NULL) { reader.nextNull()}else{ entreprise.activité =
                                reader.nextString()}}
                            "longitude" -> {
                                if (reader.peek() == JsonToken.NULL) {

                                    reader.nextNull()
                                } else {
                                    entreprise.lon = reader.nextString().toDouble()
                                }}
                            "latitude" -> {
                                if (reader.peek() == JsonToken.NULL) {

                                reader.nextNull()
                            } else {
                                entreprise.lata = reader.nextString().toDouble()
                            }}
                            else -> reader.skipValue()
                        }
                    }
                    reader.endObject()


                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()

            return entreprise
        } catch (e: IOException) {
            return null
        } finally {
            connect?.disconnect()
        }

    }
}
