package br.com.zup.edu.controller.consultar.todas.dto.response

import br.com.zup.edu.ChavePixTodasConsultarResponse

class ChavePixResponseDto(
    chave: ChavePixTodasConsultarResponse.ChavePix?
) {

    val pixId = chave?.pixId
    val tipoChave = chave?.tipoChave
    val chave = chave?.tipoChave
    val tipoConta = chave?.tipoConta
    val dataRegistro = chave?.dataRegistro
}
