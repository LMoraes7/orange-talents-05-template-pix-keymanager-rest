package br.com.zup.edu.rest.controller.consultar.todas

import br.com.zup.edu.ConsultarTodasChavePixRequestGrpc
import br.com.zup.edu.ConsultarTodasChavePixServiceGrpc
import br.com.zup.edu.rest.controller.consultar.todas.response.ChavePixResponseDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import java.util.*

@Controller("/api/consultar")
class ConsultarTodasChavePixController(
    private val grpcClient: ConsultarTodasChavePixServiceGrpc.ConsultarTodasChavePixServiceBlockingStub,
) {

    @Get("/cliente/{clienteId}")
    fun consultar(@PathVariable clienteId: UUID): HttpResponse<List<ChavePixResponseDto>> {
        val chavesPixResponse =
            ConsultarTodasChavePixRequestGrpc.newBuilder()
                .setClienteId(clienteId.toString())
                .build().run {
                    grpcClient.consultar(this)
                }.let {
                    it.chavesPixList.map { chave -> ChavePixResponseDto(chave) }
                }
        return HttpResponse.ok(chavesPixResponse)
    }
}