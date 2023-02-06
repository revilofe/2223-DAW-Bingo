package es.edu.iesra.daw.revilofe.bingo

enum class EstadoCasilla {
    MARCADO, NOMARCADO, VACIO
}
data class Casilla(val f:Int, val c:Int, val numero:Int, var estado: EstadoCasilla = EstadoCasilla.NOMARCADO)
enum class LineaCantada {
    SI, NO
}
data class Linea(val linea:List<Casilla>, val cantada:LineaCantada=LineaCantada.NO)



/*
Cada cartón tendrá un identificador.
Cada cartón cuenta con cinco columnas y cinco filas, con números generados al azar.
    La primera columna incluye 4 números del 1 al 15
    la segunda desde el 16 al 30
    la tercera desde el 31 al 45
    el cuarto desde el 46 al 60 y la quinta desde el 61 al 75.
 */
class Carton(numeros: List<List<Int>>) {
    private lateinit var carton : List<List<Casilla>>
    private lateinit var estadoBingo : MutableMap<Int, Casilla>
    private var estadoLineas : MutableList<MutableList<Casilla>> = MutableList<MutableList<Casilla>>((carton.size*2)+2) {
        MutableList<Casilla>(carton.size-1)
    }

    init{
        montaCarton(numeros)
        montaEstadoCarton()
    }

    private fun montaEstadoCarton() {
        //Horizontales
        carton.forEachIndexed { indexCasillas, casillas ->
            estadoLineas.add()
            casillas.forEachIndexed { indexCasilla, casilla ->

            }
        }
        //Verticales

        //Diagonales
    }

    private fun montaCarton(numeros: List<List<Int>>) {
        var indFilasNumeros = 0
        var indColumasNumeros = 0
        estadoBingo = mutableMapOf()
        carton = List(numeros[0].size + 1) { iColumnas ->
            List(numeros.size) { iFilas ->
                if (iFilas == iColumnas) {
                    //println("$iFilas, $iColumnas, -1")
                    Casilla(iFilas, iColumnas, -1, EstadoCasilla.VACIO)
                } else {
                    //println("$iFilas, $iColumnas, <- $indFilasNumeros, $indColumasNumeros: ${numeros[indFilasNumeros][indColumasNumeros]}")
                    Casilla(iFilas, iColumnas, numeros[indFilasNumeros][indColumasNumeros++]).also {
                        estadoBingo[it.numero] = it
                    }
                }
            }.also {
                indColumasNumeros = 0
                indFilasNumeros++
            }
        }
    }

    fun estaMarcado(numero: Int): Boolean {
        var estaMarcado=false
        estadoBingo[numero]?.let{
            estaMarcado=(it.estado==EstadoCasilla.MARCADO)
        }
        return estaMarcado
    }

    fun marcar(numero: Int) {
        estadoBingo[numero]?.estado = EstadoCasilla.MARCADO
    }

    fun compruebaSiLinea(): Boolean {
        return false
    }

    fun compruebaSiBingo(): Boolean {
        return (estadoBingo.values.filter {
            it.estado == EstadoCasilla.NOMARCADO
        }.size == 0)
    }
}
