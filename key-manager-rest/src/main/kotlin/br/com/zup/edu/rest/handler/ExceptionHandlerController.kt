package br.com.zup.edu.rest.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class ExceptionHandlerController : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>?, ex: StatusRuntimeException?):
            HttpResponse<Any> {

        val codigo = ex?.status?.code
        val descricao = ex?.status?.description ?: ""

        val pair: Pair<HttpStatus, String> =
            when (codigo) {
                Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, descricao)
                Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, descricao)
                Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, descricao)
                Status.PERMISSION_DENIED.code -> Pair(HttpStatus.FORBIDDEN, descricao)
                else -> Pair(HttpStatus.INTERNAL_SERVER_ERROR, descricao)
            }

        return HttpResponse.status<JsonError>(pair.first).body(JsonError(pair.second))
    }
}