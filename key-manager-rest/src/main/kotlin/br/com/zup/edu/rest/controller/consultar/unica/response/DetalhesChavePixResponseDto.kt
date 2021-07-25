package br.com.zup.edu.rest.controller.consultar.unica.response

import br.com.zup.edu.ConsultarChavePixResponseGrpc
import br.com.zup.edu.ConsultarChavePixResponseGrpc.*

class DetalhesChavePixResponseDto(
    response: ConsultarChavePixResponseGrpc,
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
            response.clienteId
    val tipoChave = response.tipoChave
    val chave = response.chave
    val titular = TitularResponseDto(response.titular)
    val conta = ContaResponseDto(response.conta)
    val registradaEm = response.registradaEm
}

class ContaResponseDto(response: Conta) {
    val instituicao: String = response.instituicao
    val agencia: String = response.agencia!!
    val numero: String = response.numero
    val tipoConta: String = response.tipoConta
}

class TitularResponseDto(
    response: Titular,
) {
    val nome: String = response.nome
    val cpf: String = response.cpf
}