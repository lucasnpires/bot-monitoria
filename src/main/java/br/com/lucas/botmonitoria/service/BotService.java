package br.com.lucas.botmonitoria.service;

import static br.com.lucas.botmonitoria.enums.AppConstantesEnum.BARRA_DIREITA;
import static br.com.lucas.botmonitoria.enums.AppConstantesEnum.INICIO_URL_HOST;
import static br.com.lucas.botmonitoria.enums.EnviromentEnum.QA;
import static br.com.lucas.botmonitoria.enums.EnviromentEnum.STAGING;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lucas.botmonitoria.domain.bot.AttachmentRequest;
import br.com.lucas.botmonitoria.domain.bot.FieldRequest;
import br.com.lucas.botmonitoria.domain.bot.MessageRequest;
import br.com.lucas.botmonitoria.domain.entity.Host;
import br.com.lucas.botmonitoria.util.JsonUtil;
import br.com.lucas.botmonitoria.util.RestUtils;

@Service
public class BotService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	private RestUtils restUtils;
	
	@Value("${app.pathWebHook}")
	private String pathWebHook;

	public ResponseEntity<String> enviarMensagem(Host host) {
		ResponseEntity<String> response = null;
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(pathWebHook);
			HttpHeaders headers = restUtils.initializeHeaderHook();

			MessageRequest messageRequest = personalizarMessageRequest(host);
			
			String jsonString = jsonUtil.toJsonString(messageRequest);

			HttpEntity<?> entity = new HttpEntity<>(messageRequest, headers);

			response = restTemplate.exchange(builder.build().encode().toUri(), POST, entity, String.class);

		} catch (Exception e) {
			response = new ResponseEntity<>(BAD_REQUEST);
		}
		return response;
	}

	private MessageRequest personalizarMessageRequest(Host host) {
		List<FieldRequest> fields = new ArrayList<>();
		
		FieldRequest fieldRequest = FieldRequest
				.builder()
				.title("Prioridade")
				.build();
		
		if(host.getEnv().equalsIgnoreCase(STAGING.getValor())) {
			fieldRequest.setValue("Alta");
		} else if(host.getEnv().equalsIgnoreCase(QA.getValor())) {
			fieldRequest.setValue("Média");
		} else {
			fieldRequest.setValue("Baixa");
		}
		
		fields.add(fieldRequest);
		
		List<AttachmentRequest> attachments = new ArrayList<>();
		attachments.add(AttachmentRequest
				.builder()
				.pretext("Estamos com problemas no ambiente de "+host.getEnv())
				.author_name(host.getNomeApi())
				.text("Endpoint Check: " + INICIO_URL_HOST.getValor() + host.getHost() + ":" + host.getPorta() + BARRA_DIREITA.getValor() + host.getEndpointCheck())
				.color("#1e5ee1")
				.author_icon("https://www.benvisavale.com.br/wp-content/uploads/2018/07/Logo_Ben.png")
				.fields(fields)
				.footer("Boot Monitoramento")
				.ts(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant().toEpochMilli())
				.build());
		
		return MessageRequest.builder().attachments(attachments).build();
	}
}
