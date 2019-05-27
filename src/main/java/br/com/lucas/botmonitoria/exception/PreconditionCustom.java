package br.com.lucas.botmonitoria.exception;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

public class PreconditionCustom extends ExceptionCustom {
     
     private static final long serialVersionUID = -7496301131580599320L;

     public PreconditionCustom(final String message) {
          super(PRECONDITION_FAILED, message);
     }

}
