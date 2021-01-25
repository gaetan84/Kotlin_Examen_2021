package com.example.kotlin_examen

import java.io.Serializable


data class Location_Enterprise(var siret: String="0",var name : String="0", var departement: String="aucun departement renseigné") : Serializable
{
    override fun toString(): String
    {
        return "$name"
    }
    fun name (): String {
        return  "$name"

    }

    fun department (): String {
        return  "$departement"

    }

}
