package br.com.lucas.botmonitoria.service;

import static br.com.lucas.botmonitoria.domain.exception.ExceptionsMessagesEnum.ERRO_SALVAR_HOST;
import static br.com.lucas.botmonitoria.domain.exception.ExceptionsMessagesEnum.GLOBAL_RECURSO_NAO_ENCONTRADO;
import static br.com.lucas.botmonitoria.enums.AppConstantesEnum.BARRA_DIREITA;
import static br.com.lucas.botmonitoria.enums.AppConstantesEnum.DOIS_PONTOS;
import static br.com.lucas.botmonitoria.enums.AppConstantesEnum.INICIO_URL_HOST;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lucas.botmonitoria.domain.entity.Host;
import br.com.lucas.botmonitoria.domain.request.HostSalvarRequest;
import br.com.lucas.botmonitoria.domain.response.PageHostsResponse;
import br.com.lucas.botmonitoria.exception.BadRequestCustom;
import br.com.lucas.botmonitoria.exception.ExceptionCustom;
import br.com.lucas.botmonitoria.repository.HostRepository;
import br.com.lucas.botmonitoria.util.GenericConvert;
import br.com.lucas.botmonitoria.util.RestUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HostService {

	@Autowired
	private HostRepository hostRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RestUtils restUtils;

	public List<Host> buscarTodosPorEnvironment(String environment) {
		return hostRepository.findAllByEnv(environment);
	}

	public Host save(HostSalvarRequest host) {
		Host hostSave = convertHostSalvarRequestInHost(host);

		hostSave.setDataCadastro(Timestamp.from(Instant.now()));

		try {
			hostSave = hostRepository.save(hostSave);
		} catch (Exception e) {
			log.error("Problema ao salvar host: {} _id: {}", hostSave.getHost() + ":" + hostSave.getPorta().toString(),
					hostSave.getId(), e);
			ExceptionCustom.checkThrow(Objects.isNull(hostSave.getId()), ERRO_SALVAR_HOST);
		}

		return hostSave;
	}

	public PageHostsResponse findAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Host> listHosts = hostRepository.findAll(pageable);

		BadRequestCustom.checkThrow(Objects.isNull(listHosts), GLOBAL_RECURSO_NAO_ENCONTRADO);

		return new PageHostsResponse(
				GenericConvert.convertModelMapperToPageResponse(listHosts, new TypeToken<List<Host>>() {
				}.getType()));
	}

	private Host convertHostSalvarRequestInHost(HostSalvarRequest host) {
		return Host.builder().env(host.getEnv()).nomeApi(host.getNomeApi()).host(host.getHost()).porta(host.getPorta())
				.endpointCheck(host.getEndpointCheck().getValor()).build();
	}

	public List<Host> buscarTodos() {
		return hostRepository.findAll();
	}

	public ResponseEntity<String> chamadaGet(Host host) {
		ResponseEntity<String> response = null;
		try {
			String httpUrl = INICIO_URL_HOST.getValor() + host.getHost() + DOIS_PONTOS.getValor() + host.getPorta()
					+ BARRA_DIREITA.getValor() + host.getEndpointCheck();

			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(httpUrl);
			HttpHeaders headers = restUtils.initializeHeaders();
			HttpEntity<?> entity = new HttpEntity<>(headers);

			response = restTemplate.exchange(builder.build().encode().toUri(), GET, entity, String.class);

		} catch (Exception e) {
			response = new ResponseEntity<>(BAD_REQUEST);
		}

		return response;

	}
}
