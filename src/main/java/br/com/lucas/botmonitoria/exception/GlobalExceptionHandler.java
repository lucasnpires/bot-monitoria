
package br.com.lucas.botmonitoria.exception;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.lucas.botmonitoria.domain.exception.ErroInfo;

@ControllerAdvice
public class GlobalExceptionHandler {

     public static final String MENSAGEM_GLOBAL_400 = "Requisição inválida.";

     public static final String MENSAGEM_GLOBAL_401 = "Não autorizado.";

     public static final String MENSAGEM_GLOBAL_403 = "Não permitido.";

     public static final String MENSAGEM_GLOBAL_404 = "Recurso não encontrado.";

     public static final String MENSAGEM_GLOBAL_409 = "Objeto já existente.";

     public static final String MENSAGEM_GLOBAL_412 = "Pré condições não atendidas.";

     public static final String MENSAGEM_GLOBAL_500 = "Erro interno do sistema.";

     public static final String MENSAGEM_TIMEOUT = "Problema de conexao";

     private static final String TIMED_OUT = "timed out";

     public static final String PARAMETRO_INVALIDO = "Parâmetro inválido";

     public static final String MENSAGEM_GLOBAL_204 = "Nenhum conteúdo.";

     public static final String MENSAGEM_GLOBAL_201 = "Objeto criado.";
     
     public static final String MENSAGEM_GLOBAL_200 = "Sucesso na requisição.";

     private static final String FALHA_NO_REQUEST_MSG_PATTERN = "Falha no request: Objeto[%s] Campo[%s] Valor[%s]";

     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     @ExceptionHandler(Exception.class)
     public @ResponseBody ResponseEntity<ErroInfo> handleExceptionServerError(HttpServletRequest request, Exception ex) {

          if (ex instanceof HttpRequestMethodNotSupportedException) {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }

          String message = MENSAGEM_GLOBAL_500;
          HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

          if (StringUtils.contains(ex.getMessage(), TIMED_OUT)) {
               httpStatus = HttpStatus.GATEWAY_TIMEOUT;
               message = MENSAGEM_TIMEOUT;
          } else if (StringUtils.contains(ex.getMessage(), "Type definition error")) {

               String[] valor = ex.getMessage().split("problem: ");
               String[] variavel = ex.getMessage().split("Cannot construct instance of ");
               if (valor.length > 1 && variavel.length > 1) {
                    try {
                         String valorStr = valor[1].split("\n at ")[0];
                         int iniCampo = variavel[1].split(",")[2].lastIndexOf("[\"")+2;
                         int endCampo = variavel[1].split(",")[2].lastIndexOf("\"])");
                         String campo = variavel[1].split(",")[2].substring(iniCampo,endCampo);
                         String variavelStr = variavel[1].split(",")[0];
                         message = format(FALHA_NO_REQUEST_MSG_PATTERN, variavelStr, campo, valorStr);
                    } catch (Exception e) {
                         message = ex.getMessage();
                    }
               } else {
                    message = ex.getMessage();
               }
               httpStatus = HttpStatus.BAD_REQUEST;
          } else if (StringUtils.contains(ex.getMessage(), "Failed to convert value of type")) {
               String[] str = ex.getMessage().split("\\.");
               String variavel = "";
               String valor = "";
               if (str.length > 2) {
                    variavel = str[str.length - 2];
                    valor = str[str.length - 1];
                    if(valor.contains("NumberFormatException")) {
                         valor = valor.split("\"")[1];
                    }
               }
               if (!"".equals(str) && !"".equals(variavel)) {
                    try {
                         message = "Valor inválido: " + valor + " do tipo: " + variavel;
                    } catch (Exception e) {
                         message = ex.getMessage();
                    }
               } else {
                    message = ex.getMessage();
               }
               httpStatus = HttpStatus.BAD_REQUEST;
          } else if(StringUtils.contains(ex.getMessage(), "Unrecognized token")) {
               httpStatus = HttpStatus.BAD_REQUEST;
               String msgErro = ex.getCause().getMessage();

               String valor = msgErro.split("Unrecognized token")[1].split(":")[0];
               message = "Valor inválido: "+ valor;
          } else if(StringUtils.contains(ex.getMessage(), "JSON parse error")) {
               httpStatus = HttpStatus.BAD_REQUEST;
               String msgErro = ex.getCause().getMessage();
               String campo = StringUtils.reverse(msgErro).split("\\[")[0];
               campo = StringUtils.reverse(campo).replace("\"","").replace("])","");
               String valor = msgErro.split(" from String ")[1].split(":")[0];
               message = "Valor inválido: "+ valor + " para o campo: " + campo;
          }

          return new ResponseEntity<>(buildErrorInfo(request, ex, singletonList(message)), httpStatus);
     }

     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler(MethodArgumentNotValidException.class)
     public @ResponseBody ErroInfo handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {

          final List<String> messages = new ArrayList<>();
          ofNullable(ex.getBindingResult().getAllErrors()).orElse(emptyList()).forEach(objectError -> {

               final FieldError field = (FieldError) objectError;
               messages.add(format(FALHA_NO_REQUEST_MSG_PATTERN, field.getObjectName(), field.getField(), field.getRejectedValue()));
          });

          return buildErrorInfo(request, ex, messages);
     }

     @ResponseStatus(HttpStatus.NOT_FOUND)
     @ExceptionHandler({ NotFoundCustom.class })
     public @ResponseBody ErroInfo handleNotFoundCustom(HttpServletRequest request, ExceptionCustom ex) {

          return buildErrorInfo(request, ex, singletonList(ex.getMessage()));
     }

     @ResponseStatus(HttpStatus.NO_CONTENT)
     @ExceptionHandler({ NoContentCustom.class })
     public @ResponseBody ErroInfo handleNoContentCustom(HttpServletRequest request, ExceptionCustom ex) {

          return buildErrorInfo(request, ex, singletonList(ex.getMessage()));
     }

     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler({ BadRequestCustom.class })
     public @ResponseBody ErroInfo handleExceptionCdt(HttpServletRequest request, ExceptionCustom ex) {

          return buildErrorInfo(request, ex, singletonList(ex.getMessage()));
     }
     
     @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
     @ExceptionHandler({ PreconditionCustom.class })
     public @ResponseBody ErroInfo handlePreconditionCustom(HttpServletRequest request, ExceptionCustom ex) {

          return buildErrorInfo(request, ex, singletonList(ex.getMessage()));
     }

     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler(BindException.class)
     public @ResponseBody ErroInfo handleBindException(HttpServletRequest request, BindException ex) {
    	 
         final List<String> messages = new ArrayList<>();
         ofNullable(ex.getBindingResult().getAllErrors()).orElse(emptyList()).forEach(objectError -> {

              final FieldError field = (FieldError) objectError;
              messages.add(format(FALHA_NO_REQUEST_MSG_PATTERN, field.getObjectName(), field.getField(), field.getRejectedValue()));
         });

         return buildErrorInfo(request, ex, messages);
     }

     @ExceptionHandler(HttpClientErrorException.class)
     public ResponseEntity<ErroInfo> handleHttpClientErrorException(HttpServletRequest request, HttpClientErrorException ex) {

          String message = null;
          HttpStatus httpStatus = ex.getStatusCode();

          switch (ex.getStatusCode()) {
               case BAD_REQUEST:
                    message = MENSAGEM_GLOBAL_400;
                    break;
               case NOT_FOUND:
                    message = MENSAGEM_GLOBAL_404;
                    break;
               case UNAUTHORIZED:
                    message = MENSAGEM_GLOBAL_401;
                    break;
               case FORBIDDEN:
                    message = MENSAGEM_GLOBAL_403;
                    break;
               case CONFLICT:
                    message = MENSAGEM_GLOBAL_409;
                    break;
               case UNPROCESSABLE_ENTITY:
                    message = ex.getMessage();
                    break;
               default:
                    httpStatus = HttpStatus.PRECONDITION_FAILED;
                    message = ex.getMessage();
                    break;
          }
          return new ResponseEntity<>(buildErrorInfo(request, ex, singletonList(message)), httpStatus);
     }

     @ExceptionHandler(ExceptionCustom.class)
     public ResponseEntity<ErroInfo> handleCustomException(HttpServletRequest request, ExceptionCustom ex) {

          String message = ex.getMessage();
          HttpStatus httpStatus = ex.getStatus();
          return new ResponseEntity<>(buildErrorInfo(request, ex, singletonList(message)), httpStatus);
     }

     private ErroInfo buildErrorInfo(HttpServletRequest request, Exception exceptionCdt, List<String> messages) {
          return ErroInfo.builder().timestamp(LocalDateTime.now()).messages(messages).exception(exceptionCdt.getClass().getSimpleName()).path(request.getRequestURI()).build();
     }

     @ResponseStatus(HttpStatus.UNAUTHORIZED)
     @ExceptionHandler({ UnauthorizedCustom.class })
     public @ResponseBody ErroInfo handleUnauthorizedCustom(HttpServletRequest request, ExceptionCustom ex) {

          return buildErrorInfo(request, ex, singletonList(ex.getMessage()));
     }
     
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
     public @ResponseBody ErroInfo handleNumberFormatException(HttpServletRequest request, ExceptionCustom ex) {

          return buildErrorInfo(request, ex, singletonList(ex.getMessage()));
     }
     
     
}