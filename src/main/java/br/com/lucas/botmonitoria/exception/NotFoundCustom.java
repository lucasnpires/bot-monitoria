package br.com.lucas.botmonitoria.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundCustom extends ExceptionCustom {
     
     private static final long serialVersionUID = 6668700084083250933L;

     public NotFoundCustom(final String message) {
          super(NOT_FOUND, message);
     }
}
