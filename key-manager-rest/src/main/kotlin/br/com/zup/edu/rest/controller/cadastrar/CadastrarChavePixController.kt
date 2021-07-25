package br.com.zup.edu.rest.controller.cadastrar

import br.com.zup.edu.CadastrarChavePixServiceGrpc
import br.com.zup.edu.rest.controller.cadastrar.request.NovaChavePixDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import java.util.*

@Controller("/api/cadastrar")
class CadastrarChavePixController(
    private val grpcClient: CadastrarChavePixServiceGrpc.CadastrarChavePixServiceBlockingStub,
) {

    @Post("/cliente/{clienteId}")
    fun cadastrar(
        @PathVariable clienteId: UUID,
        @Body request: NovaChavePixDto,
    ): HttpResponse<Any> {
        val responseGrpc = this.grpcClient.cadastrar(request.paraRequestGrpc(clienteId))

        return UriBuilder.of("/pix/consultar/cliente/{clienteId}/pix/{pixId}")
            .expand(mutableMapOf(
                Pair("clienteId", responseGrpc.clienteId),
                Pair("pixId", responseGrpc.pixIdInterno)
            )).let {
                HttpResponse.created(it)
            }
    }
}