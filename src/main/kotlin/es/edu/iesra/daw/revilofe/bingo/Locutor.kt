package es.edu.iesra.daw.revilofe.bingo

import es.edu.iesra.daw.revilofe.patrones.signalslot.Signal

/*
Los locutores de bingo supervisan las partidas de bingo que se juegan en los clubes de bingo.
Llaman a los números hasta que se produce un ganador.
 */
object Locutor(val bombo:Bombo = Bombo ) {
    val nuevoNumero = Signal<Int>()
    fun sacaNumero():Boolean{
        var hayBolas = false
        bombo.nuevaBola()?.let{
            nuevoNumero.emitir(it)
            hayBolas = true
        }
        return hayBolas
    }


}