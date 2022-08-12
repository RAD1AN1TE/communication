package repository

import com.sun.org.apache.xpath.internal.operations.Bool
import kotlinx.coroutines.selects.select
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import routes.models.Inventory


object InventoryTable : Table("inventory") {

    val item = varchar("items", 50)
    val count = integer("count")

}

interface InventoryRepository : KoinComponent {

    fun save(inventory: Inventory):Inventory

    fun updateInventory(inventory: Inventory): Inventory
    fun getInventory():Iterable<Inventory>
}

class InventoryRepositoryImpl(private val db: Database) : InventoryRepository {


    private fun toInventory(row: ResultRow) = Inventory(
        item = row[InventoryTable.item],
        count = row[InventoryTable.count]
    )

    override fun save(inventory: Inventory): Inventory = transaction(db){

        val result = InventoryTable.select { InventoryTable.item eq inventory.item }.map(::toInventory)
        val notExist = result.isEmpty()
        println(notExist)
        if(notExist){
            insertItem(inventory)
        }
        else{
            inventory.count += result.first().count
            updateItem(inventory)
        }

        inventory
    }


    override fun updateInventory(inventory: Inventory): Inventory = transaction (db){
        val result = InventoryTable.select{InventoryTable.item eq inventory.item}.map(::toInventory)
        inventory.count = result.first().count - inventory.count
        updateItem(inventory)
        inventory
    }


    override fun getInventory(): Iterable<Inventory> = transaction(db) {
        val result = InventoryTable.selectAll().map(::toInventory)
        result
    }
    private fun insertItem(inventory: Inventory){
        InventoryTable.insert{
            it[item] = inventory.item
            it[count] = inventory.count
        }
    }
    private fun updateItem(inventory: Inventory){
        InventoryTable.update({InventoryTable.item eq inventory.item}){
            it[item] = inventory.item
            it[count] = inventory.count
        }
    }

}



