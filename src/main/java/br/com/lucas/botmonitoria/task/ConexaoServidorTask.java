package br.com.lucas.botmonitoria.task;

import static br.com.lucas.botmonitoria.enums.HostStatusEnum.AVISADO_NO_CHANNEL;
import static br.com.lucas.botmonitoria.enums.HostStatusEnum.SEM_STATUS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.lucas.botmonitoria.domain.entity.Host;
import br.com.lucas.botmonitoria.service.BotService;
import br.com.lucas.botmonitoria.service.HostService;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class ConexaoServidorTask {

	private static final String TIME_ZONE = "America/Sao_Paulo";

	@Autowired
	private HostService hostService;

	@Autowired
	private BotService botService;

	@Value("${app.intervaloVerificacao}")
	private Integer intervaloVerificacao;

	@Value("${app.horaDesligamento}")
	private Integer horaDesligamento;

	@Value("${app.qtdVezesDelayMsgChannel}")
	private Integer qtdVezesDelayMsgChannel;

	int loopHostDesconectado = 0;

//  CRON
//	A B C D E F
//	A: Segundos (0 – 59).
//	B: Minutos (0 – 59).
//	C: Horas (0 – 23).
//	D: Dia (1 – 31).
//	E: Mês (1 – 12).
//	F: Dia da semana (0 – 6).

	/**
	 * 
	 * Método responsável por agendar o início da tarefa e buscar no banco de dados
	 * os hosts a serem verificados baseado no CRON 
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0 40 20 * * *", zone = TIME_ZONE)
	public void tarefaPrincipal() throws InterruptedException {
		log.info("Tarefa de verificação dos servidores comecará agora");

		List<Host> listaHosts = hostService.buscarTodos();

		if (listaHosts.isEmpty() || Objects.isNull(listaHosts)) {
			log.warn("Não encontrou nenhum servidor cadastrado");
		}

		while (antesDoDesligamentoAutomatico()) {
			verificaStatusHosts(listaHosts);

			Thread.sleep(intervaloVerificacao);
		}
	}

	/**
	 * @param listaHosts 
	 * 
	 * Método responsável por verificar se os hosts (APIs) estão conectadas ou não
	 * 
	 * @throws InterruptedException
	 */
	private void verificaStatusHosts(List<Host> listaHosts) throws InterruptedException {

		for (Host host : listaHosts) {
			if (host.getQtdVezesDesconectado() == null) {
				host.setQtdVezesDesconectado(0);
			}
			
			if(host.getStatus() == null) {
				host.setStatus(SEM_STATUS);
			}

			ResponseEntity<String> response = hostService.chamadaGet(host);

			if (response.getStatusCode() == OK) {
				log.info("Host conectado => {} : {}", host.getHost(), host.getPorta());
			} else if (response.getStatusCode() == BAD_REQUEST) {
				log.info("Host desconectado => {} : {}", host.getHost(), host.getPorta());

				if (host.getQtdVezesDesconectado() % qtdVezesDelayMsgChannel == 0) {
					botService.enviarMensagem(host);
					host.setStatus(AVISADO_NO_CHANNEL);
				}
				host.setQtdVezesDesconectado(host.getQtdVezesDesconectado() + 1);
			} 
		}
	}

	/**
	 * Método responsável por verificar se o horário de agora é menor do que o
	 * horário de desligamento da verificação
	 * 
	 * @return true ou false
	 */
	private boolean antesDoDesligamentoAutomatico() {
		return LocalDate.now().atTime(horaDesligamento, 0).isAfter(LocalDateTime.now());
	}
}
