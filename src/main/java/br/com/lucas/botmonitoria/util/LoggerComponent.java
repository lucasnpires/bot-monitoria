package br.com.lucas.botmonitoria.util;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggerComponent {

     @Async
     public void logSuccess(final String messageId, final Object data, final String exchange) {

          log.info("Sent Message ID -> {}", messageId);
          log.debug("Queuing {} to {}", data, exchange);
     }

}
