package model

import java.io.Serializable

data class Operacion(
    val operador: String,
    val num1: Double,
    val num2: Double
) : Serializable