package com.example.kotlin_examen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class Adapterlist(private val context: Context,private  val dataSource: List<Location_Enterprise>):BaseAdapter()  {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataSource.size
    }


    override fun getItem(position: Int): Any {
        return dataSource[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.listtrow, parent, false)


        val department_enterprise = rowView.findViewById(R.id.department) as TextView



        val Enterprise_name = rowView.findViewById(R.id.name_enterprise) as TextView
        val loc=getItem(position) as Location_Enterprise
        department_enterprise.text= loc.departement
        Enterprise_name.text=loc.name
        return rowView
    }
}

