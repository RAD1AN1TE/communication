package routes.models

data class Order(
    var orderId: Int,
    var item: String,
    var count: Int,
    var success: Boolean,
)


