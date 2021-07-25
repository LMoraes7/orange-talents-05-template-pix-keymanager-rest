package br.com.zup.edu.rest.controller.consultar.unica

import br.com.zup.edu.ConsultarChavePixResponseGrpc
import br.com.zup.edu.ConsultarChavePixServiceGrpc
import br.com.zup.edu.TipoChaveGrpc
import br.com.zup.edu.TipoContaGrpc
import br.com.zup.edu.grpc.factory.GrpcClientFactory
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
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultarChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: ConsultarChavePixServiceGrpc.ConsultarChavePixServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    internal fun `deve retornar 200 quando consultar for por clienteId e pixId`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        given(this.grpcClient.consultar(Mockito.any())).willReturn(
            this.criarResponseGrpcCompleta(
                clienteId = clienteId,
                pixId = pixId
            ))

        val requestHttp = HttpRequest.GET<Any>("/api/consultar/cliente/${clienteId}/pix/${pixId}")
        val responseHttp = this.httpClient.toBlocking().exchange(requestHttp, Any::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.OK, this.status)
            assertNotNull(this.body())
        }
    }

    @Test
    internal fun `deve retornar 200 quando consultar for por chave`() {
        val chave = "email@email.com"

        given(this.grpcClient.consultar(Mockito.any())).willReturn(
            this.criarResponseGrpcIncompleta())

        val requestHttp = HttpRequest.GET<Any>("/api/consultar//chave/${chave}")
        val responseHttp = this.httpClient.toBlocking().exchange(requestHttp, Any::class.java)

        with(responseHttp) {
            assertEquals(HttpStatus.OK, this.status)
            assertNotNull(this.body())
        }
    }

    private fun criarResponseGrpcCompleta(
        clienteId: String,
        pixId: String,
    ):
            ConsultarChavePixResponseGrpc =
        ConsultarChavePixResponseGrpc.newBuilder()
            .setPixId(pixId)
            .setClienteId(clienteId)
            .setTipoChave(TipoChaveGrpc.EMAIL.name)
            .setChave("email@email.com")
            .setTitular(
                ConsultarChavePixResponseGrpc.Titular.newBuilder()
                    .setNome("Diego Castro")
                    .setCpf("11111111111")
                    .build()
            )
            .setConta(
                ConsultarChavePixResponseGrpc.Conta.newBuilder()
                    .setInstituicao("Itaú Unibanco")
                    .setAgencia("0001")
                    .setNumero("0002")
                    .setTipoConta(TipoContaGrpc.CONTA_CORRENTE.name)
                    .build()
            )
            .setRegistradaEm(LocalDateTime.now().toString())
            .build()

    private fun criarResponseGrpcIncompleta():
            ConsultarChavePixResponseGrpc =
        ConsultarChavePixResponseGrpc.newBuilder()
            .setPixId("")
            .setClienteId("")
            .setTipoChave(TipoChaveGrpc.EMAIL.name)
            .setChave("email@email.com")
            .setTitular(
                ConsultarChavePixResponseGrpc.Titular.newBuilder()
                    .setNome("Diego Castro")
                    .setCpf("11111111111")
                    .build()
            )
            .setConta(
                ConsultarChavePixResponseGrpc.Conta.newBuilder()
                    .setInstituicao("Itaú Unibanco")
                    .setAgencia("0001")
                    .setNumero("0002")
                    .setTipoConta(TipoContaGrpc.CONTA_CORRENTE.name)
                    .build()
            )
            .setRegistradaEm(LocalDateTime.now().toString())
            .build()


    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    class Client3 {

        @Singleton
        fun consultarMock():
                ConsultarChavePixServiceGrpc.ConsultarChavePixServiceBlockingStub =
            Mockito.mock(ConsultarChavePixServiceGrpc.ConsultarChavePixServiceBlockingStub::class.java)

    }
}