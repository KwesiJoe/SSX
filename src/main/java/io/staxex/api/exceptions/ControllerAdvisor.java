package io.staxex.api.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class ControllerAdvisor {

    Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    Map<Integer, String> messages = new HashMap<Integer, String>(){{
        put(401, "Unauthorised to perform this operation");
        put(500, "something went wrong");
        put(404, "Not found");
        // include more custom images
    }};

    @ExceptionHandler(value = {WebClientResponseException.class})
    public ResponseEntity<ErrorMessage> responseExceptionHandler(WebClientResponseException e, WebRequest wr){
        logger.error(e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorMessage(messages.getOrDefault(e.getStatusCode().value(),e.getMessage())));
    }

    @ExceptionHandler(value = {WebClientRequestException.class})
    public ResponseEntity<ErrorMessage> requestExceptionHandler(WebClientRequestException e, WebRequest wr){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<ErrorMessage> requestExceptionHandler(NoHandlerFoundException e, WebRequest wr){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> invalidRequestBodyExceptionHandler(MethodArgumentNotValidException e, WebRequest wr){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ErrorMessage("invalid request body, " + e.getBindingResult().getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(",\n"))));
    }

}
