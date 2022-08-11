package com.nttdata.bbva.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bbva.documents.CustomerProduct;

public interface ICustomerProductRepository extends ReactiveMongoRepository<CustomerProduct, String> {}
