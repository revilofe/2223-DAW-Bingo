package es.edu.iesra.daw.revilofe.bingo

data class Casilla(val f:Int, val c:Int, val numero:Int, var marcado:Boolean=false)

class Carton(numeros: List<List<Int>>) {
    lateinit var carton : List<List<Casilla>>
    private var marcado: Boolean = false
    init{
        var indFilasNumeros = 0
        var indColumasNumeros = 0

        carton = List(numeros[0].size+1){iColumnas->
            List<Casilla>(numeros.size){iFilas->
                if (iFilas==iColumnas){
                    //println("$iFilas, $iColumnas, -1")
                    Casilla(iFilas,iColumnas,-1)
                }
                else {
                    //println("$iFilas, $iColumnas, <- $indFilasNumeros, $indColumasNumeros: ${numeros[indFilasNumeros][indColumasNumeros]}")
                    Casilla(iFilas, iColumnas, numeros[indFilasNumeros][indColumasNumeros++])
                }
            }.also{
                indColumasNumeros=0
                indFilasNumeros++
            }
        }
    }
    fun estaMarcado(numero: Int): Boolean {
        return marcado
    }

    fun marcar(numero: Int) {
        marcado = true
    }

}
