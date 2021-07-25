package br.com.zup.edu.rest.dominio.enums

import br.com.zup.edu.TipoChaveGrpc
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator

enum class TipoChaveRequest(val atributoGrpc: TipoChaveGrpc) {

    CPF(TipoChaveGrpc.CPF) {
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
    CNPJ(TipoChaveGrpc.CNPJ) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank())
                return false

            return CNPJValidator()
                .run {
                    initialize(null)
                    isValid(chave, null)
                }
        }
    },
    CELULAR(TipoChaveGrpc.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank())
                return false

            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL(TipoChaveGrpc.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if(chave.isNullOrBlank())
                return false

            return EmailValidator()
                .run {
                    initialize(null)
                    isValid(chave, null)
                }
        }
    },
    ALEATORIA(TipoChaveGrpc.ALEATORIA) {
        override fun valida(chave: String?): Boolean = chave.isNullOrBlank()
    };

    abstract fun valida(chave: String?): Boolean
}