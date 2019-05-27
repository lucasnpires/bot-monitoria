package br.com.lucas.botmonitoria.enums;

import lombok.Getter;

@Getter
public enum EnviromentEnum {
	
	DEV("DEV"),
	QA("QA"),
	STAGING("STAGING");
	
	private String valor;
	
	EnviromentEnum(String valor){
		this.valor = valor;
	}
}
