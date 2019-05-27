package br.com.lucas.botmonitoria.resource;

import static br.com.lucas.botmonitoria.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_400;
import static br.com.lucas.botmonitoria.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_404;
import static br.com.lucas.botmonitoria.exception.GlobalExceptionHandler.MENSAGEM_GLOBAL_500;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.lucas.botmonitoria.domain.entity.Host;
import br.com.lucas.botmonitoria.domain.exception.ErroInfo;
import br.com.lucas.botmonitoria.domain.request.HostSalvarRequest;
import br.com.lucas.botmonitoria.domain.response.PageHostsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(produces = APPLICATION_JSON_UTF8_VALUE, tags = { "Hosts" })
public interface HostDefinition {
	
	@ApiOperation(value = "Save Host", notes = "Save Host")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Host saved", response = Host.class),
		 	@ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
			@ApiResponse(code = 404, message = MENSAGEM_GLOBAL_404, response = ErroInfo.class),
			@ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class) })
	ResponseEntity<Host> save(@RequestBody HostSalvarRequest host);
	
	
	@ApiOperation(value = "List Hosts", notes = "List Hosts", response = PageHostsResponse.class)
	@ApiResponses({ 
			@ApiResponse(code = 400, message = MENSAGEM_GLOBAL_400, response = ErroInfo.class),
			@ApiResponse(code = 404, message = MENSAGEM_GLOBAL_404, response = ErroInfo.class),
			@ApiResponse(code = 500, message = MENSAGEM_GLOBAL_500, response = ErroInfo.class) })
	ResponseEntity<PageHostsResponse> listHosts(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "50") Integer size);

}
