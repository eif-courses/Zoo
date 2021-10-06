package eif.viko.lt.zoo

data class Gyvunas(val pavadinimas:String="",
              val aprasymas:String="",
              val paveikslelis: String=""){
    override fun toString() = pavadinimas

}