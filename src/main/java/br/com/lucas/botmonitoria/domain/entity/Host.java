package br.com.lucas.botmonitoria.domain.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.lucas.botmonitoria.enums.HostStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Host")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Host implements Serializable{
	private static final long serialVersionUID = -802664361141076573L;
	
	@Id
    private String id;
	
	private String host;
	
	private Integer porta;
	
	private String nomeApi;
	
	private String endpointCheck;
	
	private String env;
	
	private Date dataCadastro;
	
	@Transient
	@JsonIgnore
	private HostStatusEnum status;
	
	@Transient
	@JsonIgnore
	private Integer qtdVezesDesconectado;
}
