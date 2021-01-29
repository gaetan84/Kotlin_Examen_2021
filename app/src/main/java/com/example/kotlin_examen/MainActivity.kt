package com.example.kotlin_examen

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
            val test=listResult
            if (result?.isNullOrEmpty() == true) {
                Toast.makeText(this@MainActivity, "Aucune connexion internet", Toast.LENGTH_SHORT).show()
            } else {
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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = findViewById<ListView>(R.id.listResults)
        val db = TodoDatabase.getDatabase(this)
        val entdao = db.locationDao()
        val detaildao = db.detailDao()

        db.seed()
        val svc = Enterprise_Service(entdao, detaildao)
        val departement= findViewById<RadioButton>(R.id.radiodepartement)
        val codepostale= findViewById<RadioButton>(R.id.radiocode)
        val aucunparametre= findViewById<RadioButton>(R.id.aucunparametre)
        val edittextcodepost=  findViewById<EditText>(R.id.codepostale_edit)
        val edittextdepart=  findViewById<EditText>(R.id.depart_edit)

        edittextcodepost.visibility=View.INVISIBLE
        edittextdepart.visibility=View.INVISIBLE
        departement.setOnClickListener {


                codepostale.isChecked = false
edittextcodepost.visibility=View.INVISIBLE
            edittextdepart.visibility=View.VISIBLE


        }

        codepostale.setOnClickListener{

                departement.isChecked = false
            edittextcodepost.visibility=View.VISIBLE
            edittextdepart.visibility=View.INVISIBLE


        }
        aucunparametre.setOnClickListener{

            departement.isChecked = false
            codepostale.isChecked = false
            edittextcodepost.visibility=View.INVISIBLE
            edittextdepart.visibility=View.INVISIBLE

        }


        findViewById<ImageButton>(R.id.imagebuttonsearch).setOnClickListener {
            val equery = name_enterprise.text.toString()

            var apiUrl = "https://entreprise.data.gouv.fr"
            var queryUrl = "$apiUrl/api/sirene/v1/full_text/%s?page=1&per_page=100"

            if (equery.isEmpty()) {

                Toast.makeText(
                    this@MainActivity,
                    "Veuillez rentrer votre recherhce",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
               var url= String.format(queryUrl,equery)
                if (aucunparametre.isChecked== true){

                    QueryTask(svc, listResults).execute(url)

                } else if( codepostale.isChecked==true){

                    if (edittextcodepost.length()==5){
                    url=url+"&code_postal="+edittextcodepost.text
                    QueryTask(svc, listResults).execute(url)

                }else {
                    Toast.makeText(
                        this@MainActivity,
                        "Code Postal invalide",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                }


                 else if (departement.isChecked==true){
                    if (edittextdepart.length()==2){
                        url=url+"&departement="+edittextdepart.text
                        QueryTask(svc, listResults).execute(url)

                    }else {
                        Toast.makeText(
                            this@MainActivity,
                            "Departement invalide",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

             //   val sdf = SimpleDateFormat("dd/M/yyyyhh:mm:ss")
              //  val currentDate = sdf.format(Date())




            }
            list.setOnItemClickListener { parent, view, position, id ->
                val location = list.getItemAtPosition(position) as Location_Enterprise




                intent = Intent(this, detail_enterprise::class.java)
                intent.putExtra("siret", location)
                this.startActivity(intent)

            }
        }
    }
}