package br.com.lucas.botmonitoria.resource;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.botmonitoria.domain.entity.Host;
import br.com.lucas.botmonitoria.domain.request.HostSalvarRequest;
import br.com.lucas.botmonitoria.domain.response.PageHostsResponse;
import br.com.lucas.botmonitoria.service.HostService;

@RestController
@RequestMapping(value = "/hosts", produces = APPLICATION_JSON_UTF8_VALUE)
public class HostResource implements HostDefinition{
	
	@Autowired
	private HostService hostService;
	
	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Host> save(@RequestBody HostSalvarRequest host) {
		Host response = hostService.save(host);
		return new ResponseEntity<>(response, CREATED);
	}

	@GetMapping(value = "/list", produces = APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PageHostsResponse> listHosts(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "50") Integer size) {
		return ResponseEntity.ok(hostService.findAll(page, size));
	}

}
