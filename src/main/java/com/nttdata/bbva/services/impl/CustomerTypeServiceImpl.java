package com.nttdata.bbva.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bbva.documents.CustomerType;
import com.nttdata.bbva.exceptions.ModelNotFoundException;
import com.nttdata.bbva.repositories.ICustomerTypeRepository;
import com.nttdata.bbva.services.ICustomerTypeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerTypeServiceImpl implements ICustomerTypeService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
			
	@Autowired
	private ICustomerTypeRepository repo;
	
	@Override
	public Mono<CustomerType> insert(CustomerType obj) {
		return repo.save(obj)
				.doOnNext(tc -> logger.info("SE INSERTÓ EL TIPO CLIENTE ::: " + tc.getId()));
	}

	@Override
	public Mono<CustomerType> update(CustomerType obj) {
		return repo.save(obj);
	}

	@Override
	public Flux<CustomerType> findAll() {
		return repo.findAll();
	}

	@Override
	public Mono<CustomerType> findById(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("TIPO CLIENTE NO ENCONTRADO")));
	}

	@Override
	public Mono<Void> delete(String id) {
		return repo.deleteById(id);
	}

}
