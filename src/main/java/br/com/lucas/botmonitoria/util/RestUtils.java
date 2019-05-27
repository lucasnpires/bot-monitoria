package br.com.lucas.botmonitoria.util;

import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class RestUtils {
	
	
	 public HttpHeaders initializeHeaders() {
         final HttpHeaders headers = new HttpHeaders();
         headers.setAccept(singletonList(APPLICATION_JSON_UTF8));
         headers.setContentType(APPLICATION_JSON_UTF8);
         return headers;
    }

	public HttpHeaders initializeHeaderHook() {
		final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return headers;
	}

}
