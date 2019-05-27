
package br.com.lucas.botmonitoria.domain.exception;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import br.com.lucas.botmonitoria.exception.BadRequestCustom;
import br.com.lucas.botmonitoria.exception.ConflictedCustom;
import br.com.lucas.botmonitoria.exception.ExceptionCustom;
import br.com.lucas.botmonitoria.exception.ForbiddenCustom;
import br.com.lucas.botmonitoria.exception.NoContentCustom;
import br.com.lucas.botmonitoria.exception.NotFoundCustom;
import br.com.lucas.botmonitoria.exception.PreconditionCustom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum ExceptionsMessagesEnum {

    GLOBAL_BAD_REQUEST(BAD_REQUEST, "Falha na requisição" , BadRequestCustom.class),
    GLOBAL_ERRO_SERVIDOR(INTERNAL_SERVER_ERROR, "Erro interno de servidor", ExceptionCustom.class),
    GLOBAL_RECURSO_NAO_ENCONTRADO(NOT_FOUND, "Recurso não encontrado", NotFoundCustom.class),
    GLOBAL_ERRO_CONEXAO_CIPHER(INTERNAL_SERVER_ERROR, "Global erro conexão cipher", ExceptionCustom.class),
    GLOBAL_ERRO_GENERIGO_CIPHER(BAD_REQUEST, "Global erro generigo cipher", BadRequestCustom.class), 
    PROBLEMA_ATUALIZAR_HOST(INTERNAL_SERVER_ERROR, "Erro ao atualizar o host", ExceptionCustom.class), 
    ERRO_SALVAR_HOST(INTERNAL_SERVER_ERROR, "Erro ao salvar o host" , ExceptionCustom.class);

    private HttpStatus code;

    @Setter
    private String message;

    private Class<? extends ExceptionCustom> klass;

    ExceptionsMessagesEnum(HttpStatus code, String message, Class<? extends ExceptionCustom> klass) {

        this.message = message;
        this.klass = klass;
        this.code = code;
    }

    public static ExceptionsMessagesEnum getEnum(final String key) {

        return asList(ExceptionsMessagesEnum.values()).stream().filter(e -> StringUtils.equals(e.getMessage(), key)).findAny().orElse(null);
    }

    public void raise() throws ExceptionCustom {

        log.debug("Raising error: {}", this);
        switch (this.code) {
            case BAD_REQUEST:
                throw new BadRequestCustom(this.message);
            case NOT_FOUND:
                throw new NotFoundCustom(this.message);
            case CONFLICT:
                throw new ConflictedCustom(this.message);
            case NO_CONTENT:
                throw new NoContentCustom(this.message);
            case PRECONDITION_FAILED:
            	throw new PreconditionCustom(this.message);
            case FORBIDDEN:
              throw new ForbiddenCustom(this.message);
            default:
                throw new ExceptionCustom(INTERNAL_SERVER_ERROR, this.message);
        }
    }

    public void raise(String errorMessage) {

        this.setMessage(errorMessage);
        this.raise();
    }

    public void raiseLogError(String... textoErro) {

        asList(textoErro).stream().forEach(erro -> log.error(erro));
        raise();
    }

}
