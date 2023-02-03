package es.edu.iesra.daw.revilofe.api

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.string.shouldStartWith

class NumberServiceTest : DescribeSpec({
    describe("Servicio de numeros"){
        it("El total de números en la fuente es igual a máximo-mínimo"){
            val minimo = 5
            val maximo = 6
            val service = ServicioDeNumeros(minimo, maximo)
            service.restoNumeros().size.shouldBeExactly(maximo-minimo)
        }
        describe( "Con una diferencia de <=0 entre máximo y minimo"){
            it("=0, debe lanzar un error de parámetros ilegales"){
                val minimo = 5
                val maximo = 5
                val exception = shouldThrowExactly<IllegalArgumentException> {
                    ServicioDeNumeros(minimo, maximo)
                }
                exception.message.shouldStartWith ("El parámetro máximo")
            }
            it("<0, debe lanzar un error de parámetros ilegales"){
                val minimo = 5
                val maximo = 4
                val exception = shouldThrowExactly<IllegalArgumentException> {
                    ServicioDeNumeros(minimo, maximo)
                }
                exception.message.shouldStartWith ("El parámetro máximo")
            }
        }
        describe( "Con una diferencia de 1 entre máximo y minimo")
        {
            val minimo = 5
            val maximo = 6
            val service = ServicioDeNumeros(minimo, maximo)
            it("el servicio contiene únicamente el valor mínimo"){
                service.unNumeroOrNull()?.shouldBeExactly(minimo)
            }
            it("el servicio queda vacío, al sacar el primer y único número"){
                service.restoNumeros().size.shouldBeExactly(0)
            }
            describe( "Una vez inicializado")
            {
                service.inicializa()
                it("el servicio contiene únicamente el valor mínimo") {
                    service.unNumeroOrNull()?.shouldBeExactly(minimo)
                }
                it("el servicio queda vacío, al sacar el primer y único número") {
                    service.restoNumeros().size.shouldBeExactly(0)
                }
            }
            describe( "Una vez inicializado, por segunda vez")
            {
                service.inicializa()
                it("el servicio contiene únicamente el valor mínimo") {
                    service.unNumeroOrNull()?.shouldBeExactly(minimo)
                }
                it("el segundo valor debe ser nulo.") {
                    service.unNumeroOrNull()?.let {
                        it.shouldBeNull()
                    }
                }
            }
        }
        describe( "Con una diferencia de 2 entre máximo y minimo")
        {
            val minimo = 5
            val maximo = 7
            val service = ServicioDeNumeros(minimo, maximo)
            it("el servicio debe contener los elementos {minimo, maximo-1}"){
                service.restoNumeros().shouldContainAll(minimo, maximo-1)
            }
        }
        describe( "Debe devolver números ") {
            it("exactamente todos los números entre mínimo y máximo") {
                val minimo = 1
                val maximo = 1550
                val service = ServicioDeNumeros(minimo, maximo)
                service.restoNumeros().shouldContainExactlyInAnyOrder(
                    List(maximo-minimo){ minimo+it}
                )
            }
            it("prueba") {
                val minimo = 0
                val maximo = 15
                val service = ServicioDeNumeros(minimo, maximo)
                println(service.siguientesNumeros(7))
                println(service.restoNumeros())
            }
        }

    }

})
