package services

import client.PaymentApi
import org.koin.core.component.KoinComponent
import repository.InventoryRepository
import repository.OrderRepository
import routes.models.Inventory
import routes.models.Order


interface OrderService : KoinComponent {

    suspend fun createOrder(order: Inventory, itemsAvailable: Iterable<Inventory> )

    fun getOrderById(orderId:Int): Order

    fun editOrder(order: Order) : Order

}

class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val inventoryRepository: InventoryRepository,
    private val paymentApi: PaymentApi
) : OrderService{


    override suspend fun createOrder(order: Inventory, itemsAvailable: Iterable<Inventory>) {
        try{
            checkValidOrder(order, itemsAvailable)
            val paymentStatus = paymentApi.getStatus().toBoolean()
            orderRepository.createOrder(order, paymentStatus)
            inventoryRepository.updateInventory(order)
        }
        catch (ex: Exception){
            println(ex.message)
        }
    }

    override fun getOrderById(orderId: Int): Order {
        checkValidOrderNumber(orderId)
        val order = orderRepository.getOrderById(orderId)!!
        return order
    }

    override fun editOrder(order: Order): Order {
//        checkValidOrderNumber(order.orderId)
        val order = orderRepository.editOrder(order)?:throw NoSuchElementException("Order id: ${order.orderId} does not exists")
        return order

    }

    private fun checkValidOrder(order: Inventory, itemsAvailable: Iterable<Inventory>){
        for(i:Inventory in itemsAvailable){
            if(i.item == order.item){
                if(i.count<order.count)
                    throw Exception("Out of Stock")
            }
        }
    }
    private fun checkValidOrderNumber(orderId: Int){
        val notExist = orderRepository.checkOrder(orderId)
        if(notExist)
            throw NoSuchElementException("Order id: $orderId does not exists")
    }

}
