package br.com.lucas.botmonitoria.enums;

import lombok.Getter;

@Getter
public enum AppConstantesEnum {
	
	INICIO_URL_HOST("http://"),
	DOIS_PONTOS(":"),
	BARRA_DIREITA("/");
	
	private String valor;
	
	AppConstantesEnum(String valor){
		this.valor = valor;
	}

}
