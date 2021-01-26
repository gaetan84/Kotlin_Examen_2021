package com.example.kotlin_examen

import android.graphics.BitmapFactory
import android.util.JsonReader
import android.util.JsonToken
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Enterprise_Service() {

    private val apiUrl = "https://entreprise.data.gouv.fr"
    private val queryUrl = "$apiUrl/api/sirene/v1/full_text/%s?page=1&per_page=100"

    fun getLocations(query1: String): List<Location_Enterprise> {
        val url = URL(String.format(queryUrl, query1))
        var connection: HttpsURLConnection? = null

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
                        listLocation.add(location)
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
                                "nom_raison_sociale" -> entreprise.Name_enterprise =
                                    reader.nextString()
                                "geo_adresse" -> entreprise.adresse = reader.nextString()

                                "date_creation" -> entreprise.date_crea = reader.nextString()
                                "libelle_nature_juridique_entreprise" -> entreprise.type =
                                    reader.nextString()
                                "libelle_activite_principale"-> entreprise.activité=reader.nextString()
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
