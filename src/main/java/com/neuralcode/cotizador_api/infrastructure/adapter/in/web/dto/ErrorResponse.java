package com.neuralcode.cotizador_api.infrastructure.adapter.in.web.dto;

/**
 * DTO sencillo para estandarizar el cuerpo de las respuestas de error HTTP.
 * <p>
 * Su finalidad es evitar que el cliente reciba trazas largas o estructuras internas de Spring/Jackson,
 * y en su lugar devolver un JSON pequeño y predecible, por ejemplo:
 * {@code {"message": "Campo name no puede ir vacío"}}.
 * <p>
 * Al ser un {@code record}, Java genera automáticamente el constructor y el getter del campo
 * {@code message}, por lo que es ideal cuando solo necesitamos transportar datos.
 *
 * @param message mensaje legible que describe el error que se enviará al cliente
 */
public record ErrorResponse(String message) {
}