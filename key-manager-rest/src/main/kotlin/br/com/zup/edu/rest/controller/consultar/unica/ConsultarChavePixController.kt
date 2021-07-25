package br.com.zup.edu.rest.controller.consultar.unica

import br.com.zup.edu.ConsultarChavePixRequestGrpc
import br.com.zup.edu.ConsultarChavePixResponseGrpc
import br.com.zup.edu.ConsultarChavePixServiceGrpc
import br.com.zup.edu.rest.controller.consultar.unica.response.DetalhesChavePixResponseDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.constraints.Size

@Validated
@Controller("/api/consultar")
class ConsultarChavePixController(
    private val grpcClient: ConsultarChavePixServiceGrpc.ConsultarChavePixServiceBlockingStub,
) {

    @Get("/cliente/{clienteId}/pix/{pixId}")
    fun consultarPorClienteIdEhPixId(
        @PathVariable clienteId: UUID,
        @PathVariable pixId: UUID,
    ): HttpResponse<DetalhesChavePixResponseDto> {
        val chavePixResponse = ConsultarChavePixRequestGrpc.newBuilder()
            .setPixId(
                ConsultarChavePixRequestGrpc.FiltroPoPixId.newBuilder()
                    .setClienteId(clienteId.toString())
                    .setPixId(pixId.toString())
                    .build()
            ).build()
            .run {
                grpcClient.consultar(this)
            }.converterParaChavePixResponseDto()

        return HttpResponse.ok(chavePixResponse)
    }

    @Get("/chave/{chave}")
    fun consultarPorChave(
        @PathVariable @Size(max = 77) chave: String,
    ): HttpResponse<DetalhesChavePixResponseDto> {
        val chavePixResponse = ConsultarChavePixRequestGrpc.newBuilder()
            .setChave(chave)
            .build()
            .run {
                grpcClient.consultar(this)
            }.converterParaChavePixResponseDto()

        return HttpResponse.ok(chavePixResponse)
    }
}

private fun ConsultarChavePixResponseGrpc.converterParaChavePixResponseDto() =
    DetalhesChavePixResponseDto(this)
