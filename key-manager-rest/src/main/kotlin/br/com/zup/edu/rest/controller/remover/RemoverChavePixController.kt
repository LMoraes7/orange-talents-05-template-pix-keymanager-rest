package br.com.zup.edu.rest.controller.remover

import br.com.zup.edu.RemoverChavePixRequestGrpc
import br.com.zup.edu.RemoverChavePixServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import java.util.*

@Controller("/api/remover")
class RemoverChavePixController(
    private val grpcClient: RemoverChavePixServiceGrpc.RemoverChavePixServiceBlockingStub,
) {

    @Delete("/cliente/{clienteId}/pix/{pixId}")
    fun remover(
        @PathVariable clienteId: UUID,
        @PathVariable pixId: UUID,
    ): HttpResponse<Any> =
        RemoverChavePixRequestGrpc.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()
            .run {
                grpcClient.remover(this)
            }.let {
                HttpResponse.noContent<Any>()
            }
}