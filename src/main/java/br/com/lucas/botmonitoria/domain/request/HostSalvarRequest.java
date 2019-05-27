package br.com.lucas.botmonitoria.domain.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.lucas.botmonitoria.enums.EndpointChekEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class HostSalvarRequest implements Serializable {
	private static final long serialVersionUID = 7194264844055340565L;

	@Size(max = 100, message = "quantidade de caracteres deve ser menor que 100")
	@NotNull(message = "não pode ser null")
	@ApiModelProperty(value = "Host", example = "localhost", position = 1)
	private String host;

	@NotNull(message = "não pode ser null")
	@ApiModelProperty(value = "Número da porta", example = "8091", position = 2)
	private Integer porta;

	@NotNull(message = "não pode ser null")
	@ApiModelProperty(value = "Nome da API", example = "Guardian API", position = 3)
	private String nomeApi;
	
	
	@NotNull(message = "não pode ser null")
	@ApiModelProperty(value = "Endpoint de checkagem", example="ACTUATOR_INFO", position = 4)
	private EndpointChekEnum endpointCheck;

	@NotNull(message = "não pode ser null")
	@ApiModelProperty(value = "Environment do host", example = "DEV", position = 5)
	private String env;
}
