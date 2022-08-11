package com.nttdata.bbva.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bbva.documents.Product;

public interface IProductRepository extends ReactiveMongoRepository<Product, String> {}
