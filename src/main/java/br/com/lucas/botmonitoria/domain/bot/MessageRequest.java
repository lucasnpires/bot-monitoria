package br.com.lucas.botmonitoria.domain.bot;

import java.io.Serializable;
import java.util.List;

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
public class MessageRequest implements Serializable {
	private static final long serialVersionUID = -2973183294887855870L;
	
	private List<AttachmentRequest> attachments;

}
