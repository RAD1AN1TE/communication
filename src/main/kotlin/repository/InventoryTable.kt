package repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import routes.models.Inventory


object InventoryTable : Table("inventory") {

    val pencil = integer("pencils")
    val pen = integer("pens")
    val book = integer("books")

}

interface InventoryRepository : KoinComponent {

    fun save(inventory: Inventory): Inventory
    fun getInventory():Inventory
}

class InventoryRepositoryImpl(private val db: Database) : InventoryRepository {

    private fun toInventory(row: ResultRow) = Inventory(
        pencil = row[InventoryTable.pencil],
        pen = row[InventoryTable.pen],
        book = row[InventoryTable.book]
    )

    override fun save(inventory: Inventory): Inventory = transaction(db) {
            val result = InventoryTable.selectAll().map(::toInventory)
            if(result.isNotEmpty())
            {
                inventory.pencil += result.last().pencil
                inventory.pen += result.last().pen
                inventory.book += result.last().book
            }
            InventoryTable.insert {
                it[pencil] =  inventory.pencil
                it[pen] =  inventory.pen
                it[book] =  inventory.book
            }
        inventory
    }

    override fun getInventory(): Inventory = transaction(db) {
            val result = InventoryTable.selectAll().map(::toInventory)
            result.last()
    }

}



