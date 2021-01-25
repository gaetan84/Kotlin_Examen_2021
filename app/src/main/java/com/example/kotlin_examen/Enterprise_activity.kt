package com.example.kotlin_examen

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
            while (reader.hasNext())
            {
                if (reader.nextName() == "etablissement") {
                    reader.beginArray()
                    while (reader.hasNext()) {
                        val location = Location_Enterprise()
                        reader.beginObject()
                        while (reader.hasNext()) {

                            when (reader.nextName()) {





                                "siret" -> location.siret = reader.nextString()



                                "nom_raison_sociale" -> location.name = reader.nextString()


                                "departement" ->{ if (reader.peek() == JsonToken.NULL){

                                 reader.nextNull()
                                }


                                else {
                                    location.departement = reader.nextString()
                                }}
                                else -> reader.skipValue()

                            }
                        }
                        reader.endObject()
                        listLocation.add(location)
                    }

                    reader.endArray()
                }
                else
                {

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
}
