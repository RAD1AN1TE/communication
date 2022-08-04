package repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import routes.models.Inventory
import routes.models.Order

object OrderTable: Table("orders"){

    val orderId = integer("orderId").autoIncrement()
    val pencil = integer("pencils")
    val pen = integer("pens")
    val book = integer("books")

}

interface OrderRepository: KoinComponent{

    fun createOrder(inventory: Inventory):Int
    fun getOrder(order: Order): Order
    fun editOrder(order: Order):Order

}

class OrderRepositoryImpl(private val db: Database) : OrderRepository {

    private fun toOrder(row: ResultRow) = Order(
        orderId = row[OrderTable.orderId],
        pencil = row[OrderTable.pencil],
        pen = row[OrderTable.pen],
        book = row[OrderTable.book]
    )
    override fun createOrder(inventory: Inventory):Int =  transaction(db) {
        OrderTable.insert {
            it[pencil] =  inventory.pencil
            it[pen] =  inventory.pen
            it[book] =  inventory.book
        }
         val actual = InventoryTable.selectAll().map(::toOrder)
        actual.last().orderId
     }

    override fun getOrder(order: Order): Order {
        TODO("Not yet implemented")
    }

    override fun editOrder(order: Order): Order {
        TODO("Not yet implemented")
    }


}








