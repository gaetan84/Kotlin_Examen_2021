package com.example.kotlin_examen

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detail_enterprise.*
import kotlinx.android.synthetic.main.activity_location.*

class detail_enterprise : AppCompatActivity() , OnMapReadyCallback  {
    var lat: Double= 40.0
    var lon: Double= 40.0
    inner class QueryEnterprise(private val service:Enterprise_Service, private val layout: RelativeLayout): AsyncTask<Location_Enterprise, String,Enterprise>()
    {

fun formatText( text: String ) : String{


var maString=text.substring(0,1).toUpperCase()+text.substring(1).toLowerCase()
    return maString
}
        fun formatDateFr(date: String):String
        {
            var année: String = date.substring(0,4)
            var mois: String = date.substring(4,6)
            var jours: String = date.substring(6,8)
            var retour: String=""

when ( mois){

    "01"->mois="janvier"
    "02"->mois="février"
    "03"->mois="mars"
    "04"->mois="avril"
    "05"->mois="mai"
    "06"->mois="juin"
    "07"->mois="juillet"
    "08"->mois="aout"
    "09"->mois="septembre"
    "10"->mois="octobre"
    "11"->mois="novembre"
    "12"->mois="decembre"



}
            retour="Date de création de l'entreprise: Le $jours $mois $année"
            return  retour

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
            val date : String = result?.date_crea.toString()


            layout.findViewById<TextView>(R.id.Name_enterprise).text = formatText(result?.Name_enterprise.toString())
            layout.findViewById<TextView>(R.id.date_crea).text = formatDateFr(date)
            layout.findViewById<TextView>(R.id.adresse).text = String.format(getString(R.string.Adresse),formatText(result?.adresse.toString()) )
            layout.findViewById<TextView>(R.id.type).text =String.format(getString(R.string.nature_juridique), formatText(result?.type.toString()) )
            layout.findViewById<TextView>(R.id.activité).text= String.format(getString(R.string.activité), formatText(result?.activité.toString()) )
             lat= result?.lata!!
           lon=result?.lon
            layout.visibility = View.VISIBLE
            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this@detail_enterprise)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_enterprise)

        val svc = Enterprise_Service()
        val location = intent.getSerializableExtra("siret") as Location_Enterprise
      var test=  location.siret

        val layout = findViewById<RelativeLayout>(R.id.relative)
        QueryEnterprise(svc, layout).execute(location)

    }

    override fun onMapReady(googleMap: GoogleMap ) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        val positiongeo = LatLng(lat,lon)
        googleMap.addMarker(
            MarkerOptions().position(positiongeo)
                .title("marqueur"))



        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positiongeo,13f))



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.whole_map -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.location -> {
                val intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}