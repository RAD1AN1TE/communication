import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import routes.models.Inventory


object NewOrderTable : Table("NewOrders"){
    val id  = integer("orderId").autoIncrement().uniqueIndex()
    val success = bool("success")
}

object ItemTable: Table("ItemTable"){
    val orderId = integer("orderId").references(NewOrderTable.id)
    val item = varchar("items", 50)
    val count = integer("count")
}

interface NewOrderRepository{
    fun getOrderDetails(id: Int):Iterable<Inventory>
}

class NewOrderRepositoryImpl(private val db:Database):NewOrderRepository{

    private fun toInventory(row: ResultRow) = Inventory(

        item = row[ItemTable.item],
        count = row[ItemTable.count],

    )
    override fun getOrderDetails(id:Int):Iterable<Inventory> = transaction(db) {
        val join = Join(
            NewOrderTable, ItemTable,
            onColumn = NewOrderTable.id, otherColumn = ItemTable.orderId,
            joinType = JoinType.INNER,
            additionalConstraint = { NewOrderTable.id eq id}
        )
        val result = join.selectAll().map(::toInventory)
        result
    }

}
