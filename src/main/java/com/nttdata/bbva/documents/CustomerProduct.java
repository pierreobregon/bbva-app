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
@Document(collection = "customerProduct")
public class CustomerProduct {
	@Id
	private String id;
	@NotEmpty(message = "El campo customerId es requerido.")
	private String customerId;
	@NotEmpty(message = "El campo productId es requerido.")
	private String productId;
	@DecimalMin(value = "0.0", message = "El campo amountAvailable debe tener un valor mínimo de '0.0'.")
	@Digits(integer = 10, fraction = 3, message = "El campo amountAvailable tiene un formato no válido (#####.000).")
	@NotNull(message = "El campo amountAvailable es requerido.")
	private BigDecimal amountAvailable; // Monto disponible
	private BigDecimal creditLine; // Monto de la linea de crédito (Tarjeta de crédito)

	private Customer customers;
	private Product product;
	
}
