package com.nttdata.bbva.services;

import com.nttdata.bbva.documents.Customer;

import reactor.core.publisher.Mono;

public interface ICustomerService extends ICRUD<Customer, String> {
	Mono<Boolean> existCustomer(String identificationDocument);
}
