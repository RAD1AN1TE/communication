package client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.*
import routes.models.Order


interface PaymentApi{
    suspend fun getStatus(): String
}
class PaymentApiImpl(private val client: HttpClient):PaymentApi{

    override suspend fun getStatus(): String {
        
        val response1: HttpResponse = client.get("http://localhost:8081/payment/status")

        val message:String = response1.readText()
        return message
    }

}