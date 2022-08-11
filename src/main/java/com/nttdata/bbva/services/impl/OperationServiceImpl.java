package com.nttdata.bbva.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bbva.documents.Operation;
import com.nttdata.bbva.exceptions.BadRequestException;
import com.nttdata.bbva.exceptions.ModelNotFoundException;
import com.nttdata.bbva.repositories.IOperationRepository;
import com.nttdata.bbva.services.ICustomerProductService;
import com.nttdata.bbva.services.ICustomerService;
import com.nttdata.bbva.services.IOperationService;
import com.nttdata.bbva.services.IProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OperationServiceImpl implements IOperationService {
	private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	@Autowired
	private IOperationRepository repo;
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ICustomerProductService contractService;

	@Override
	public Mono<Operation> insert(Operation obj) {
		return contractService.findById(obj.getCustomerProductId())
				.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerProductId tiene un valor no válido.")))
				.flatMap(contract -> {
					if(obj.getOperationType().equals("D"))
						contract.setAmountAvailable(contract.getAmountAvailable().add(obj.getAmount()));
					else if(obj.getOperationType().equals("R"))
						contract.setAmountAvailable(contract.getAmountAvailable().subtract(obj.getAmount()));
//					else if(obj.getOperationType().equals("P"))
//						contract.setAmountAvailable(contract.getAmountAvailable().add(obj.getAmount()));
					
					return contractService.update(contract)
							.flatMap(updateContract -> {
								return repo.save(obj)
										.map(operation -> {
											operation.setCustomerProduct(contract);
											return operation;
										});
							});
				})
				.doOnNext(o -> logger.info("SE INSERTÓ EL MOVIMIENTO ::: " + o.getId()));
	}

	@Override
	public Mono<Operation> update(Operation obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Operation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Operation> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Operation> findByIdentificationDocumentAndProductId(String identificationDocument, String productId) {
		return customerService.findAll()
				.filter(customers -> customers.getIdentificationDocument().equals(identificationDocument))
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CLIENTE NO ENCONTRADO")))
				.next()
				.flatMapMany(customer -> {
					return productService.findById(productId)
							.flatMapMany(product -> {
								return contractService.findByCustomersIdAndProductId(customer.getId(), productId)
										.flatMapMany(contract -> {
											return repo.findAll()
													.filter(operations -> operations.getCustomerProductId().equals(contract.getId()))
													.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("MOVIMIENTO NO ENCONTRADO")))
													.map(o -> {
														contract.setCustomers(customer);
														contract.setProduct(product);
														o.setCustomerProduct(contract);
														return o;
													})
													.doOnNext(o -> logger.info("SE ENCONTRÓ LAS MOVIMIENTOS DEL CLIENTE ::: " + customer.getFullName()));
										});
							});
				});
	}

}
