
package br.com.lucas.botmonitoria.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import br.com.lucas.botmonitoria.domain.exception.ExceptionsMessagesEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionCustom extends RuntimeException implements Serializable {

     /**
      * 
      */
     private static final long serialVersionUID = 1L;

     private String message;

     private Throwable throwable;

     private HttpStatus status;

     public ExceptionCustom(HttpStatus status, Throwable cause) {

          super(cause);
          this.status = status;
     }

     public ExceptionCustom(HttpStatus status, String message) {

          super(message);
          this.status = status;
          this.message = message;
     }

     public static void checkThrow(final boolean expression, final ExceptionsMessagesEnum exceptionsMessagesCdtEnum) {

          if (expression) {
               exceptionsMessagesCdtEnum.raise();
          }
     }

     public static void checkThrow(final boolean expression, final ExceptionsMessagesEnum exceptionsMessagesCdtEnum, final String message) {

          if (expression) {
               exceptionsMessagesCdtEnum.raise(message);
          }
     }

     public static void checkThrowLogError(Boolean expression, final ExceptionsMessagesEnum exceptionsMessagesCdtEnum, String message) {

          if (expression) {
               exceptionsMessagesCdtEnum.raise(message);
          }

     }

}
