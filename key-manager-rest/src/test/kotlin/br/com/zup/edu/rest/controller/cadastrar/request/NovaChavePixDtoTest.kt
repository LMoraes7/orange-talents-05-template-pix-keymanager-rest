package br.com.zup.edu.rest.controller.cadastrar.request

import br.com.zup.edu.rest.dominio.enums.TipoChaveRequest
import br.com.zup.edu.rest.dominio.enums.TipoContaModel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.validation.Validator

@MicronautTest
internal class NovaChavePixDtoTest(
    private val validator: Validator,
) {

    @Test
    internal fun `deve validar`() {
        val dto = NovaChavePixDto(
            tipoChave = TipoChaveRequest.EMAIL,
            chave = "email@email.com",
            tipoConta = TipoContaModel.CONTA_CORRENTE
        )

        val errors = this.validator.validate(dto)

        assertTrue(errors.isEmpty())
    }

    @Test
    internal fun `nao deve validar enumeracoes nulas`() {
        var dto = NovaChavePixDto(
            tipoChave = null,
            chave = "email@email.com",
            tipoConta = TipoContaModel.CONTA_CORRENTE
        )
        var errors = this.validator.validate(dto)

        assertTrue(errors.isNotEmpty())

        dto = NovaChavePixDto(
            tipoChave = TipoChaveRequest.EMAIL,
            chave = "email@email.com",
            tipoConta = null
        )
        errors = this.validator.validate(dto)

        assertTrue(errors.isNotEmpty())
    }

    @Test
    internal fun `nao deve validar chave maior do que 77 caracteres`() {
        val dto = NovaChavePixDto(
            tipoChave = TipoChaveRequest.EMAIL,
            chave = "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres" +
                    "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres" +
                    "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres" +
                    "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres" +
                    "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres" +
                    "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres" +
                    "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres" +
                    "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres" +
                    "nao deve validar chave maior do que 77 caracteresnao deve validar chave maior do que 77 caracteres",

            tipoConta = TipoContaModel.CONTA_CORRENTE
        )

        val errors = this.validator.validate(dto)

        assertTrue(errors.isNotEmpty())
    }
}