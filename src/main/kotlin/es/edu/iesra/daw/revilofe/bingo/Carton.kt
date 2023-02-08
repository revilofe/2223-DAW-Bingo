package es.edu.iesra.daw.revilofe.bingo

enum class EstadoCasilla {
    MARCADO, NOMARCADO, VACIO
}
data class Casilla(val f:Int, val c:Int, val numero:Int, var estado: EstadoCasilla = EstadoCasilla.NOMARCADO)
enum class LineaCantada {
    SI, NO
}
data class Linea(val linea: MutableList<Casilla> =mutableListOf(), val cantada:LineaCantada=LineaCantada.NO)



/*
Cada cartón tendrá un identificador.
Cada cartón cuenta con cinco columnas y cinco filas, con números generados al azar.
    La primera columna incluye 4 números del 1 al 15
    la segunda desde el 16 al 30
    la tercera desde el 31 al 45
    el cuarto desde el 46 al 60 y la quinta desde el 61 al 75.
 */
class Carton(val id: String, numeros: List<List<Int>>) {
    private lateinit var carton : List<MutableList<Casilla>>
    private lateinit var estadoBingo : MutableMap<Int, Casilla>
    private lateinit var estadoLineas : MutableList<Linea>

    init{
        montaCarton(numeros)
        montaEstadoBingo()
        montaEstadoLineas()
        println(carton)
        estadoLineas.forEach { println(it) }
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
        var indiceLinea = 0
        var hayLinea = false
        while (!hayLinea && indiceLinea < estadoLineas.size){
            hayLinea = esUnaLineaSinCantar(estadoLineas[indiceLinea])
            indiceLinea++
        }
        return hayLinea
    }

    fun compruebaSiBingo(): Boolean {
        return (estadoBingo.values.none {
            it.estado == EstadoCasilla.NOMARCADO
        })
    }

    private fun montaCarton(numeros: List<List<Int>>) {
        val dimension = numeros.size
        carton = List(dimension){
            mutableListOf()
        }
        var filasCarton = 0
        var columnasCarton = 0

        numeros.forEach {
            it.forEach{numero ->
                if (filasCarton==columnasCarton) {
                    carton[filasCarton].add(Casilla(filasCarton, columnasCarton, -1, EstadoCasilla.VACIO))
                    filasCarton++
                }
                carton[filasCarton].add(Casilla(filasCarton, columnasCarton, numero, EstadoCasilla.NOMARCADO))

                filasCarton++
            }
            columnasCarton++
            filasCarton=0
        }.also {
            //Rellena la ultima casilla
            carton[dimension-1].add(Casilla(dimension-1, dimension-1, -1, EstadoCasilla.VACIO))
        }
    }

    private fun montaEstadoLineas() {
        var indiceEstadoLinea = 0
        estadoLineas = MutableList((carton.size*2)+2) {
            Linea()
        }
        //Lineas Horizontales
        carton.forEach { filaCasillas ->
            filaCasillas.filter { it.estado!= EstadoCasilla.VACIO}.forEach { casilla ->
                estadoLineas[indiceEstadoLinea].linea.add(casilla)
            }
            indiceEstadoLinea++
        }
        //Lineas Verticales
        val dimension = carton.size
        for(indiceCasilla in 0 until dimension) {
            for (indiceLista in 0 until dimension)
                if (carton[indiceLista][indiceCasilla].estado!=EstadoCasilla.VACIO)
                    estadoLineas[indiceEstadoLinea].linea.add(carton[indiceLista][indiceCasilla])
            indiceEstadoLinea++
        }

        //Diagonales
        for(indice in 0..dimension-2) {
            estadoLineas[indiceEstadoLinea].linea.add(carton[indice][indice+1])
            estadoLineas[indiceEstadoLinea+1].linea.add(carton[indice+1][indice])
        }

    }

    private fun montaEstadoBingo(){
        estadoBingo = mutableMapOf()
        carton.forEach { filaCasillas ->
            filaCasillas.filter { it.estado!= EstadoCasilla.VACIO}.forEach { casilla ->
                estadoBingo[casilla.numero] = casilla
            }
        }

    }

    private fun esUnaLineaSinCantar(linea: Linea): Boolean {
        return ((linea.linea.none { it.estado == EstadoCasilla.NOMARCADO }) && (linea.cantada == LineaCantada.NO))
    }

}
