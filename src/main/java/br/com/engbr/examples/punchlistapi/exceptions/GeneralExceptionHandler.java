package br.com.engbr.examples.punchlistapi.exceptions;

import br.com.engbr.examples.punchlistapi.utils.ResponseError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError methodArgumentNotValidException(Exception e) {
        return new ResponseError(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError dataIntegrityViolationException(Exception e) {
        return new ResponseError(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError httpMessageNotReadableException(Exception e) {
        return new ResponseError(e.getMessage());
    }

    @ExceptionHandler(PendencyStatusInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError pendencyFieldsInvalidException(Exception e) {
        return new ResponseError(e.getMessage());
    }
}
