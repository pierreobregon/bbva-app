package com.nttdata.bbva.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bbva.documents.ProductType;

public interface IProductTypeRepository extends ReactiveMongoRepository<ProductType, String> {}
