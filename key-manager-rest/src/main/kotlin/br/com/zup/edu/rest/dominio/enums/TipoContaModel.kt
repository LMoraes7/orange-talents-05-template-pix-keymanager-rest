package br.com.zup.edu.rest.dominio.enums

import br.com.zup.edu.TipoContaGrpc

enum class TipoContaModel(val atributoGrpc: TipoContaGrpc) {

    CONTA_CORRENTE(TipoContaGrpc.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoContaGrpc.CONTA_POUPANCA);
}
