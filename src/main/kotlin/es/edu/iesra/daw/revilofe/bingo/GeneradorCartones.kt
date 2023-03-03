package es.edu.iesra.daw.revilofe.bingo

import es.edu.iesra.daw.revilofe.servicios.ServicioDeNumeros

object GeneradorCartones {

    /**
     * Genera los cartones indicados por **cuantos**
     */
    fun genera(cuantos: Int): List<Carton> {
        // TODO: Para hacerlo generico, se tendría que indicar el número de filas, columnas. Y calcular el rango de
        // numeros de cada columna y cuantos numeros tiene que proporcionar columna
        return List<Carton>(cuantos) {
            Carton(
                it.toString(),
                listOf(
                    ServicioDeNumeros(1, 16).siguientesNumeros(4),
                    ServicioDeNumeros(16, 31).siguientesNumeros(4),
                    ServicioDeNumeros(31, 46).siguientesNumeros(4),
                    ServicioDeNumeros(46, 61).siguientesNumeros(4),
                    ServicioDeNumeros(61, 76).siguientesNumeros(4)
                )
            )
        }
    }
}
