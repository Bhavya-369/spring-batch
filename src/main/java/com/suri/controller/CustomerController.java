package com.suri.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.suri.service.CustomerPdfService;
import com.suri.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	 private final CustomerPdfService customerPdfService;
	
	/**
     * Endpoint to manually trigger the batch job
     * URL: /run-batch
     */
	  @GetMapping("/run-batch")
	public String runBatchJob() {
		 return customerService.launchJobManually();
	}
	  
	  /**
	     * Generate PDF for a single customer
	     */
	    @GetMapping("/{id}/pdf")
	    public ResponseEntity<byte[]> getCustomerPdf(@PathVariable Long id) {
	        byte[] pdfBytes = customerPdfService.generatePdfForCustomer(id);

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=customer_" + id + ".pdf")
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(pdfBytes);
	    }
	    
	    /**
	     * Generate PDF for all customers (or filtered)
	     */
	    @GetMapping("/allpdf")
	    public ResponseEntity<byte[]> generatePdfForAll() {
	    	 try {
	        byte[] pdfBytes = customerService.generatePdfForAllCustomers();

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=all_customers.pdf")
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(pdfBytes);
	    	 } catch (RuntimeException e) {
	             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                     .contentType(MediaType.TEXT_PLAIN)
	                     .body(("Error generating PDF: " + e.getMessage()).getBytes());
	         }
	    }

}
