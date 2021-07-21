package br.com.zup.edu.controller.consultar.chave.dto.response

import br.com.zup.edu.ChavePixUnicaConsultarResponse
import br.com.zup.edu.ContaAssociada
import br.com.zup.edu.Titular
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_NULL)
class ChavePixDetalhadaResponseDto(
    response: ChavePixUnicaConsultarResponse,
) {
    val pixId: String? =
        if (response.pixId.isNullOrBlank())
            null
        else
            response.pixId
    val clienteId: String? =
        if (response.clienteId.isNullOrBlank())
            null
        else
            response.pixId
    val tipoChave: String = response.tipoChave
    val chave: String = response.chave
    val titular = TitularResponseDto(response.titular)
    val contaAssociada = ContaAssociadaResponseDto(response.contaAssociada)
    val dataRegistro = response.dataRegistro
}

class ContaAssociadaResponseDto(contaAssociada: ContaAssociada?) {
    val instituicao = contaAssociada?.instituicao
    val agencia = contaAssociada?.agencia
    val numero = contaAssociada?.numero
    val tipoConta = contaAssociada?.tipoConta
}

class TitularResponseDto(titular: Titular?) {
    val nome = titular?.nome
    val cpf = titular?.cpf
}
