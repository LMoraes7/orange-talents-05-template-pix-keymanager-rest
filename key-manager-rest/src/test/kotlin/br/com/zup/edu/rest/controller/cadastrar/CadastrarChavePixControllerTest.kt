package br.com.zup.edu.rest.controller.cadastrar

import br.com.zup.edu.CadastrarChavePixServiceGrpc
import br.com.zup.edu.NovaChavePixResponseGrpc
import br.com.zup.edu.grpc.factory.GrpcClientFactory
import br.com.zup.edu.rest.controller.cadastrar.request.NovaChavePixDto
import br.com.zup.edu.rest.dominio.enums.TipoChaveRequest
import br.com.zup.edu.rest.dominio.enums.TipoContaModel
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class CadastrarChavePixControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @field:Inject
    lateinit var grpcClient: CadastrarChavePixServiceGrpc.CadastrarChavePixServiceBlockingStub

    @Test
    internal fun `deve retonar 201 quando tudo der certo`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
        val bodyHttp = this.criarNovaChavePixDto(
            tipoChave = TipoChaveRequest.EMAIL,
            chave = "email@email.com",
            tipoConta = TipoContaModel.CONTA_CORRENTE
        )

        given(this.grpcClient.cadastrar(Mockito.any())).willReturn(this.criarGrpcResponse(clienteId, pixId))

        val requestHttp = HttpRequest.POST("/api/cadastrar/cliente/${clienteId}", bodyHttp)
        val responseHttp = this.httpClient.toBlocking().exchange(requestHttp, NovaChavePixDto::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.CREATED, this.status)
            assertTrue(this.headers.contains("Location"))
            assertTrue(this.header("Location")!!.contains(pixId))
        }
    }

    private fun criarGrpcResponse(clienteId: String, pixId: String) =
        NovaChavePixResponseGrpc.newBuilder()
            .setClienteId(clienteId)
            .setPixIdInterno(pixId)
            .build()

    private fun criarNovaChavePixDto(
        tipoChave: TipoChaveRequest?,
        chave: String?,
        tipoConta: TipoContaModel?,
    ) =
        NovaChavePixDto(
            tipoChave = tipoChave,
            chave = chave,
            tipoConta = tipoConta
        )

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    class Client1 {
        @Singleton
        fun cadastrarMock(): CadastrarChavePixServiceGrpc.CadastrarChavePixServiceBlockingStub =
            Mockito.mock(CadastrarChavePixServiceGrpc.CadastrarChavePixServiceBlockingStub::class.java)
    }
}