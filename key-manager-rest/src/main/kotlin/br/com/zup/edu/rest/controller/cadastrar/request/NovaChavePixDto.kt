package br.com.zup.edu.rest.controller.cadastrar.request

import br.com.zup.edu.NovaChavePixRequestGrpc
import br.com.zup.edu.TipoChaveGrpc
import br.com.zup.edu.TipoContaGrpc
import br.com.zup.edu.rest.dominio.enums.TipoChaveRequest
import br.com.zup.edu.rest.dominio.enums.TipoContaModel
import br.com.zup.edu.rest.dominio.validacao.ValidarChave
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ValidarChave
class NovaChavePixDto(
    @field:NotNull
    val tipoChave: TipoChaveRequest?,
    @field:Size(max = 77)
    val chave: String?,
    @field:NotNull
    val tipoConta: TipoContaModel?,

    ) {
    fun paraRequestGrpc(clienteId: UUID): NovaChavePixRequestGrpc =
        NovaChavePixRequestGrpc.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoChave(this.tipoChave?.atributoGrpc ?: TipoChaveGrpc.CHAVE_DESCONHECIDA)
            .setChave(this.chave)
            .setTipoConta(this.tipoConta?.atributoGrpc ?: TipoContaGrpc.CONTA_DESCONHECIDA)
            .build()

}
