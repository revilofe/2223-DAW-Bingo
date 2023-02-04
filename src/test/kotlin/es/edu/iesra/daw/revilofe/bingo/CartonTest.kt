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
                listOf(4, 6, 7, 15),
                listOf(29, 22, 28, 16),
                listOf(40, 37, 34, 41),
                listOf(46, 50, 54, 47),
                listOf(75, 61, 70, 69)
            )
        )

        it("Tiene que marcar aquel numero que ha salido y lo contiene") {
            val numero = 15
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
            listOf(4,29,40,46,75).forEach{
                carton.marcar(it)
            }
            carton.compruebaSiLinea().shouldBeTrue()

        }
        it("Tiene que poder chequear si ha conseguido bingo") {

        }
    }
})