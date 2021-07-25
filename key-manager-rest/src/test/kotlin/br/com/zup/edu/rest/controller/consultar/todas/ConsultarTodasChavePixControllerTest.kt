package br.com.zup.edu.rest.controller.consultar.todas

import br.com.zup.edu.ConsultarTodasChavePixResponseGrpc
import br.com.zup.edu.ConsultarTodasChavePixServiceGrpc
import br.com.zup.edu.grpc.factory.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultarTodasChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: ConsultarTodasChavePixServiceGrpc.ConsultarTodasChavePixServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    internal fun `deve retornar 200`() {
        val clienteId = UUID.randomUUID().toString()

        given(this.grpcClient.consultar(Mockito.any())).willReturn(this.criarResponseGrpc())

        val requestHttp = HttpRequest.GET<Any>("/api/consultar/cliente/${clienteId}")
        val responseHttp = this.httpClient.toBlocking().exchange(requestHttp, Any::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.OK, this.status)
        }
    }

    private fun criarResponseGrpc():
            ConsultarTodasChavePixResponseGrpc =
        ConsultarTodasChavePixResponseGrpc.newBuilder()
            .build()

        @Factory
        @Replaces(factory = GrpcClientFactory::class)
        class Client4 {

            @Singleton
            fun consultarTodas():
                    ConsultarTodasChavePixServiceGrpc.ConsultarTodasChavePixServiceBlockingStub =
                Mockito.mock(ConsultarTodasChavePixServiceGrpc.ConsultarTodasChavePixServiceBlockingStub::class.java)
        }

}