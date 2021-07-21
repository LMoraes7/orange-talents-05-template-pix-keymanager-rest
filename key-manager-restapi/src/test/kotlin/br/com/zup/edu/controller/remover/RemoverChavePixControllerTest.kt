package br.com.zup.edu.controller.remover

import br.com.zup.edu.ChavePixRemoverRequest
import br.com.zup.edu.ChavePixRemoverResponse
import br.com.zup.edu.ChavePixRemoverServiceGrpc
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

@MicronautTest
internal class RemoverChavePixControllerTest(
    private val grpcClient: ChavePixRemoverServiceGrpc.ChavePixRemoverServiceBlockingStub,
) {

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    internal fun `deve remover chave pix`() {
        val responseGrpc = ChavePixRemoverResponse.newBuilder()
            .setRemovida(true)
            .build()

        given(this.grpcClient.remover(Mockito.any())).willReturn(responseGrpc)

        val clienteId = UUID.randomUUID().toString()
        val pixIdInterno = UUID.randomUUID().toString()


        val request = HttpRequest.DELETE<Any>("/api/pix/remover/cliente/${clienteId}/pix/${pixIdInterno}")
        val responseHttp = this.httpClient.toBlocking().exchange(request, Any::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.NO_CONTENT, this.status)
            assertNull(this.body())
        }
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    class Client2 {
        @Singleton
        fun removerMock(): ChavePixRemoverServiceGrpc.ChavePixRemoverServiceBlockingStub =
            Mockito.mock(ChavePixRemoverServiceGrpc.ChavePixRemoverServiceBlockingStub::class.java)
    }
}