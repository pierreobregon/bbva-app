package com.nttdata.bbva.documents;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@Document(collection = "operations")
public class Operation {
	
	@Id
	private String id;
	@NotEmpty(message = "El campo customerProductId es requerido.")
	private String customerProductId;
	@NotEmpty(message = "El campo operationType es requerido.")
	private String operationType; // Depósito: D, Retiro: R, Pagos: P
	@DecimalMin(value = "0.0", message = "El campo amount debe tener un valor mínimo de '0.0'.")
	@Digits(integer = 10, fraction = 3, message = "El campo amount tiene un formato no válido (#####.000).")
	@NotNull(message = "El campo amount es requerido.")
	private BigDecimal amount;
	
	private CustomerProduct customerProduct;
}
