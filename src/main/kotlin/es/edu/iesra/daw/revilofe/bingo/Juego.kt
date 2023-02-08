package es.edu.iesra.daw.revilofe.bingo

data class ConfigJuego(val nunCartones: Int = 5, val locutor: Locutor =Locutor, val generador: GeneradorCartones = GeneradorCartones)

object Juego {

    private var numCartones: Int = 0
    private lateinit var locutor: Locutor
    private lateinit var generador: GeneradorCartones

    fun configura(config : ConfigJuego= ConfigJuego()) {
        numCartones = config.nunCartones
        locutor = config.locutor
        generador = config.generador

    }

    fun montaJuego(){

    }

    fun play(){

    }
}