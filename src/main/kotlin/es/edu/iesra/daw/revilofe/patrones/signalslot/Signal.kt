package es.edu.iesra.daw.revilofe.patrones.signalslot
//https://in-kotlin.com/design-patterns/observer/

class Signal<T> {

    class Connection

    val callbacks = mutableMapOf<Connection, (T) -> Unit>()

    fun emit(newValue: T) {
        for(cb in callbacks) cb.value(newValue)
    }

    fun connect(callback: (newValue: T) -> Unit) : Connection {
        val connection = Connection()
        callbacks[connection] = callback
        return connection
    }

    fun disconnect(connection : Connection) {
        callbacks.remove(connection)
    }
}