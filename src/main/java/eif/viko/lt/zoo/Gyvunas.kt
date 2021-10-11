package eif.viko.lt.zoo

import java.io.Serializable


data class Gyvunas(val pavadinimas:String="",
              val aprasymas:String="",
              val paveikslelis: String=""){
    override fun toString() = pavadinimas
}

