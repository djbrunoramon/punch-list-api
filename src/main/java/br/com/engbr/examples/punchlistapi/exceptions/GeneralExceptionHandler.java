package br.com.engbr.examples.punchlistapi.exceptions;

import br.com.engbr.examples.punchlistapi.utils.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {

    private static final String ID_NOT_FOUND = "ID not found";

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError idNotFoundException(Exception e) {
        return new ResponseError(ID_NOT_FOUND);
    }
}
