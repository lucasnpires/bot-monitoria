package br.com.lucas.botmonitoria.domain.bot;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldRequest implements Serializable {
	private static final long serialVersionUID = 9012908706033239962L;

	private String title;
	
	private String value;

}
