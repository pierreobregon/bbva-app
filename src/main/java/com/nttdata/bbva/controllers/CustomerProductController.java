package com.nttdata.bbva.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bbva.documents.CustomerProduct;
import com.nttdata.bbva.services.ICustomerProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/1.0.0/contracts")
public class CustomerProductController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerProductController.class);
	
	@Autowired
	private ICustomerProductService service;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Flux<CustomerProduct> findAll(){
		logger.info("Inicio ::: findAll");
		return service.findAll()
				.doOnNext(x -> logger.info("Fin ::: findAll"));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Mono<CustomerProduct> fintById(@PathVariable("id") String id){
		return service.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<CustomerProduct> insert(@Valid @RequestBody CustomerProduct obj){
		return service.insert(obj);
	}
	
	@PutMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<CustomerProduct> update(@Valid @RequestBody CustomerProduct obj){
		return service.update(obj);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable("id") String id) {
		return service.delete(id);
	}
}
