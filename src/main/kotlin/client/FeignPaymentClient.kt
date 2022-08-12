package client
//
//
//import feign.Feign
//import feign.Headers
//import feign.RequestLine
//import feign.httpclient.ApacheHttpClient
//import feign.jackson.JacksonDecoder
//import feign.jackson.JacksonEncoder
//
//
//
//interface Payment{
//
//    @RequestLine("GET /payment/status")
//    @Headers("Content-Type: text/plain; charset=UTF-8")
//    fun getStatus():String
//
//}
//
//interface PaymentClient{
//
//    fun getPaymentStatus(status: String): String
//}
//
////val paymentClient:PaymentClient = Feign.builder().client().encoder(GsonEncoder())
//
//class PaymentClientImpl: PaymentClient{
//
//    private val url = "http://localhost:8081"
//
//    override fun getPaymentStatus(status: String): String {
//        val paymentApi = Feign.builder().client(ApacheHttpClient()).encoder(JacksonEncoder()).decoder(JacksonDecoder()).target(Payment::class.java, url)
//        return paymentApi.getStatus()
//    }
//
//}
