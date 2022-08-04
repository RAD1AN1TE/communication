package routes


import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import routes.models.Inventory
import routes.models.Order
import services.InventoryService
import services.OrderService

fun Application.orderRoutes() {

    val inventoryService: InventoryService by inject()
    val orderService: OrderService by inject()

    routing {
        route("/v1") {

            post("/create") {
                val orderRequest = call.receive<Inventory>()
                val itemsAvailable = inventoryService.save(orderRequest)
                orderService.createOrder(orderRequest, itemsAvailable)
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }

}