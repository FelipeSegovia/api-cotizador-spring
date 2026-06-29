package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.handler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.neuralcode.cotizador_api.domain.exceptions.UserDomainException;
import com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la capa web.
 * <p>
 * La anotación {@link RestControllerAdvice} le indica a Spring que esta clase debe escuchar las
 * excepciones lanzadas por los controladores REST y transformarlas en respuestas HTTP controladas.
 * <p>
 * En este proyecto su objetivo principal es:
 * <ul>
 *     <li>Evitar respuestas {@code 500} con mensajes técnicos muy largos.</li>
 *     <li>Convertir errores comunes de validación o deserialización en respuestas {@code 400}.</li>
 *     <li>Devolver un formato uniforme usando {@link ErrorResponse}.</li>
 * </ul>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de Bean Validation, por ejemplo cuando un campo anotado con
     * {@code @NotBlank} o {@code @Email} no cumple las reglas definidas en el request.
     * <p>
     * Spring lanza {@link MethodArgumentNotValidException} después de intentar construir el objeto
     * del {@code @RequestBody} y ejecutar las validaciones activadas con {@code @Valid}.
     * <p>
     * Aquí primero se busca el error específico del campo {@code name} para priorizar el mensaje que
     * queríamos mostrar en ese caso. Si no existe, se toma el primer error disponible y se envía su
     * mensaje al cliente. Si por alguna razón no hay errores detallados, se usa el mensaje genérico
     * {@code "Solicitud invalida"}.
     *
     * @param exception excepción generada por Spring cuando falla la validación del request
     * @return respuesta HTTP 400 con un mensaje simple dentro de {@link ErrorResponse}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError("name");

        if (fieldError != null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(fieldError.getDefaultMessage()));
        }

        FieldError firstError = exception.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
        String message = firstError != null ? firstError.getDefaultMessage() : "Solicitud invalida";
        return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

    /**
     * Maneja errores de lectura del JSON antes de que Bean Validation pueda ejecutarse.
     * <p>
     * Esto ocurre, por ejemplo, cuando Jackson intenta convertir un valor JSON a un tipo Java y no
     * puede hacerlo. En este caso nos interesa detectar específicamente cuando {@code name} no llega
     * como texto, por ejemplo si llega como objeto o número.
     * <p>
     * Flujo resumido:
     * <ol>
     *     <li>Spring intenta deserializar el JSON del request.</li>
     *     <li>Jackson detecta un tipo incompatible y lanza una excepción interna.</li>
     *     <li>Spring la envuelve en {@link HttpMessageNotReadableException}.</li>
     *     <li>Este método revisa la causa real y decide qué mensaje devolver.</li>
     * </ol>
     * Si el problema corresponde al campo {@code name}, se devuelve un mensaje específico. Para los
     * demás casos se responde con un mensaje genérico de solicitud inválida.
     *
     * @param exception excepción lanzada cuando el body JSON no puede convertirse correctamente
     * @return respuesta HTTP 400 con un mensaje controlado dentro de {@link ErrorResponse}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableMessage(HttpMessageNotReadableException exception) {
        Throwable cause = exception.getCause();

        if (cause instanceof MismatchedInputException mismatchedInputException &&
                mismatchedInputException.getPath().stream().anyMatch(reference -> "name".equals(reference.getFieldName()))) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Campo name debe ser una cadena de texto"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Solicitud invalida"));
    }

    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<ErrorResponse> handleUserDomainException(UserDomainException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage()));
    }
}