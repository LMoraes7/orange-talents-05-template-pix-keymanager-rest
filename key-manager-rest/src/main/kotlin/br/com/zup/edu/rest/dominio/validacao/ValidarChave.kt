package br.com.zup.edu.rest.dominio.validacao

import br.com.zup.edu.rest.controller.cadastrar.request.NovaChavePixDto
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.validation.Constraint
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Retention(RUNTIME)
@Target(CLASS)
@Constraint(validatedBy = [ValidarChaveValidator::class])
annotation class ValidarChave

class ValidarChaveValidator : ConstraintValidator<ValidarChave, NovaChavePixDto> {

    override fun isValid(
        value: NovaChavePixDto,
        annotationMetadata: AnnotationValue<ValidarChave>,
        context: ConstraintValidatorContext,
    ): Boolean {
        if(value.tipoChave == null)
            return false

        return value.tipoChave.valida(value.chave)
    }
}
