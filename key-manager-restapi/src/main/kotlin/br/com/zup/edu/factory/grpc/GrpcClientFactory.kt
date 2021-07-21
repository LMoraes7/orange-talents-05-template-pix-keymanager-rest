package br.com.zup.edu.factory.grpc

import br.com.zup.edu.ChavePixCadastrarServiceGrpc
import br.com.zup.edu.ChavePixRemoverServiceGrpc
import br.com.zup.edu.ChavePixTodasConsultarServiceGrpc
import br.com.zup.edu.ChavePixUnicaConsultarServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory() {

    @Singleton
    fun cadastrar(@GrpcChannel("pixManager") channel: ManagedChannel):
            ChavePixCadastrarServiceGrpc.ChavePixCadastrarServiceBlockingStub =
        ChavePixCadastrarServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun remover(@GrpcChannel("pixManager") channel: ManagedChannel):
            ChavePixRemoverServiceGrpc.ChavePixRemoverServiceBlockingStub =
        ChavePixRemoverServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listarChaves(@GrpcChannel("pixManager") channel: ManagedChannel):
            ChavePixTodasConsultarServiceGrpc.ChavePixTodasConsultarServiceBlockingStub =
        ChavePixTodasConsultarServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultarChave(@GrpcChannel("pixManager") channel: ManagedChannel):
            ChavePixUnicaConsultarServiceGrpc.ChavePixUnicaConsultarServiceBlockingStub =
        ChavePixUnicaConsultarServiceGrpc.newBlockingStub(channel)
}