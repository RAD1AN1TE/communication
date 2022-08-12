package exceptions

import io.ktor.http.*

data class GenericException(val message: String?, val status: HttpStatusCode)

fun NoSuchElementException.toGenericException() = GenericException(this.message, HttpStatusCode.NotFound)