package client

import model.Operacion
import mu.KotlinLogging
import java.io.DataInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.Socket

private val log = KotlinLogging.logger { }
fun main() {
    val direccion: InetAddress
    val servidor: Socket
    val puerto = 6969
    val resultado: Double

    log.debug { "Operación a realizar: +, -, *, /" }
    val operador = readln()
    log.debug { "Primera cifra:" }
    val cifra1 = readln().toDouble()
    log.debug { "Segunda cifra:" }
    val cifra2 = readln().toDouble()

    val operacion = Operacion(operador, cifra1, cifra2)

    try {
        direccion = InetAddress.getLocalHost()
        servidor = Socket(direccion, puerto)
        log.debug { "Conectado al servidor" }

        val operacionCliente = ObjectOutputStream(servidor.getOutputStream())
        operacionCliente.writeObject(operacion)

        val recibirOperacion = DataInputStream(servidor.getInputStream())
        resultado = recibirOperacion.readDouble()
        log.debug { "Resultado recibido: $resultado" }

        operacionCliente.close()
        recibirOperacion.close()
        servidor.close()

        log.debug { "Desconectado" }

    } catch (e: Exception) {
        System.err.println("Servidor desconectado")
    }
}