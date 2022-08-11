package com.nttdata.bbva.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bbva.documents.Customer;
import com.nttdata.bbva.exceptions.BadRequestException;
import com.nttdata.bbva.exceptions.ModelNotFoundException;
import com.nttdata.bbva.repositories.ICustomerRepository;
import com.nttdata.bbva.repositories.ICustomerTypeRepository;
import com.nttdata.bbva.services.ICustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements ICustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private ICustomerTypeRepository customerTypeRepository;
	
	@Autowired
	private ICustomerRepository repo;
	
	@Override
	public Mono<Customer> insert(Customer obj) {
		return customerTypeRepository.findById(obj.getCustomerTypeId())
				.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerTypeId tiene un valor no válido.")))
				.flatMap(customerType ->{
					return this.existCustomer(obj.getIdentificationDocument())
							.flatMap(existCustomer -> {
								if(existCustomer)
									return Mono.error(() -> new BadRequestException("El cliente ya está registrado."));
								else
									return repo.save(obj)
											.map(customer -> {
												customer.setCustomerType(customerType);
												return customer;
											});
							});
				})
				.doOnNext(c -> logger.info("SE INSERTÓ EL CLIENTE ::: " + c.getId()));
	}

	@Override
	public Mono<Customer> update(Customer obj) {
		if (obj.getId() == null || obj.getId().isEmpty())
			return Mono.error(() -> new BadRequestException("El campo id es requerido."));
		
		return repo.findById(obj.getId())
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CLIENTE NO ENCONTRADO")))
				.flatMap(c -> {
					return customerTypeRepository.findById(obj.getCustomerTypeId())
							.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerTypeId tiene un valor no válido.")))
							.flatMap(customerType ->{
								return repo.save(obj)
										.map(customer -> {
											customer.setCustomerType(customerType);
											return customer;
										});
							});
				})
				.doOnNext(c -> logger.info("SE ACTUALIZÓ EL CLIENTE ::: " + c.getId()));
	}

	@Override
	public Flux<Customer> findAll() {
		return repo.findAll()
				.flatMap(customer ->{
					return customerTypeRepository.findById(customer.getCustomerTypeId())
							.map(customerType -> {
								customer.setCustomerType(customerType);
								return customer;
							});
				});
	}

	@Override
	public Mono<Customer> findById(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CLIENTE NO ENCONTRADO")))
				.flatMap(customer ->{
					return customerTypeRepository.findById(customer.getCustomerTypeId())
							.map(customerType -> {
								customer.setCustomerType(customerType);
								return customer;
							});
				})
				.doOnNext(c -> logger.info("SE ENCONTRÓ EL CLIENTE ::: " + id));
	}

	@Override
	public Mono<Void> delete(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CLIENTE NO ENCONTRADO")))
				.flatMap(customer -> repo.deleteById(customer.getId()))
				.doOnNext(c -> logger.info("SE ELIMINÓ EL CLIENTE ::: " + id));
	}

	@Override
	public Mono<Boolean> existCustomer(String identificationDocument) {
		return repo.findAll()
//				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CLIENTES NO ENCONTRADO")))
				.filter(customers -> customers.getIdentificationDocument().equals(identificationDocument))
//				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("NO SE ENCONTRÓ EL CLIENTE CON NÚMERO DE DOCUMENTO ::: " + identificationDocument)))
				.next()
				.hasElement()
				.doOnNext(c -> logger.info("SE ENCONTRÓ EL CLIENTE CON NÚMERO DE DOCUMENTO ::: " + identificationDocument));
	}

}
