package es.edu.iesra.daw.revilofe.bingo
/*
El juego consistirá en lo siguiente:

Configura tu juego: generar un número X de cartones.
Iterar la dinámica siguiente hasta que algún cartón consiga bingo.
    El bombo genera una bola marcada con un número.
    Se marca en los X cartones el número marcado en la bola.
    Se chequea si algún cartón de los X cartones, ha conseguido línea.
    Se chequea si algún cartón de los X cartones, ha conseguido bingo.
Resume el juego.

Cada vez que se consiga una línea, se mostrará el identificador del
cartón y las posiciones y número de cartones marcados para conseguir la línea.
Cuando se consiga una línea, se mostrará el identificador del cartón
y las posiciones y número de cartones marcados.
Al finalizar, se podrá pedir un resumen por cartón, en el que se muestran
los identificadores de los cartones y las líneas conseguidas, así como
el bingo del cartón ganador.
 */

data class ConfigJuego(val nunCartones: Int = 5, val bombo: Bombo = Bombo, val locutor: Locutor =Locutor, val generador: GeneradorCartones = GeneradorCartones, val registro: Registro = Registro)

object Juego {

    private var hayBingo: Boolean = false
    private var numCartones: Int = 0
    private lateinit var locutor: Locutor
    private lateinit var generador: GeneradorCartones
    private lateinit var cartones: List<Carton>
    private lateinit var bombo: Bombo
    private lateinit var registro: Registro

    fun configura(config : ConfigJuego= ConfigJuego()) {
        numCartones = config.nunCartones
        generador = config.generador
        locutor = config.locutor.apply {
            configura(config.bombo)
        }
        registro = config.registro
        montaJuego()
        
    }

    /**
     * Monta el bingo, creando los cartones y conecta los observers del locutor y de los cartones.
     */
    private fun montaJuego(){

        //Me creo los cartones,
        cartones = generador.genera(numCartones)

        //Añado como observadores del locutor.
        cartones.forEach {carton ->
            locutor.nuevoNumero.conectar (carton::marcar)
        }
        locutor.nuevoNumero.conectar (registro::nuevoNumero)

        //Observadores de cartones
        cartones.forEach {carton ->
            carton.cantaBingo.conectar ( registro::bingoCantado )
            carton.cantaLinea.conectar ( registro::lineaCantada )
            carton.cantaBingo.conectar ( this::bingoCantado )

        }

    }

    /**
     * Pone en juego el bingo.
     */
    fun play() {
        while (!hayBingo && locutor.anunciaNuevaBola()){
            cartones.forEach { carton ->
                carton.compruebaSiLinea()
                carton.compruebaSiBingo()
            }
        }
    }

    /**
     * Callback que se llama cuando hay bingo
     */
    private fun bingoCantado(carton: NumerosCarton) {
        hayBingo = true
    }

}