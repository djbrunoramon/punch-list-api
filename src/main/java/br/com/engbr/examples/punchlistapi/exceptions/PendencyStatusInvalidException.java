package br.com.engbr.examples.punchlistapi.exceptions;

public class PendencyStatusInvalidException extends Exception{
    private static final String MESSAGE = "Pendency status is invalid. Please check the fields.";

    public PendencyStatusInvalidException() {
        super(MESSAGE);
    }
}
