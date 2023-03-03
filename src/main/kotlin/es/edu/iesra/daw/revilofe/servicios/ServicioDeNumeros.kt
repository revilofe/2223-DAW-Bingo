package es.edu.iesra.daw.revilofe.servicios

class ServicioDeNumeros(private val minimo: Int = 0, private val maximo: Int = 100) {

    private lateinit var fuenteNumeros: MutableList<Int>
    private val listaNumeros: List<Int> by lazy {
        List(maximo - minimo) {
            minimo + it
        }
    }

    init {
        require(maximo> minimo) { "El parámetro máximo tiene que ser mayor que mínimo." }
        inicializa()
    }
    fun inicializa() {
        fuenteNumeros = listaNumeros.shuffled().toMutableList()
    }

    fun unNumeroOrNull(): Int? = fuenteNumeros.removeFirstOrNull()

    fun siguientesNumeros(cuantos: Int): MutableList<Int> {
        lateinit var siguientesNumeros: MutableList<Int>
        if (cuantos >= fuenteNumeros.size)
            siguientesNumeros = restoNumeros()
        else {
            val tama = fuenteNumeros.size
            siguientesNumeros = fuenteNumeros.take(cuantos).toMutableList()
            fuenteNumeros = fuenteNumeros.takeLast(tama - cuantos).toMutableList()
        }
        return siguientesNumeros
    }

    fun restoNumeros(): MutableList<Int> {
        val restoNumeros = fuenteNumeros.toMutableList()
        fuenteNumeros.clear()
        return restoNumeros
    }
}
