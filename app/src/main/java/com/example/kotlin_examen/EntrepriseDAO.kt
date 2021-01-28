package com.example.kotlin_examen

import androidx.room.*
@Dao
interface EntrepriseDAO {

    @Query ("SELECT * FROM enterprise WHERE siret=:siret1 ")
    fun selectbysiretl(siret1: String): Enterprise

    @Query ("SELECT  id FROM enterprise WHERE siret= :siret1")
    fun getIDbysiret(siret1: String): Int
    @Query("SELECT COUNT(*) FROM enterprise")
    fun count(): Int
    @Insert
    fun insert(entrepriseDAO: Enterprise)


    @Update
    fun update(entrepriseDAO: Enterprise)

    @Delete
    fun delete(entrepriseDAO: Enterprise)

}