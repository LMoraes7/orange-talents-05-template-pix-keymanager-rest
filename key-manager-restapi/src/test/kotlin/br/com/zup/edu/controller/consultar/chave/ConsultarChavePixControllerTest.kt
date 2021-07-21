package br.com.zup.edu.controller.consultar.chave

import br.com.zup.edu.ChavePixRemoverResponse
import br.com.zup.edu.ChavePixRemoverServiceGrpc
import br.com.zup.edu.ChavePixUnicaConsultarResponse
import br.com.zup.edu.ChavePixUnicaConsultarServiceGrpc
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
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultarChavePixUnicaControllerTest(
    private val grpcClient: ChavePixUnicaConsultarServiceGrpc.ChavePixUnicaConsultarServiceBlockingStub
) {

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    internal fun `deve consultar chave pix pelo cliente ID e pelo pix ID`() {
        val responseGrpc = ChavePixUnicaConsultarResponse.newBuilder()
            .build()

        given(this.grpcClient.consultarUnica(Mockito.any())).willReturn(responseGrpc)

        val clienteId = UUID.randomUUID().toString()
        val pixIdInterno = UUID.randomUUID().toString()


        val request = HttpRequest.GET<Any>("/api/pix/consultar/cliente/${clienteId}/pix/${pixIdInterno}")
        val responseHttp = this.httpClient.toBlocking().exchange(request, Any::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.OK, this.status)
        }
    }

    @Test
    internal fun `deve consultar chave pix por chave`() {
        val responseGrpc = ChavePixUnicaConsultarResponse.newBuilder()
            .build()

        given(this.grpcClient.consultarUnica(Mockito.any())).willReturn(responseGrpc)

        val chave = "yuri@email.com"
        val request = HttpRequest.GET<Any>("/api/pix/consultar/chave/${chave}")
        val responseHttp = this.httpClient.toBlocking().exchange(request, Any::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.OK, this.status)
        }
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    class Client3 {
        @Singleton
        fun consultarUnicaMock(): ChavePixUnicaConsultarServiceGrpc.ChavePixUnicaConsultarServiceBlockingStub? =
            Mockito.mock(ChavePixUnicaConsultarServiceGrpc.ChavePixUnicaConsultarServiceBlockingStub::class.java)
    }
}