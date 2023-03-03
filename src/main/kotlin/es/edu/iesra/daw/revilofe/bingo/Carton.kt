package es.edu.iesra.daw.revilofe.bingo

import es.edu.iesra.daw.revilofe.patrones.signalslot.Signal

/**
 * Representa los posibles estados de las casillas.
 */
enum class EstadoCasilla {
    MARCADO, NOMARCADO, NULA
}

/**
 * Representa las casillas del cartón
 */
data class Casilla(val f: Int, val c: Int, val numero: Int, var estado: EstadoCasilla = EstadoCasilla.NOMARCADO)

/**
 * Representa los posibles estados de una línea.
 */
enum class LineaCantada {
    SI, NO
}

/**
 * Representa una línea del cartón y su estado, y su estado. Estructura util para saber si hay o no línea.
 */
data class Linea(val idCarton: String, val linea: MutableList<Casilla> = mutableListOf(), var cantada: LineaCantada = LineaCantada.NO)

/**
 * Representa un cartón en modo data class. Util para pasar la info del cartón.
 */
data class NumerosCarton(val idCarton: String, val numeros: List<MutableList<Casilla>> = List(5) { mutableListOf() })

/*
Cada cartón tendrá un identificador.
Cada cartón cuenta con cinco columnas y cinco filas, con números generados al azar.
    La primera columna incluye 4 números del 1 al 15
    la segunda desde el 16 al 30
    la tercera desde el 31 al 45
    el cuarto desde el 46 al 60 y la quinta desde el 61 al 75.
 */
class Carton(private val idCarton: String, numeros: List<List<Int>>) {
    private lateinit var carton: List<MutableList<Casilla>>
    private lateinit var estadoBingo: MutableMap<Int, Casilla>
    private lateinit var estadoLineas: MutableList<Linea>
    val cantaLinea = Signal<Linea>()
    val cantaBingo = Signal<NumerosCarton>()

    init {
        montaCarton(numeros)
        montaEstadoBingo()
        montaEstadoLineas()
        // println(carton)
        // estadoLineas.forEach { println(it) }
    }

    /**
     * Monta el carton a partir de la lista de números.
     */
    private fun montaCarton(numeros: List<List<Int>>) {
        val dimension = numeros.size
        carton = List(dimension) {
            mutableListOf()
        }
        var filasCarton = 0
        var columnasCarton = 0

        numeros.forEach {
            it.forEach { numero ->
                if (filasCarton == columnasCarton) {
                    carton[filasCarton].add(Casilla(filasCarton, columnasCarton, -1, EstadoCasilla.NULA))
                    filasCarton++
                }
                carton[filasCarton].add(Casilla(filasCarton, columnasCarton, numero, EstadoCasilla.NOMARCADO))

                filasCarton++
            }
            columnasCarton++
            filasCarton = 0
        }.also {
            // Rellena la ultima casilla
            carton[dimension - 1].add(Casilla(dimension - 1, dimension - 1, -1, EstadoCasilla.NULA))
        }
    }

    /**
     * Monta la estructura de datos para saber si hay línea
     */
    private fun montaEstadoLineas() {
        var indiceEstadoLinea = 0
        estadoLineas = MutableList(carton.size * 3) {
            Linea(idCarton)
        }
        // Lineas Horizontales
        carton.forEach { filaCasillas ->
            filaCasillas.filter { it.estado != EstadoCasilla.NULA }.forEach { casilla ->
                estadoLineas[indiceEstadoLinea].linea.add(casilla)
            }
            indiceEstadoLinea++
        }
        val dimension = carton.size

        // Lineas Verticales
        for (indiceCasilla in 0 until dimension) {
            for (indiceLista in 0 until dimension)
                if (carton[indiceLista][indiceCasilla].estado != EstadoCasilla.NULA) {
                    estadoLineas[indiceEstadoLinea].linea.add(carton[indiceLista][indiceCasilla])
                }
            indiceEstadoLinea++
        }

        // Diagonales I-D\
        for (indice in 0 until dimension - 1) {
            estadoLineas[indiceEstadoLinea].linea.add(carton[indice][indice + 1])
            estadoLineas[indiceEstadoLinea + 1].linea.add(carton[indice + 1][indice])
        }
        indiceEstadoLinea += 2

        // Diagonales D-I/
        for (indiceFilas in dimension - 2 downTo 0) {
            estadoLineas[indiceEstadoLinea].linea.add(carton[indiceFilas][(dimension - 2) - indiceFilas])
        }
        indiceEstadoLinea++

        for (indiceFilas in dimension - 1 downTo 0) {
            if (indiceFilas != ((dimension - 1) - indiceFilas)) {
                estadoLineas[indiceEstadoLinea].linea.add(carton[indiceFilas][(dimension - 1) - indiceFilas])
            }
        }
        indiceEstadoLinea++

        for (indiceFilas in dimension - 1 downTo 1) {
            estadoLineas[indiceEstadoLinea].linea.add(carton[indiceFilas][dimension - indiceFilas])
        }
    }

    /**
     * Monta la estructura de datos para saber si hay bingo.
     */
    private fun montaEstadoBingo() {
        estadoBingo = mutableMapOf()
        carton.forEach { filaCasillas ->
            filaCasillas.filter { it.estado != EstadoCasilla.NULA }.forEach { casilla ->
                estadoBingo[casilla.numero] = casilla
            }
        }
    }

    /**
     * Pregunta si un número está marcado en el cartón.
     */
    fun estaMarcado(numero: Int): Boolean {
        var estaMarcado = false
        estadoBingo[numero]?.let {
            estaMarcado = (it.estado == EstadoCasilla.MARCADO)
        }
        return estaMarcado
    }

    /**
     * Marcar un número.
     */
    fun marcar(numero: Int) {
        estadoBingo[numero]?.estado = EstadoCasilla.MARCADO
    }

    /**
     * Comprueba si hay línea sin cantar, y si es así la canta.
     */
    fun compruebaSiLinea(): Boolean {
        var indiceLinea = 0
        var hayLinea = false
        while (indiceLinea < estadoLineas.size) {
            if (esUnaLineaSinCantar(estadoLineas[indiceLinea])) {
                hayLinea = true
                cantaLinea(estadoLineas[indiceLinea])
            }
            indiceLinea++
        }
        return hayLinea
    }

    /**
     * Canta la línea, emitiendo una señal con la línea y la marca como cantada
     *
     * TODO: ¿Tiene sentido que sea el carton quien canta la línea?
     */
    private fun cantaLinea(linea: Linea) {
        linea.cantada = LineaCantada.SI
        cantaLinea.emitir(linea)
    }

    /**
     * Comprueba si hay bingo, y si es asi lo canta.
     */
    fun compruebaSiBingo(): Boolean {
        val hayBingo = (
            estadoBingo.values.none {
                it.estado == EstadoCasilla.NOMARCADO
            }
            )
        if (hayBingo) {
            cantaBingo(numerosCarton())
        }
        return hayBingo
    }

    /**
     * Canta el bingo, emitiendo una señal con la información del cartón
     *
     * TODO: ¿Tiene sentido que sea el carton quien canta el bingo?
     */
    private fun cantaBingo(numerosCarton: NumerosCarton) {
        cantaBingo.emitir(numerosCarton)
    }

    /**
     * Pregunta si es linea y sin cantar
     */
    private fun esUnaLineaSinCantar(linea: Linea): Boolean {
        return ((linea.linea.none { it.estado == EstadoCasilla.NOMARCADO }) && (linea.cantada == LineaCantada.NO))
    }

    /**
     * Convierte a formato data class los numeros del cartón para enviarlo por señal
     */
    private fun numerosCarton(): NumerosCarton {
        val numerosCarton = NumerosCarton(idCarton)
        var indiceFilas = 0
        carton.forEach {
            it.forEach { casilla ->
                numerosCarton.numeros[indiceFilas].add(casilla.copy())
            }
            indiceFilas++
        }
        return numerosCarton
    }
}
