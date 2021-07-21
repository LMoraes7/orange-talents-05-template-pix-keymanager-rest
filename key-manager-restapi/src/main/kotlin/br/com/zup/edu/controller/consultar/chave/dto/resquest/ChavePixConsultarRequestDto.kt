package br.com.zup.edu.controller.consultar.chave.dto.resquest

import br.com.zup.edu.ChavePixUnicaConsultarRequest
import br.com.zup.edu.FiltroPoPixId
import br.com.zup.edu.dominio.validacao.IsUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class ChavePixConsultarRequestDto(
    @field:NotBlank
    @field:IsUUID
    val clienteId: String?,
    @field:NotBlank
    val pixId: String?
) {

    fun paraGrpcRequest(): ChavePixUnicaConsultarRequest =
        ChavePixUnicaConsultarRequest.newBuilder()
            .setPixId(
                FiltroPoPixId.newBuilder()
                    .setClienteId(this.clienteId)
                    .setPixId(this.pixId)
                    .build()
            )
            .build()
}
