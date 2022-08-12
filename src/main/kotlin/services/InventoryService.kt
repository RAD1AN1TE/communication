package services

import org.koin.core.component.KoinComponent
import repository.InventoryRepository
import routes.models.Inventory

interface InventoryService : KoinComponent{

    fun save(inventory: Inventory): Inventory
    fun getInventory(): Iterable<Inventory>
}

class InventoryServiceImpl(
    private val inventoryRepository: InventoryRepository
) : InventoryService{

    override fun save(inventory: Inventory): Inventory {

        try{
            checkInputNumber(inventory.count)
        }
        catch (ex: Exception){
            inventory.count = 0
            println(ex.message)
        }
        return inventoryRepository.save(inventory)
    }

    override fun getInventory(): Iterable<Inventory> {
        return inventoryRepository.getInventory()
    }

    private fun checkInputNumber(input: Int){
        if(input<0)
            throw Exception("Input is not valid...Enter a valid number")
    }
}