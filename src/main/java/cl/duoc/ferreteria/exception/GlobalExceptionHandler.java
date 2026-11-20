package cl.duoc.ferreteria.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import main.java.cl.duoc.ferreteria.exception.StockInsuficienteException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(RecursoNoEncontradoException.class)
        public ResponseEntity<Map<String, String>> noEncontrado(
                        RecursoNoEncontradoException e) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Map.of("error", e.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> datosInvalidos(
                        MethodArgumentNotValidException e) {

                String mensaje = e.getBindingResult().getFieldErrors().stream()
                                .findFirst()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .orElse("Datos inválidos");

                return ResponseEntity.badRequest()
                                .body(Map.of("error", mensaje));
        }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity> stockInsuficiente(StockInsuficienteException e) {
        return ResponseEntity.badRequest()
                .body(Map.of("mensaje", e.getMessage(), "error", e.getMessage()));
    }
}