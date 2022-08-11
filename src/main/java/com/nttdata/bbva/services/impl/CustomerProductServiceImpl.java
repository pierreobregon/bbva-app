package com.nttdata.bbva.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bbva.documents.CustomerProduct;
import com.nttdata.bbva.exceptions.BadRequestException;
import com.nttdata.bbva.exceptions.ModelNotFoundException;
import com.nttdata.bbva.repositories.ICustomerProductRepository;
import com.nttdata.bbva.repositories.ICustomerRepository;
import com.nttdata.bbva.repositories.IProductRepository;
import com.nttdata.bbva.services.ICustomerProductService;
import com.nttdata.bbva.services.ICustomerService;
import com.nttdata.bbva.services.IProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerProductServiceImpl implements ICustomerProductService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerProductServiceImpl.class);
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ICustomerProductRepository repo;
	
	@Override
	public Mono<CustomerProduct> insert(CustomerProduct obj) {
		return customerRepository.findById(obj.getCustomerId())
				.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerId tiene un valor no válido.")))
				.flatMap(customer ->{
					return productRepository.findById(obj.getProductId())
							.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo productId tiene un valor no válido.")))
							.flatMap(product -> {
								return repo.save(obj)
										.map(contract -> {
											contract.setCustomers(customer);
											contract.setProduct(product);
											return contract;
										});
							});
				})
				.doOnNext(c -> logger.info("SE INSERTÓ EL CONTRATO ::: " + c.getId()));
	}

	@Override
	public Mono<CustomerProduct> update(CustomerProduct obj) {
		if (obj.getId() == null || obj.getId().isEmpty())
			return Mono.error(() -> new BadRequestException("El campo id es requerido."));
		
		return repo.findById(obj.getId())
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.flatMap(c -> {
					return customerRepository.findById(obj.getCustomerId())
							.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerId tiene un valor no válido.")))
							.flatMap(customer ->{
								return productRepository.findById(obj.getProductId())
										.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo productId tiene un valor no válido.")))
										.flatMap(product ->{
											return repo.save(obj)
													.map(contract -> {
														contract.setCustomers(customer);
														contract.setProduct(product);
														return contract;
													});
										});
							});
				})
				.doOnNext(c -> logger.info("SE ACTUALIZÓ EL CONTRATO ::: " + c.getId()));
	}

	@Override
	public Flux<CustomerProduct> findAll() {
		return repo.findAll()
				.flatMap(contract -> {
					return customerService.findById(contract.getCustomerId())
							.flatMap(customer -> {
								return productService.findById(contract.getProductId())
										.map(product -> {
											contract.setCustomers(customer);
											contract.setProduct(product);
											return contract;
										});
							});
				});
	}

	@Override
	public Mono<CustomerProduct> findById(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.flatMap(contract ->{
					return customerService.findById(contract.getCustomerId())
							.flatMap(customer -> {
								return productService.findById(contract.getProductId())
										.map(product -> {
											contract.setCustomers(customer);
											contract.setProduct(product);
											return contract;
										});
							});
				})
				.doOnNext(c -> logger.info("SE ENCONTRÓ EL CONTRATO ::: " + id));
	}

	@Override
	public Mono<Void> delete(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.flatMap(contract -> repo.deleteById(contract.getId()))
				.doOnNext(c -> logger.info("SE ELIMINÓ EL CONTRATO ::: " + id));
	}

	@Override
	public Mono<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId) {
		return repo.findAll()
				.filter(contracts -> contracts.getCustomerId().equals(customersId) && contracts.getProductId().equals(productId))
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.next()
				.doOnNext(c -> logger.info("SE ENCONTRÓ EL CONTRATO DEL CLIENTE ::: " + customersId + " Y PRODUCTO ::: " + productId));
				
	}

}
