package es.edu.iesra.daw.revilofe.bingo

object Registro {
    private var carton: NumerosCarton? = null
    private var registroLineas:  MutableList<Linea> =  mutableListOf()
    private var registroNumeros:  MutableList<Int> =  mutableListOf()

    /**
     * Callback que se llama para informar de nuevo numero
     */
    fun nuevoNumero(numero: Int)
    {
        registroNumeros.add(numero)
        informaDelNumero(numero)
    }

    /**
     * Callback que se llama cuando para informar de nueva linea
     */
    fun lineaCantada(linea: Linea)
    {
        registroLineas.add(linea)
        informaDelLinea(linea)
    }

    /**
     * Callback que se llama cuando para informar de bingo
     */
    fun bingoCantado(carton: NumerosCarton)
    {
        this.carton = carton
        informaDelBingo(carton)
    }

    //TODO: Valorar si a la hora de informar puede tener sentido crear una clase display.
    /**
     * Informa de un numero
     */
    private fun informaDelNumero(numero: Int) {
        println("numero ${registroNumeros.size}: $numero")
    }

    /**
     * Informa de una linea
     */
    private fun informaDelLinea(linea: Linea) {
        println("línea número ${registroLineas.size} del cartón:${linea.idCarton}: ").also {
            linea.linea.forEach {
                println("- ${it.f} ${it.c}: ${it.numero}")
            }
        }

    }

    /**
     * Informa de un bingo
     */
    private fun informaDelBingo(carton: NumerosCarton) {
        println("Han cantado bingo, el cartón:${carton.idCarton}: ").also {
            carton.numeros.forEach {
                it.forEach {casilla->
                    print("(${casilla.f} ${casilla.c}: ${casilla.numero}")
                    when(casilla.estado) {
                        EstadoCasilla.NOMARCADO -> print("O) ")
                        EstadoCasilla.MARCADO -> print("V) ")
                        EstadoCasilla.VACIO -> print("X) ")
                    }
                }
            }
        }
    }

}