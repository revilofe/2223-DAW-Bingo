package es.edu.iesra.daw.revilofe.patrones.signalslot
//https://in-kotlin.com/design-patterns/observer/

class Signal<T> {

    class Conexion

    val callbacks = mutableMapOf<Conexion, (T) -> Unit>()

    fun emitir(nuevoValor: T) {
        for(cb in callbacks)
            cb.value(nuevoValor)
    }


    fun conectar(callback: (nuevoValor: T) -> Unit) : Conexion {
        val conexion = Conexion()
        callbacks[conexion] = callback
        return conexion
    }

    fun desconectar(conexion : Conexion) {
        callbacks.remove(conexion)
    }
}