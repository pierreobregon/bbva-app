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

import com.nttdata.bbva.documents.Product;
import com.nttdata.bbva.services.IProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/1.0.0/products")
public class ProductController {
	
	@Autowired
	private IProductService service;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Flux<Product> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Mono<Product> fintById(@PathVariable("id") String id){
		return service.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Product> insert(@Valid @RequestBody Product obj){
		return service.insert(obj);
	}
	
	@PutMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Product> update(@Valid @RequestBody Product obj){
		return service.update(obj);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable("id") String id) {
		return service.delete(id);
	}
}
