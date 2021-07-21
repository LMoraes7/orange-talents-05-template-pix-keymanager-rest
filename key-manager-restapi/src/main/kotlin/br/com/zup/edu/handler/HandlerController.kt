package br.com.zup.edu.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class HandlerController : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>?, exception: StatusRuntimeException?):
            HttpResponse<Any> {

        val codigo = exception?.status?.code
        val descricao = exception?.status?.description ?: ""

        val (httpStatus, mensagem) =
            when (codigo) {
                Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, descricao)
                Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, descricao)
                Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, descricao)
                Status.PERMISSION_DENIED.code -> Pair(HttpStatus.FORBIDDEN, descricao)
                else -> Pair(HttpStatus.INTERNAL_SERVER_ERROR, descricao)
            }

        return HttpResponse
            .status<JsonError>(httpStatus)
            .body(JsonError(mensagem))
    }
}