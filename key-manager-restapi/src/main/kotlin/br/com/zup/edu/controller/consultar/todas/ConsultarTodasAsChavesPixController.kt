package br.com.zup.edu.controller.consultar.todas

import br.com.zup.edu.ChavePixTodasConsultarRequest
import br.com.zup.edu.ChavePixTodasConsultarResponse
import br.com.zup.edu.ChavePixTodasConsultarServiceGrpc
import br.com.zup.edu.controller.consultar.todas.dto.response.ChavePixTodasResponseDto
import br.com.zup.edu.dominio.validacao.IsUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated

@Validated
@Controller("/api/pix/consultar/todas")
class ConsultarTodasAsChavesPixController(
    private val grpcClient: ChavePixTodasConsultarServiceGrpc.ChavePixTodasConsultarServiceBlockingStub,
) {

    @Get("/{clienteId}")
    fun consultar(@PathVariable @IsUUID clienteId: String): HttpResponse<Any> {
        val response: ChavePixTodasConsultarResponse = ChavePixTodasConsultarRequest.newBuilder()
            .setClienteId(clienteId)
            .build().run {
                grpcClient.consultarTodas(this)
            }

        return response.paraResponseDto().run {
            HttpResponse.ok(this)
        }
    }
}

private fun ChavePixTodasConsultarResponse.paraResponseDto():
        ChavePixTodasResponseDto = ChavePixTodasResponseDto(this)