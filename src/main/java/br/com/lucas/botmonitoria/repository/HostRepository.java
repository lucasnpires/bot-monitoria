package br.com.lucas.botmonitoria.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.lucas.botmonitoria.domain.entity.Host;

@Repository
public interface HostRepository extends MongoRepository<Host, String> {

	List<Host> findAllByEnv(String env);	
}
