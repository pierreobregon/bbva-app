package com.nttdata.bbva.documents;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
	
	@Id
	private String id;
	@NotEmpty(message = "El campo name es requerido.")
	private String name;
	@NotEmpty(message = "El campo productTypeId es requerido.")
	private String productTypeId;
	private ProductType productType;
	
}
