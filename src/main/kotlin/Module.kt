

import client.PaymentApi
import client.PaymentApiImpl
import org.koin.dsl.module
import repository.InventoryRepository
import repository.InventoryRepositoryImpl
import repository.OrderRepository
import repository.OrderRepositoryImpl
import services.InventoryService
import services.InventoryServiceImpl
import services.OrderService
import services.OrderServiceImpl


val inventoryModule = module{

    single{DbConnection.dataBase}
    single{Http.client}

    single<PaymentApi>{PaymentApiImpl(get())}
    single<InventoryRepository>{InventoryRepositoryImpl(get())}
    single<InventoryService>{ InventoryServiceImpl(get()) }
    single<OrderRepository>{ OrderRepositoryImpl(get()) }
    single<OrderService>{ OrderServiceImpl(get(), get(), get()) }
    single<NewOrderRepository>{ NewOrderRepositoryImpl(get())}


}