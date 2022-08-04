package services

import org.koin.core.component.KoinComponent
import repository.OrderRepository
import routes.models.Inventory


interface OrderService : KoinComponent {

    fun createOrder(order: Inventory, itemsAvailable: Inventory ): Int

}

class OrderServiceImpl(
    private val orderRepository: OrderRepository
) : OrderService{


    override fun createOrder(order: Inventory, itemsAvailable: Inventory): Int {
        try{
            checkValidOrder(order.pencil, itemsAvailable.pencil)
        }
        catch (ex: Exception){
            println(ex.message)
        }
        try{
            checkValidOrder(order.pen, itemsAvailable.pen)
        }
        catch (ex: Exception){
            println(ex.message)
        }
        try{
            checkValidOrder(order.book, itemsAvailable.book)
        }
        catch (ex: Exception){
            println(ex.message)
        }

        return orderRepository.createOrder(order)

    }
    private fun checkValidOrder(input1: Int, input2: Int){
        if(input1>input2)
            throw Exception("Order is not valid...Enter a valid order")

    }

}
