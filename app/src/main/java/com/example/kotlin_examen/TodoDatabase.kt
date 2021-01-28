package com.example.kotlin_examen

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ Location_Enterprise::class], version = 1)
abstract class TodoDatabase : RoomDatabase (){

    abstract fun locationDao(): Location_EnterpriseDAO


    fun seed()
    {



            if (locationDao().count() == 0)
            {
                val Location_Entreprise1 = Location_Enterprise(id = 0, recherche = "test",siret = "555",name = "test1",departement ="84")
                val Location_Entreprise2 = Location_Enterprise(id = 1, recherche = "test1",siret = "555",name = "test2",departement ="88")


            }

        }





    companion object {
        var INSTANCE: TodoDatabase? = null
        fun getDatabase(context: Context): TodoDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, TodoDatabase::class.java, "Todoactivité")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}
