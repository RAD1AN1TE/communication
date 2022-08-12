package repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import routes.models.Inventory
import routes.models.Order

object OrderTable: Table("orders"){

    val orderId = integer("orderId").autoIncrement()
    val item = varchar("items", 20)
    val count = integer("count")
    val success = bool("success")
}

interface OrderRepository: KoinComponent{

    fun createOrder(inventory: Inventory, paymentStatus: Boolean)
    fun getOrderById(orderId: Int): Order?
    fun editOrder(order: Order): Order?

    fun checkOrder(orderId: Int):Boolean
}

class OrderRepositoryImpl(private val db: Database) : OrderRepository {

    private fun toOrder(row: ResultRow) = Order(
        orderId = row[OrderTable.orderId],
        item = row[OrderTable.item],
        count = row[OrderTable.count],
        success = row[OrderTable.success]
    )
    override fun createOrder(order: Inventory, paymentStatus: Boolean) {

        transaction(db) {
            OrderTable.insert {
                it[item] = order.item
                it[count] = order.count
                it[success] = paymentStatus
            }
//        val actual = OrderTable.selectAll().map(::toOrder)
//        actual.last().orderId
        }
    }

    override fun getOrderById(orderId: Int): Order? = transaction(db) {
        val result = OrderTable.select{OrderTable.orderId eq orderId}.map(::toOrder)
        result.first()
    }

    override fun editOrder(order: Order): Order? = transaction(db){
        OrderTable.update({OrderTable.orderId eq order.orderId!!}){
            it[item] = order.item
            it[count]  = order.count
        }
        order
    }

    override fun checkOrder(orderId: Int): Boolean = transaction(db){
        val result = OrderTable.select { OrderTable.orderId eq orderId }.map(::toOrder)
        val notExist = result.isEmpty()
        notExist
    }


}








