package br.com.lucas.botmonitoria.domain.exception;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel(description = "Resposta de erro da API")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ErroInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "timestamp", notes = "Data e hora do erro")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public LocalDateTime timestamp;

	@ApiModelProperty(value = "code", notes = "Código do erro")
	public Integer code;

	@ApiModelProperty(value = "exception", notes = "Exceção lançada")
	public String exception;

	@ApiModelProperty(value = "messages", notes = "Lista de mensagens de erro")
	public List<String> messages;

	@ApiModelProperty(value = "path", notes = "Path da chamada que ocasionou o erro")
	public String path;

}
