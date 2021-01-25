package com.example.kotlin_examen

import java.io.Serializable


data class Location_Enterprise(var name : String,var woeid: Int) : Serializable
{
    override fun toString(): String
    {
        return "$name "
    }
}
