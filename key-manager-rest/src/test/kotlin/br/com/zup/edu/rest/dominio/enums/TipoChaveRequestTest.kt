package br.com.zup.edu.rest.dominio.enums

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoChaveRequestTest {

    @Nested
    inner class ChaveAleatoriaTest {
        @Test
        internal fun `deve ser valido quando chave aleatoria for nula ou vazia`() {
            val tipoChave = TipoChaveRequest.ALEATORIA

            assertTrue(tipoChave.valida(null))
            assertTrue(tipoChave.valida(""))
        }

        @Test
        internal fun `nao deve ser valido quando chave aleatoria nao for vazia`() {
            val tipoChave = TipoChaveRequest.ALEATORIA

            assertFalse(tipoChave.valida("yutgjhg"))
        }
    }

    @Nested
    inner class ChaveCpfTeste {
        @Test
        internal fun `deve ser valido quando for um CPF valido`() {
            val tipoChave = TipoChaveRequest.CPF

            assertTrue(tipoChave.valida("13049364300"))
        }

        @Test
        internal fun `nao deve ser valido quando CPF for invalido, nulo ou vazio`() {
            val tipoChave = TipoChaveRequest.CPF

            assertFalse(tipoChave.valida("12345678900"))
            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida(""))
        }
    }

    @Nested
    inner class ChaveCnpjTeste {
        @Test
        internal fun `deve ser valido quando for um CNPJ valido`() {
            val tipoChave = TipoChaveRequest.CNPJ

            assertTrue(tipoChave.valida("52.550.571/0001-01"))
        }

        @Test
        internal fun `nao deve ser valido quando CNPJ for invalido, nulo ou vazio`() {
            val tipoChave = TipoChaveRequest.CNPJ

            assertFalse(tipoChave.valida("00.000.000/0100-00"))
            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida(""))
        }
    }

    @Nested
    inner class ChaveCelularTeste {
        @Test
        internal fun `deve ser valido quando for um celular valido`() {
            val tipoChave = TipoChaveRequest.CELULAR

            assertTrue(tipoChave.valida("+5511987654321"))
        }

        @Test
        internal fun `nao deve ser valido quando um celular for invalido, nulo ou vazio`() {
            val tipoChave = TipoChaveRequest.CELULAR

            assertFalse(tipoChave.valida("11987654321"))
            assertFalse(tipoChave.valida("+55a11987654321"))
            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida(""))
        }
    }

    @Nested
    inner class ChaveEmailTeste {
        @Test
        internal fun `deve ser valido quando for um email valido`() {
            val tipoChave = TipoChaveRequest.EMAIL

            assertTrue(tipoChave.valida("yuri@email.com"))
        }

        @Test
        internal fun `nao deve ser valido quando o email nao for valido, nulo ou vazio`() {
            val tipoChave = TipoChaveRequest.EMAIL

            assertFalse(tipoChave.valida("yuri.com"))
            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida(""))
        }
    }
}