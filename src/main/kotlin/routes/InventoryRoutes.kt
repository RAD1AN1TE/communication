package routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import routes.models.Inventory
import services.InventoryService

fun Application.inventoryRoutes() {

    val service: InventoryService by inject()

    routing {
        route("/v1") {
            get("/items"){
                val allItems = service.getInventory()
                call.respond(allItems)
            }
            post("/save") {
                val inventoryRequest = call.receive<Inventory>()
                service.save(inventoryRequest)
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }

}