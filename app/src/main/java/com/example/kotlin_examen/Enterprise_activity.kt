package com.example.kotlin_examen

import android.util.JsonReader
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherService() {

    private val apiUrl = "https://www.metaweather.com"
    private val queryUrl = "$apiUrl/api/location/search/?query=%s"

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

            reader.beginArray()
            while (reader.hasNext()) {
                val location = Location_Enterprise("", 0)
                reader.beginObject()
                while (reader.hasNext()) {
                    when (reader.nextName()) {

                        "title" -> location.name = reader.nextString()

                        "woeid" -> location.woeid = reader.nextInt()


                        else -> reader.skipValue()
                    }
                }
                reader.endObject()
                listLocation.add(location)
            }
            reader.endArray()
            return listLocation
        } catch (e: IOException) {
            return emptyList()
        } finally {
            connection?.disconnect()
        }
    }
}