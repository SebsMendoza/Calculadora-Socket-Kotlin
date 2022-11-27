package server

import model.Operacion
import mu.KotlinLogging
import java.io.DataOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.net.Socket
import java.util.logging.Level
import java.util.logging.Logger

private val log = KotlinLogging.logger { }

class GestionCliente(private val s: Socket) : Thread() {
    var resultado: Double = 0.0
    override fun run() {
        try {
            s.setSoLinger(true, 10)
            val datos = ObjectInputStream(s.getInputStream())
            val operacion = datos.readObject() as Operacion
            when (operacion.operador) {
                "+" -> resultado = operacion.num1 + operacion.num2
                "-" -> resultado = operacion.num1 - operacion.num2
                "*" -> resultado = operacion.num1 * operacion.num2
                "/" -> {
                    if (operacion.num2 > 0.0) {
                        resultado = operacion.num1 / operacion.num2
                    } else {
                        resultado = 0.0
                    }
                }
            }
            log.debug { "Resultado a enviar: $resultado" }
            val sendResult = DataOutputStream(s.getOutputStream())
            sendResult.writeDouble(resultado)

            datos.close()
            sendResult.close()
            s.close()
        } catch (ex: IOException) {
            Logger.getLogger(GestionCliente::class.java.name).log(Level.SEVERE, null, ex)
        }
    }
}