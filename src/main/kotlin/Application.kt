

import exceptions.addExceptions
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.core.context.startKoin
import routes.inventoryRoutes
import routes.orderRoutes

fun main() {

    embeddedServer(factory = Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)

}

fun Application.module() {

    startKoin{
        modules(inventoryModule)
    }

    initDB()

    install(ContentNegotiation){
        jackson {  }
    }

    install(StatusPages) {
        addExceptions()
    }

    inventoryRoutes()
    orderRoutes()

}




