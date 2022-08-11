package com.nttdata.bbva.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bbva.documents.Operation;

public interface IOperationRepository extends ReactiveMongoRepository<Operation, String> {}
