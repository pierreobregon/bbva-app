package com.nttdata.bbva.services;

import com.nttdata.bbva.documents.CustomerProduct;

import reactor.core.publisher.Mono;

public interface ICustomerProductService extends ICRUD<CustomerProduct, String> {
	Mono<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId);
}
