package br.com.zup.edu.controller.cadastrar.dto.request

import br.com.zup.edu.ChavePixCadastrarRequest
import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoConta
import br.com.zup.edu.dominio.validacao.ChaveIsValid
import br.com.zup.edu.dominio.validacao.IsUUID
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ChaveIsValid
class ChavePixRequest(
    @field:NotNull
    @field:IsUUID
    val clienteId: String?,
    @field:NotNull
    val tipoChave: TipoChaveRequest?,
    @field:Size(max = 77)
    val chave: String?,
    @field:NotNull
    val tipoConta: TipoContaRequest?,
) {

    fun paraGrpcRequest(): ChavePixCadastrarRequest =
        ChavePixCadastrarRequest.newBuilder()
            .setClienteId(this.clienteId)
            .setTipoChave(this.tipoChave?.atributoGrpc ?: TipoChave.CHAVE_DESCONHECIDA)
            .setChave(this.chave ?: "")
            .setTipoConta(this.tipoConta?.atributoGrpc ?: TipoConta.CONTA_DESCONHECIDA)
            .build()
}

enum class TipoChaveRequest(val atributoGrpc: TipoChave) {

    CPF(TipoChave.CPF) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank())
                return false

            return CPFValidator()
                .run {
                    initialize(null)
                    isValid(chave, null)
                }
        }
    },
    CNPJ(TipoChave.CNPJ) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank())
                return false

            return CNPJValidator()
                .run {
                    initialize(null)
                    isValid(chave, null)
                }

        }
    },
    CELULAR(TipoChave.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank())
                return false

            return chave.matches("^\\+[1-9][0-9]\\d{1,14}$".toRegex())
        }
    },
    EMAIL(TipoChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank())
                return false

            return EmailValidator()
                .run {
                    initialize(null)
                    isValid(chave, null)
                }
        }
    },
    ALEATORIA(TipoChave.ALEATORIA) {
        override fun valida(chave: String?): Boolean = chave.isNullOrBlank()
    };

    abstract fun valida(chave: String?): Boolean
}

enum class TipoContaRequest(val atributoGrpc: TipoConta) {
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA);
}