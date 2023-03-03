package es.edu.iesra.daw.revilofe.bingo

import es.edu.iesra.daw.revilofe.servicios.ServicioDeNumeros
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.nulls.shouldBeNull
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

/*
El bombo expulsará una bola marcada con un número entre 1 y 75
que previamente no se ha generado anteriormente.

 */
class BomboTest : DescribeSpec({
    // Aunque no sería necesario crear un mock, ya que para los test nos servirían
    // que los numeros puedieran ser aleatorios, se realiza a modo de ejemplo.
    describe("Un bombo de un bingo"){
        var minimo = 1
        var maximo = 5

        // Creo una lista con los elementos ordenados de forma aleatoria
        var listaMock = List(maximo - minimo) {
            minimo + it
        }.shuffled()

        //El resto de metodos no los usaré, por tanto uso el modo relajado
        var servicioNumeroMock = mockk<ServicioDeNumeros>(relaxed = true)
        // Le digo que cada vez que se llame al objeto y metodo, devuelva un elemento de la lista.
        // Además, añado el elemento null al final, para que sea ese el que se devuelve si se hace
        // la invocación maximo + 1
        every { servicioNumeroMock.unNumeroOrNull() } returnsMany MutableList<Int?>(maximo - minimo) {
            minimo + it
            }.also{
                it.shuffled()
                it.add(null)
            }
        // Inyecto el servicio de numeros Mock
        Bombo.configura(servicioNumeroMock)
        it("Debe generar numeros aleatorios entre minimo y maximo") {
            // Hago (maximo - minimo) invocaciones al metodo y introduzco los elementos en la lista
            var lista = List(maximo - minimo) {
                Bombo.nuevaBola()
            }
            lista.shouldContainExactlyInAnyOrder(listaMock)
        }

        // En la invocación (maximo - minimo)+1, debe devolver nulo
        it("En la petición (maximo - minimo)+1, debe devolver null") {
            Bombo.nuevaBola().shouldBeNull()
            verify(exactly=(maximo - minimo)+1) { servicioNumeroMock.unNumeroOrNull()}
        }

    }
})