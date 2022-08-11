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
@Document(collection = "productTypes")
public class ProductType {
	
	@Id
	private String id;
	@NotEmpty(message = "El campo name es requerido.")
	private String name;
	
}
