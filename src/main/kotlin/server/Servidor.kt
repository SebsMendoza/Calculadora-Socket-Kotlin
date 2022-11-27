package server

import mu.KotlinLogging
import java.net.ServerSocket
import java.net.Socket

private val log = KotlinLogging.logger { }
fun main() {
    var servidor: ServerSocket
    var cliente: Socket
    val puerto = 6969

    log.debug { "Servidor activo esperando conexiones..." }

    try {
        servidor = ServerSocket(puerto)
        cliente = servidor.accept()
        val gc = GestionCliente(cliente)
        gc.start()
        println("Servidor finalizado")
        servidor.close()

    } catch (e: Exception) {
        e.printStackTrace()
    }
}