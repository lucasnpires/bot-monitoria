
package br.com.lucas.botmonitoria.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ForbiddenRequestCustom extends ExceptionCustom {

     private static final long serialVersionUID = 1L;

     public ForbiddenRequestCustom(final String message) {

          super(FORBIDDEN, message);
     }
}
