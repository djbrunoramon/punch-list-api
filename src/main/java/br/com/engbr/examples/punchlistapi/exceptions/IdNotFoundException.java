package br.com.engbr.examples.punchlistapi.exceptions;

public class IdNotFoundException extends Exception{
    private static final String MESSAGE = "Id not found";

    public IdNotFoundException() {
        super(MESSAGE);
    }
}
