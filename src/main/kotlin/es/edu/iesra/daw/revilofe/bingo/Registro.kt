package es.edu.iesra.daw.revilofe.bingo

object Registro {
    private var carton: NumerosCarton? = null
    private var registroLineas: MutableList<Linea> = mutableListOf()
    private var registroNumeros: MutableList<Int> = mutableListOf()

    /**
     * Callback que se llama para informar de nuevo numero
     */
    fun nuevoNumero(numero: Int) {
        registroNumeros.add(numero)
        informaDelNumero(numero)
    }

    /**
     * Callback que se llama cuando para informar de nueva linea
     */
    fun lineaCantada(linea: Linea) {
        registroLineas.add(linea)
        informaDelLinea(linea)
    }

    /**
     * Callback que se llama cuando para informar de bingo
     */
    fun bingoCantado(carton: NumerosCarton) {
        this.carton = carton
        informaDelBingo(carton)
    }

    // TODO: Valorar si a la hora de informar puede tener sentido crear una clase display.
    /**
     * Informa de un numero
     */
    private fun informaDelNumero(numero: Int) {
        println("##################################### Numero ${registroNumeros.size}: El $numero")
    }

    /**
     * Informa de una linea
     */
    private fun informaDelLinea(linea: Linea) {

        println("LÍNEA número ${registroLineas.size} del cartón [${linea.idCarton}]: ").also {
            linea.linea.forEach {
                print("(${it.f} ${it.c}: ${it.numero}) ")
            }
            println()
        }
    }

    /**
     * Informa de un bingo
     */
    private fun informaDelBingo(carton: NumerosCarton) {
        println("Han cantado BINGO!!!!!!!!!! El cartón [${carton.idCarton}]: ").also {
            carton.numeros.forEach {
                it.forEach { casilla ->
                    print("(${casilla.f} ${casilla.c}: ${casilla.numero}")
                    when (casilla.estado) {
                        EstadoCasilla.NOMARCADO -> print("O) ")
                        EstadoCasilla.MARCADO -> print("V) ")
                        EstadoCasilla.NULA -> print("X) ")
                    }
                }
                println()
            }
        }
    }

    fun resumen() {
        println()
        println("################## RESUMEN DE LA PARTIDA ##################")
        println("El cantidad de números que han salido: ${registroNumeros.size}")
        println("El número de líneas cantadas han sido: ${registroLineas.size}")
        registroLineas.groupBy {
            it.idCarton
        }.values.sortedBy {
            it.size
        }.forEach {
            println("El carton ${it[0].idCarton} ha cantado el total de líneas: ${it.size}")
        }
        println("\nEl carton ${carton?.idCarton} ha sido el GANADOR!!")
    }

    public fun muestraResumen() {
        println("Numeros que han salido...")
        registroNumeros.forEachIndexed { index, i ->
            println("Posición $index: $i")
        }

        println("\nCartones que han cantado línea...")
        registroLineas.forEachIndexed { index, linea ->
            println("\nPosición $index: Cartón ${linea.idCarton}")
            linea.linea.forEach { print("${it.numero} ") }
        }

        println("\nCarton que han bingo: ${carton?.idCarton}")
    }
}
