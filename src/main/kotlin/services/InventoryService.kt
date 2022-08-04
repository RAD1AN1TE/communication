package services

import org.koin.core.component.KoinComponent
import repository.InventoryRepository
import routes.models.Inventory

interface InventoryService : KoinComponent{

    fun save(inventory: Inventory): Inventory
    fun getInventory():Inventory
}

class InventoryServiceImpl(
    private val inventoryRepository: InventoryRepository
) : InventoryService{

    override fun save(inventory: Inventory): Inventory {

        try{
            checkInputNumber(inventory.pencil)
        }
        catch (ex: Exception){
            inventory.pencil = 0
            println(ex.message)
        }
        try{
            checkInputNumber(inventory.pen)
        }
        catch (ex: Exception){
            inventory.pen = 0
            println(ex.message)
        }
        try{
            checkInputNumber(inventory.book)
        }
        catch (ex: Exception){
            inventory.book = 0
            println(ex.message)
        }
        return inventoryRepository.save(inventory)

    }

    override fun getInventory(): Inventory {
        return inventoryRepository.getInventory()
    }

    private fun checkInputNumber(input: Int){
        if(input<0)
            throw Exception("Input is not valid...Enter a valid number")
    }


}