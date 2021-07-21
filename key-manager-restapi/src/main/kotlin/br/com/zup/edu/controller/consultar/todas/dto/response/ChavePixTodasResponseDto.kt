package br.com.zup.edu.controller.consultar.todas.dto.response

import br.com.zup.edu.ChavePixTodasConsultarResponse

class ChavePixTodasResponseDto(
    response: ChavePixTodasConsultarResponse,
) {

    val clienteId = response.clienteId
    val chaves = response.chavesList.map { chave -> ChavePixResponseDto(chave) }
}
