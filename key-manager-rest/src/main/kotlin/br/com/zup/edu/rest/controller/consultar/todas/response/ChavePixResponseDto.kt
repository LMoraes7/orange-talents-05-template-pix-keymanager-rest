package br.com.zup.edu.rest.controller.consultar.todas.response

import br.com.zup.edu.ConsultarTodasChavePixResponseGrpc

class ChavePixResponseDto(
    response: ConsultarTodasChavePixResponseGrpc.DetalhesChavePix
) {
    val pixId = response.pixId
    val clienteId = response.clienteId
    val tipoChave = response.tipoChave
    val chave = response.chave
    val tipoConta = response.tipoConta
    val registradaEm = response.registradaEm
}
