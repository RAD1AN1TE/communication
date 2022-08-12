package routes


import NewOrderRepository
import NewOrderRepositoryImpl
import io.ktor.application.*
import io.ktor.features.*
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
    val newOrderRepository: NewOrderRepository by inject()

    routing {
        route("/v1") {

            post("/create") {
                val orderRequest = call.receive<Inventory>()
                val itemsAvailable = inventoryService.getInventory()
                orderService.createOrder(orderRequest, itemsAvailable)
                call.respond(HttpStatusCode.Accepted)
            }
            get("/order/{id}"){
                val orderId = call.parameters["id"]?.toIntOrNull()?:throw NotFoundException()
                val order = orderService.getOrderById(orderId)
                call.respond(order)
            }
            post("/order/edit"){
                val orderRequest = call.receive<Order>()
                orderService.editOrder(orderRequest)
                call.respond(HttpStatusCode.Accepted)
            }
            get("/orders/{id}"){
                val orderId = call.parameters["id"]?.toIntOrNull()?:throw NotFoundException()
                val order = newOrderRepository.getOrderDetails(orderId)
                call.respond(order)
            }
        }
    }

}