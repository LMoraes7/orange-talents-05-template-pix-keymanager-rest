package br.com.zup.edu.controller.consultar.chave

import br.com.zup.edu.ChavePixUnicaConsultarRequest
import br.com.zup.edu.ChavePixUnicaConsultarResponse
import br.com.zup.edu.ChavePixUnicaConsultarServiceGrpc
import br.com.zup.edu.FiltroPoPixId
import br.com.zup.edu.controller.consultar.chave.dto.response.ChavePixDetalhadaResponseDto
import br.com.zup.edu.controller.consultar.chave.dto.resquest.ChavePixConsultarRequestDto
import br.com.zup.edu.dominio.validacao.IsUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/api/pix/consultar")
class ConsultarChavePixController(
    private val grpcClient: ChavePixUnicaConsultarServiceGrpc.ChavePixUnicaConsultarServiceBlockingStub,
) {

    @Get("/cliente/{clienteId}/pix/{pixId}")
    fun consultarPorClienteEPixId(
        @PathVariable @IsUUID clienteId: String,
        @PathVariable pixId: String,
    ): HttpResponse<Any> {
        val response = ChavePixUnicaConsultarRequest.newBuilder()
            .setPixId(
                FiltroPoPixId.newBuilder()
                    .setClienteId(clienteId)
                    .setPixId(pixId)
                    .build()
            ).build().run {
                grpcClient.consultarUnica(this)
            }

        return converterParaDtoERetornarResposta(response)
    }

    @Get("/chave/{chave}")
    fun consultarPorChave(@PathVariable chave: String): HttpResponse<Any> {
        val response = ChavePixUnicaConsultarRequest.newBuilder()
            .setChave(chave)
            .build().run {
                grpcClient.consultarUnica(this)
            }

        return converterParaDtoERetornarResposta(response)
    }

    private fun converterParaDtoERetornarResposta(response: ChavePixUnicaConsultarResponse): HttpResponse<Any> =
        response.paraChavePixResponseDto().run { HttpResponse.ok(this) }
}

private fun ChavePixUnicaConsultarResponse.paraChavePixResponseDto():
        ChavePixDetalhadaResponseDto = ChavePixDetalhadaResponseDto(this)

