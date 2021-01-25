package com.example.kotlin_examen

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class detail_enterprise : AppCompatActivity() {
    inner class QueryWeatherTask(private val service:Enterprise_Service, private val layout: RelativeLayout): AsyncTask<Location_Enterprise, String,Enterprise>()
    {


        fun formatDateFr(date: String):String
        {
            val date_tmp = date.split("-")
            return "${date_tmp[2]}/${date_tmp[1]}/${date_tmp[0]}"
        }

        override fun onPreExecute()
        {
            layout.visibility = View.INVISIBLE

        }

        override fun doInBackground(vararg params: Location_Enterprise?): Enterprise?
        {
            val query = params[0] ?: return null
            return service.getinformation(query)
        }

        override fun onPostExecute(result: Enterprise?)
        {
            val date = result?.date_crea


            layout.findViewById<TextView>(R.id.date_crea).text = String.format(getString(R.string.meteo_ville), date, result?.date_crea)
            layout.findViewById<TextView>(R.id.Name_enterprise).text =  result?.Name_enterprise
            layout.findViewById<TextView>(R.id.adresse).text =  result?.adresse
            layout.findViewById<TextView>(R.id.type).text = result?.type


            layout.visibility = View.VISIBLE

        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_enterprise)

        val svc = Enterprise_Service()
        val location = intent.getSerializableExtra("siret") as Location_Enterprise

        val layout = findViewById<RelativeLayout>(R.id.relative)
        QueryWeatherTask(svc, layout).execute(location)
    }
}