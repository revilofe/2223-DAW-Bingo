package es.edu.iesra.daw.revilofe.bingo

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

/*
Cada cartón tiene que gestionar los números que han salido y coinciden con un
número que existe en el cartón. Es decir, tiene que marcar aquellos números que
han salido.
El cartón tiene que poder chequear si ha conseguido marcar una línea de 4
números  (línea recta, horizontal, vertical o diagonal)
El cartón tiene que poder chequear si ha conseguido bingo, si ha
conseguido marcar todos los números del cartón.
 */
class CartonTest : DescribeSpec({
    describe("Un cartón de bingo") {

        /*
        val carton = Carton(
            ServicioDeNumeros(1,16).siguientesNumeros(4),
            ServicioDeNumeros(16,31).siguientesNumeros(4),
            ServicioDeNumeros(31,46).siguientesNumeros(4),
            ServicioDeNumeros(46,61).siguientesNumeros(4),
            ServicioDeNumeros(61,76).siguientesNumeros(4))
         */
        val carton = Carton(
            listOf(
                listOf(8, 4, 7, 11),
                listOf(17, 23, 28, 19),
                listOf(35, 41, 37, 33),
                listOf(54, 48, 52, 50),
                listOf(61, 68, 70, 74)
            )
        )

        it("Tiene que marcar aquel numero que ha salido y lo contiene") {
            val numero = 41
            carton.estaMarcado(numero).shouldBeFalse()
            carton.marcar(numero)
            carton.estaMarcado(numero).shouldBeTrue()
        }
        it("No tiene que marcar aquel numero que ha salido y no contine") {
            val numero = 1
            carton.estaMarcado(numero).shouldBeFalse()
            carton.marcar(numero)
            carton.estaMarcado(numero).shouldBeFalse()
        }
        it("Tiene que poder chequear si ha conseguido una linea") {
            carton.compruebaSiLinea().shouldBeFalse()
            listOf(54, 48, 52, 50).forEach{
                carton.marcar(it)
            }
            carton.compruebaSiLinea().shouldBeTrue()
        }
        it("Tiene que poder chequear si ha conseguido bingo") {
            carton.compruebaSiBingo().shouldBeFalse()
            listOf(54, 1, 15, 22, 74, 48, 52, 50, 8, 4, 7, 11, 17, 23, 28, 19, 35, 41, 37, 33, 61, 68, 70, 74 ).forEach{
                carton.marcar(it)
            }
            carton.compruebaSiBingo().shouldBeTrue()
        }
    }
})