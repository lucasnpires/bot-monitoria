package br.com.lucas.botmonitoria.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


public class BadRequestCustom extends ExceptionCustom {

     private static final long serialVersionUID = 1L;
	
	public BadRequestCustom(final String message) {
		super(BAD_REQUEST, message);
	}
}
