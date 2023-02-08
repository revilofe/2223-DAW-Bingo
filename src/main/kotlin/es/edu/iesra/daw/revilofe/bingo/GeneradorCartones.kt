package es.edu.iesra.daw.revilofe.bingo

import es.edu.iesra.daw.revilofe.servicios.ServicioDeNumeros

object GeneradorCartones {
    fun genera(cuantos: Int): List<Carton> {
        return List<Carton>(cuantos){
            Carton( it.toString(),
                listOf(
                    ServicioDeNumeros(1,16).siguientesNumeros(4),
                    ServicioDeNumeros(16,31).siguientesNumeros(4),
                    ServicioDeNumeros(31,46).siguientesNumeros(4),
                    ServicioDeNumeros(46,61).siguientesNumeros(4),
                    ServicioDeNumeros(61,76).siguientesNumeros(4)
                )
            )
        }
    }

}
