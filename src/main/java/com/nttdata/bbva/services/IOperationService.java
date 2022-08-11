package com.nttdata.bbva.services;

import com.nttdata.bbva.documents.Operation;

import reactor.core.publisher.Flux;

public interface IOperationService extends ICRUD<Operation, String> {
	Flux<Operation> findByIdentificationDocumentAndProductId(String identificationDocument, String productId);
}
