package com.maybatch.training.request;

import lombok.Data;

@Data
public class PaymentDetailsRequest {

	private String accountNumber;
	
	private Double price;

}
