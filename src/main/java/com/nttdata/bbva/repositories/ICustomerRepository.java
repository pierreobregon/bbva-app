package com.nttdata.bbva.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bbva.documents.Customer;

public interface ICustomerRepository extends ReactiveMongoRepository<Customer, String> {}
