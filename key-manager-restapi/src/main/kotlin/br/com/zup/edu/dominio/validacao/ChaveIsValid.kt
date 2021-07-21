package br.com.zup.edu.dominio.validacao

import br.com.zup.edu.controller.cadastrar.dto.request.ChavePixRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.reflect.KClass


@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ChaveIsValidValidator::class])
@Target(CLASS)
annotation class ChaveIsValid(
    val message: String = "Chave pix não é válida",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ChaveIsValidValidator : ConstraintValidator<ChaveIsValid, ChavePixRequest> {

    override fun isValid(
        value: ChavePixRequest,
        annotationMetadata: AnnotationValue<ChaveIsValid>,
        context: ConstraintValidatorContext,
    ): Boolean {

        if(value.tipoChave == null)
            return false

        return value.tipoChave.valida(value.chave)
    }
}