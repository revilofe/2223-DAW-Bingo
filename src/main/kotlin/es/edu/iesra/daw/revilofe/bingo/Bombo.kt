package es.edu.iesra.daw.revilofe.bingo

import es.edu.iesra.daw.revilofe.servicios.ServicioDeNumeros

object Bombo {

    lateinit var servicioNumeros: ServicioDeNumeros
    init {
        conf()
    }
    fun conf(servicioNumeros: ServicioDeNumeros = ServicioDeNumeros(1,76)) {
        this.servicioNumeros = servicioNumeros
    }

    fun nuevaBola(): Int? {
        return servicioNumeros.unNumeroOrNull()
    }

}
