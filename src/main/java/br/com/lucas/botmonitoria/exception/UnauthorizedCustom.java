package br.com.lucas.botmonitoria.exception;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnauthorizedCustom extends ExceptionCustom {
     
     /**
      * 
      */
     private static final long serialVersionUID = -8634604130660166682L;

     public UnauthorizedCustom(final String message) {
          super(UNAUTHORIZED, message);
     }     

}
