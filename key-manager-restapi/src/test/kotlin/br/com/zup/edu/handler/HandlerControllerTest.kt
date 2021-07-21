package br.com.zup.edu.handler

import io.grpc.Status
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class HandlerControllerTest {

    val request = HttpRequest.GET<Any>("/")
    val mensagem = "mensagem"

    @Test
    internal fun `deve retornar 404 quando for NOT_FOUND`() {
        val statusException = Status.NOT_FOUND.withDescription(this.mensagem).asRuntimeException()
        val handle: HttpResponse<Any> = HandlerController().handle(this.request, statusException)

        with(handle) {
            assertEquals(HttpStatus.NOT_FOUND, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    @Test
    internal fun `deve retornar 422 quando for ALREADY_EXISTS`() {
        val statusException = Status.ALREADY_EXISTS.withDescription(this.mensagem).asRuntimeException()

        val handle = HandlerController().handle(this.request, statusException)

        with(handle) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    @Test
    internal fun `deve retornar 400 quando for INVALID_ARGUMENT`() {
        val statusException = Status.INVALID_ARGUMENT.withDescription(this.mensagem).asRuntimeException()

        val handle = HandlerController().handle(this.request, statusException)

        with(handle) {
            assertEquals(HttpStatus.BAD_REQUEST, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    @Test
    internal fun `deve retornar 403 quando for PERMISSION_DENIED`() {
        val statusException = Status.PERMISSION_DENIED.withDescription(this.mensagem).asRuntimeException()

        val handle = HandlerController().handle(this.request, statusException)

        with(handle) {
            assertEquals(HttpStatus.FORBIDDEN, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    @Test
    internal fun `deve retornar 500 quando for desconhecido`() {
        val statusException = Status.UNKNOWN.withDescription(this.mensagem).asRuntimeException()

        val handle = HandlerController().handle(this.request, statusException)

        with(handle) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }
}