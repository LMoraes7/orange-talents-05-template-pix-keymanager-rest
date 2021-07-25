package br.com.zup.edu.grpc.factory

import br.com.zup.edu.CadastrarChavePixServiceGrpc
import br.com.zup.edu.ConsultarChavePixServiceGrpc
import br.com.zup.edu.ConsultarTodasChavePixServiceGrpc
import br.com.zup.edu.RemoverChavePixServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory() {

    @Singleton
    fun cadastrar(@GrpcChannel("keyManager") channel: ManagedChannel):
            CadastrarChavePixServiceGrpc.CadastrarChavePixServiceBlockingStub =
        CadastrarChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun remover(@GrpcChannel("keyManager") channel: ManagedChannel):
            RemoverChavePixServiceGrpc.RemoverChavePixServiceBlockingStub =
        RemoverChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultarChavePix(@GrpcChannel("keyManager") channel: ManagedChannel):
            ConsultarChavePixServiceGrpc.ConsultarChavePixServiceBlockingStub =
        ConsultarChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultarTodasChavesPix(@GrpcChannel("keyManager") channel: ManagedChannel):
            ConsultarTodasChavePixServiceGrpc.ConsultarTodasChavePixServiceBlockingStub =
        ConsultarTodasChavePixServiceGrpc.newBlockingStub(channel)
}