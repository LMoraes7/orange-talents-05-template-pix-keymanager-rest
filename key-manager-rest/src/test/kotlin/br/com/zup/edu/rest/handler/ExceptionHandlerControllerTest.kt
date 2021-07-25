package br.com.zup.edu.rest.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ExceptionHandlerControllerTest {

    val mensagem = "mensagem"
    val requestHttp = HttpRequest.GET<Any>("")

    @Test
    internal fun `deve retornar 400`() {
        val handle =
            ExceptionHandlerController().handle(
                requestHttp,
                this.criarException(Status.INVALID_ARGUMENT, mensagem)
            )

        with(handle) {
            assertEquals(HttpStatus.BAD_REQUEST, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    @Test
    internal fun `deve retornar 422`() {
        val handle = ExceptionHandlerController().handle(
            this.requestHttp,
            this.criarException(Status.ALREADY_EXISTS, this.mensagem)
        )

        with(handle) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    @Test
    internal fun `deve retornar 404`() {
        val handle = ExceptionHandlerController().handle(
            this.requestHttp,
            this.criarException(Status.NOT_FOUND, this.mensagem)
        )

        with(handle) {
            assertEquals(HttpStatus.NOT_FOUND, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    @Test
    internal fun `deve retornar 403`() {
        val handle = ExceptionHandlerController().handle(
            this.requestHttp,
            this.criarException(Status.PERMISSION_DENIED, this.mensagem)
        )

        with(handle) {
            assertEquals(HttpStatus.FORBIDDEN, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    @Test
    internal fun `deve retornar 500`() {
        val handle = ExceptionHandlerController().handle(
            this.requestHttp,
            this.criarException(Status.INTERNAL, this.mensagem)
        )

        with(handle) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, this.status)
            assertNotNull(this.body())
            assertEquals((this.body() as JsonError).message, mensagem)
        }
    }

    private fun criarException(status: Status, mensagem: String):
            StatusRuntimeException =
        status.withDescription(mensagem).asRuntimeException()
}