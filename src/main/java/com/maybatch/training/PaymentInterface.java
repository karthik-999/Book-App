package com.maybatch.training;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.maybatch.training.request.PaymentDetailsRequest;
import com.maybatch.training.response.PaymentDeductionResponseDTO;

@FeignClient(name = "payments",url = "localhost:8182/bank-app/payment")
public interface PaymentInterface {

	@GetMapping("/paymentDeduction")
	public ResponseEntity<PaymentDeductionResponseDTO> paymentDeduction(
			@RequestBody PaymentDetailsRequest paymentDetailsRequest);

}
