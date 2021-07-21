package br.com.zup.edu.controller.remover.dto.request

import br.com.zup.edu.ChavePixRemoverRequest
import br.com.zup.edu.dominio.validacao.IsUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
class ChavePixRemoverRequestDto(
    @field:NotBlank
    @field:IsUUID
    val clienteId: String?
) {
    fun paraGrpcRequest(pixIdInterno: String): ChavePixRemoverRequest =
        ChavePixRemoverRequest.newBuilder()
            .setClienteId(this.clienteId!!)
            .setPixIdInterno(pixIdInterno)
            .build()

}
