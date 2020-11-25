/*
 * Software is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.
 *
 * The Initial Developer of the Original Code is Paweł Kamiński.
 * All Rights Reserved.
 */
package com.ffb.tradevalidator.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingController extends ResponseEntityExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(ErrorHandlingController.class);
    private static final String IAE_DESCRIPTION = "There were validation error.";

    @ExceptionHandler
    public ResponseEntity<Object> handleInputValidationException(IllegalArgumentException ex, WebRequest request) {
        return handleValidationException(ex, request, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        String message = ex.getBindingResult()
                .getAllErrors().stream()
                .filter(oe -> oe instanceof FieldError)
                .map(oe -> (FieldError) oe)
                .map(oe -> oe.getObjectName() + "." + oe.getField() + " " + oe.getDefaultMessage())
                .collect(Collectors.joining("\n"));
        return handleValidationException(ex, request, message);
    }

    private ResponseEntity<Object> handleValidationException(
            Exception ex,
            WebRequest request,
            String message)
    {
        log.error(IAE_DESCRIPTION + ": " + message);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return this.handleExceptionInternal(
                ex,
                new ErrorResponse(status, IAE_DESCRIPTION, message),
                new HttpHeaders(),
                status, request);
    }

    public static class ErrorResponse {

        private final HttpStatus status;
        private final String description;
        private final String message;

        private ErrorResponse(HttpStatus status, String description, String message) {

            this.status = status;
            this.description = description;
            this.message = message;
        }

        public HttpStatus getStatus()
        {
            return status;
        }

        public String getDescription()
        {
            return description;
        }

        public String getMessage()
        {
            return message;
        }
    }
}
    
