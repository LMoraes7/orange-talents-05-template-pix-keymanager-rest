package br.com.zup.edu.controller.cadastrar

import br.com.zup.edu.ChavePixCadastrarServiceGrpc
import br.com.zup.edu.controller.cadastrar.dto.request.ChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import java.net.URI
import javax.validation.Valid

@Validated
@Controller("/api/pix/cadastrar")
class CadastrarChavePixController(
    private val grpcClient: ChavePixCadastrarServiceGrpc.ChavePixCadastrarServiceBlockingStub,
) {

    @Post
    fun cadastrar(@Valid @Body request: ChavePixRequest): HttpResponse<Any> {
        val response = this.grpcClient.cadastrar(request.paraGrpcRequest())
        //O path que está sendo retornado não representa a URI do recurso,
        //  retornei a URI para o cliente apenas para fins didáticos
        //Por enquanto, ignorar a URI retornada
        return HttpResponse.created<Any>(
            UriBuilder.of("/api/pix/consultar/{pixId}")
                .expand(mutableMapOf(Pair("pixId", response.pixId)))
        ).body(object {
            val pixId = response.pixId
        })
    }
}