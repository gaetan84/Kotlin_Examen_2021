package com.example.kotlin_examen

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    inner class QueryTask(
        private val svc: Enterprise_Service,
        private val listResult: ListView


    ) :
        AsyncTask<String, Void, List<Location_Enterprise>>() {

        override fun onPreExecute() {

            listResult.visibility = View.INVISIBLE
        }

        override fun doInBackground(vararg params: String?): List<Location_Enterprise> {
            val query = params[0] ?: return emptyList()

            return svc.getLocations(query)
        }

        override fun onPostExecute(result: List<Location_Enterprise>?) {
            //  val adapter=Adapterlist(applicationContext,list)
            //  listResult.adapter=adapter
            listResult.adapter = ArrayAdapter<Location_Enterprise>(
                applicationContext,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                result!!
            )
            listResult.visibility = View.VISIBLE

            super.onPostExecute(result)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = findViewById<ListView>(R.id.listResults)
        val svc = Enterprise_Service()
        findViewById<ImageButton>(R.id.imagebuttonsearch).setOnClickListener {
            val equery = name_enterprise.text.toString()
            QueryTask(svc, listResults).execute(equery)
        }
        list.setOnItemClickListener { parent, view, position, id ->
            val location = list.getItemAtPosition(position) as Location_Enterprise




            intent = Intent(this, detail_enterprise::class.java)
            intent.putExtra("siret", location)
            this.startActivity(intent)

        }
    }
}