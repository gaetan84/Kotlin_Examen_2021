package com.example.kotlin_examen
import androidx.room.*
@Dao
interface Location_EnterpriseDAO {




    @Query ("SELECT * FROM Location_Enterprise WHERE recherche=:url ")
    fun selectbyrecherche(url: String): List<Location_Enterprise>

    @Query ("SELECT * FROM Location_Enterprise")
    fun selectall():List<Location_Enterprise>
    @Query ("SELECT recherche FROM Location_Enterprise")
    fun select():String
    @Query ("SELECT  id FROM Location_Enterprise WHERE recherche= :url")
    fun getIDbyurl(url: String): Int
    @Insert
    fun insert(locationEnterprise: Location_Enterprise)

    @Query("SELECT COUNT(*) FROM location_enterprise")
    fun count(): Int
    @Update
    fun update(locationEnterprise: Location_Enterprise)

    @Delete
    fun delete(locationEnterprise: Location_Enterprise)
}