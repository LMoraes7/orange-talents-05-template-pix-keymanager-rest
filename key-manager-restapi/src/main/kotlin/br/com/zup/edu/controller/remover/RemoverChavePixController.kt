package br.com.zup.edu.controller.remover

import br.com.zup.edu.ChavePixRemoverRequest
import br.com.zup.edu.ChavePixRemoverServiceGrpc
import br.com.zup.edu.dominio.validacao.IsUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated

@Validated
@Controller("/api/pix/remover")
class RemoverChavePixController(
    private val grpcClient: ChavePixRemoverServiceGrpc.ChavePixRemoverServiceBlockingStub,
) {

    @Delete("/cliente/{clienteId}/pix/{pixIdInterno}")
    fun remover(
        @PathVariable @IsUUID clienteId: String,
        @PathVariable pixIdInterno: String,
    ): HttpResponse<Any> =
        ChavePixRemoverRequest.newBuilder()
            .setClienteId(clienteId)
            .setPixIdInterno(pixIdInterno)
            .build().run {
                grpcClient.remover(this)
                HttpResponse.noContent()
            }
}