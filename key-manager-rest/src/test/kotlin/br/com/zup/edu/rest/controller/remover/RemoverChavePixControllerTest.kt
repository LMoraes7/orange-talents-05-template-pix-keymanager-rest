package br.com.zup.edu.rest.controller.remover

import br.com.zup.edu.RemoverChavePixRequestGrpc
import br.com.zup.edu.RemoverChavePixResponseGrpc
import br.com.zup.edu.RemoverChavePixServiceGrpc
import br.com.zup.edu.grpc.factory.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RemoverChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: RemoverChavePixServiceGrpc.RemoverChavePixServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    internal fun `deve retornar 204 quando tudo der certo`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        given(this.grpcClient.remover(Mockito.any())).willReturn(this.criarResponseGrpc(true))

        val requestHttp = HttpRequest.DELETE<Any>("/api/remover/cliente/${clienteId}/pix/${pixId}")
        val responseHttp = this.httpClient.toBlocking().exchange(requestHttp, Any::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.NO_CONTENT, this.status)
            assertNull(this.body())
        }

    }

    private fun criarResponseGrpc(removida: Boolean) =
        RemoverChavePixResponseGrpc.newBuilder()
            .setRemovida(removida)
            .build()

    private fun criarRequestGrpc(clienteId: String, pixId: String) =
        RemoverChavePixRequestGrpc.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .build()

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    class Client2 {
        @Singleton
        fun removerMock():
                RemoverChavePixServiceGrpc.RemoverChavePixServiceBlockingStub =
            Mockito.mock(RemoverChavePixServiceGrpc.RemoverChavePixServiceBlockingStub::class.java)

    }
}