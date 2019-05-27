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
public class AttachmentRequest implements Serializable {
	private static final long serialVersionUID = -2328951594211730261L;
	
	private String fallback;
	
	private String color;
	
	private String pretext;
	
	private String author_name;
	
	private String author_link;
	
	private String author_icon;
	
	private String title;
	
	private String title_link;
	
	private String text;
	
	private List<FieldRequest> fields;
	
	private String image_url;
	
	private String thumb_url;
	
	private String footer;
	
	private String footer_icon;
	
	private Long ts;

}
