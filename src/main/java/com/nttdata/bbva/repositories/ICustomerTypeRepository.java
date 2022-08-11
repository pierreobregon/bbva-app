package com.nttdata.bbva.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bbva.documents.CustomerType;

public interface ICustomerTypeRepository extends ReactiveMongoRepository<CustomerType, String> {}
