package es.edu.iesra.daw.revilofe.bingo

import io.kotest.core.spec.style.DescribeSpec

/*
Crear un generador de cartones, usando el patrón singleton. Al generador de cartones le podremos pedir:
Un cartón, y lo devolverá ya relleno.
Un conjunto de cartones, devolviendo una lista con los cartones ya rellenos.

Los cartones se generará según las indicaciones siguientes:
Cada cartón tendrá un identificador.
Cada cartón cuenta con cinco columnas y cinco filas, con números generados al azar.
    La primera columna incluye 4 números del 1 al 15
    la segunda desde el 16 al 30
    la tercera desde el 31 al 45
    el cuarto desde el 46 al 60 y la quinta desde el 61 al 75.

Las columnas contienen cuatro números porque las posiciones (0,0) (1,1) (2,2) (3,3) (4,4) estarán vacías.
 */
class GeneradorCartonesTest : DescribeSpec(){

}