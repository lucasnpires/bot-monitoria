
package br.com.lucas.botmonitoria.exception;

import static org.springframework.http.HttpStatus.NO_CONTENT;

public class NoContentCustom extends ExceptionCustom {

     private static final long serialVersionUID = 1831708858153153786L;

     public NoContentCustom(final String message) {

          super(NO_CONTENT, message);
     }

}
