package com.nttdata.bbva.documents;

import javax.validation.constraints.Email;
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
@Document(collection = "customers")
public class Customer {

	@Id
	private String id;
	@NotEmpty(message = "El campo fullName es requerido.")
	private String fullName;
	@NotEmpty(message = "El campo customerTypeId es requerido.")
	private String customerTypeId;
	private CustomerType customerType;
	@NotEmpty(message = "El campo identificationDocument es requerido.")
	private String identificationDocument;
	@NotEmpty(message = "El campo emailAddress es requerido.")
	@Email(message = "El campo emailAddress tiene un formato no válido.")
	private String emailAddress;
	
}
