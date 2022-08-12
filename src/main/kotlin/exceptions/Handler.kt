package exceptions

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*

fun StatusPages.Configuration.addExceptions() {

    exception<NoSuchElementException> { cause ->
        val genericExcep = cause.toGenericException()
        call.respond(genericExcep.status, genericExcep)
    }

}