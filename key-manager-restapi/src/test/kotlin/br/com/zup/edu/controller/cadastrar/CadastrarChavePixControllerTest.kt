package br.com.zup.edu.controller.cadastrar

import br.com.zup.edu.ChavePixCadastrarResponse
import br.com.zup.edu.ChavePixCadastrarServiceGrpc
import br.com.zup.edu.controller.cadastrar.dto.request.ChavePixRequest
import br.com.zup.edu.controller.cadastrar.dto.request.TipoChaveRequest
import br.com.zup.edu.controller.cadastrar.dto.request.TipoContaRequest
import br.com.zup.edu.factory.grpc.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class CadastrarChavePixControllerTest(
    private val grpcClient: ChavePixCadastrarServiceGrpc.ChavePixCadastrarServiceBlockingStub
) {

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    internal fun `deve cadastrar chave pix`() {
        val pixId = UUID.randomUUID().toString()
        val responseGrpc = ChavePixCadastrarResponse.newBuilder()
            .setPixId(pixId)
            .build()

        given(this.grpcClient.cadastrar(Mockito.any())).willReturn(responseGrpc)

        val dto =
            ChavePixRequest(
                clienteId = "5260263c-a3c1-4727-ae32-3bdb2538841b",
                tipoChave = TipoChaveRequest.EMAIL,
                chave = "yurimatheus@email.com",
                tipoConta = TipoContaRequest.CONTA_CORRENTE
            )

        val request = HttpRequest.POST("/api/pix/cadastrar", dto)
        val responseHttp = this.httpClient.toBlocking().exchange(request, ChavePixRequest::class.java)

        assertEquals(responseHttp.status, HttpStatus.CREATED)
        assertTrue(responseHttp.headers.contains("Location"))
        assertTrue(responseHttp.header("Location")!!.contains(pixId))
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    class Client1 {

        @Singleton
        fun cadastrarMock(): ChavePixCadastrarServiceGrpc.ChavePixCadastrarServiceBlockingStub =
            Mockito.mock(ChavePixCadastrarServiceGrpc.ChavePixCadastrarServiceBlockingStub::class.java)
    }
}