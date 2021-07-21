package br.com.zup.edu.controller.consultar.todas

import br.com.zup.edu.ChavePixRemoverResponse
import br.com.zup.edu.ChavePixRemoverServiceGrpc
import br.com.zup.edu.ChavePixTodasConsultarResponse
import br.com.zup.edu.ChavePixTodasConsultarServiceGrpc
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
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultarTodasAsChavesPixControllerTest(
    private val grpcClient: ChavePixTodasConsultarServiceGrpc.ChavePixTodasConsultarServiceBlockingStub
) {

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    internal fun `deve remover chave pix`() {
        val responseGrpc = ChavePixTodasConsultarResponse.newBuilder()
            .build()

        BDDMockito.given(this.grpcClient.consultarTodas(Mockito.any())).willReturn(responseGrpc)

        val clienteId = UUID.randomUUID().toString()


        val request = HttpRequest.GET<Any>("/api/pix/consultar/todas/${clienteId}")
        val responseHttp = this.httpClient.toBlocking().exchange(request, Any::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.OK, this.status)
        }
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    class Client5 {
        @Singleton
        fun consultarTodasMock(): ChavePixTodasConsultarServiceGrpc.ChavePixTodasConsultarServiceBlockingStub? =
            Mockito.mock(ChavePixTodasConsultarServiceGrpc.ChavePixTodasConsultarServiceBlockingStub::class.java)
    }
}