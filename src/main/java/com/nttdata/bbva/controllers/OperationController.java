package com.nttdata.bbva.controllers;

import javax.validation.Valid;

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

import com.nttdata.bbva.documents.Customer;
import com.nttdata.bbva.documents.Operation;
import com.nttdata.bbva.services.ICustomerService;
import com.nttdata.bbva.services.IOperationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/1.0.0/operations")
public class OperationController {
	
	@Autowired
	private IOperationService service;
	
//	@GetMapping
//	@ResponseStatus(code = HttpStatus.OK)
//	public Flux<Customer> findAll(){
//		return service.findAll();
//	}
//	
//	@GetMapping("/{id}")
//	@ResponseStatus(code = HttpStatus.OK)
//	public Mono<Customer> fintById(@PathVariable("id") String id){
//		return service.findById(id);
//	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Operation> insert(@Valid @RequestBody Operation obj){
		return service.insert(obj);
	}
	
	@GetMapping("/{identificationDocument}/{productId}")
	@ResponseStatus(code = HttpStatus.OK)
	public Flux<Operation> findByIdentificationDocumentAndProductId(@PathVariable("identificationDocument") String identificationDocument, @PathVariable("productId") String productId){
		return service.findByIdentificationDocumentAndProductId(identificationDocument, productId);
	}
	
}
