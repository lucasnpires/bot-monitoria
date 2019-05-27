package br.com.lucas.botmonitoria.enums;

import lombok.Getter;

@Getter
public enum EndpointChekEnum {
	
	ACTUATOR_INFO("actuator/info"),
	ACTUATOR_HEALTH("actuator/health");
	
	private String valor;
	
	EndpointChekEnum(String valor){
		this.valor = valor;
	}

}
