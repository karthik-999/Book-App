package com.maybatch.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maybatch.training.PaymentInterface;
import com.maybatch.training.request.PaymentDetailsRequest;
import com.maybatch.training.response.PaymentDeductionResponseDTO;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	PaymentInterface paymentService;

	@PostMapping("/payment-deduction/")
	public ResponseEntity<PaymentDeductionResponseDTO> paymentDeduction(
			@RequestBody PaymentDetailsRequest paymentDetailsRequest) {

		var paymentDetailsResponse =  paymentService.paymentDeduction(paymentDetailsRequest);
		return paymentDetailsResponse;

	}

}
