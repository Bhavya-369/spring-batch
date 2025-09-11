package com.suri.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.suri.entity.Customer;
import com.suri.repo.CustomerRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerPdfService {
	
	private final CustomerRepo customerRepo;
	 /**
     * Generate PDF for a single customer
     */
	
	  public byte[] generatePdfForCustomer(Long customerId) {
		  
		  Customer customer = customerRepo.findById(customerId)
				  .orElseThrow(()->new RuntimeException("customer not found"));
		  return generatePdfForAll(List.of(customer));
	}
	  
	  /**
	     * Generate PDF for a list of customers
	     */
	public byte[] generatePdfForAll(List<Customer> customers) {
		 try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

	            PdfWriter writer = new PdfWriter(baos);
	            PdfDocument pdf = new PdfDocument(writer);
	            Document document = new Document(pdf);

	            document.add(new Paragraph("Customer Report"));
	            document.add(new Paragraph("-------------------"));

	            for (Customer c : customers) {
	                document.add(new Paragraph("ID: " + c.getId()));
	                document.add(new Paragraph("Name: " + c.getName() ));
	                document.add(new Paragraph("Email: " + c.getEmail()));
	                document.add(new Paragraph("Phone: " + c.getAge()));
	                document.add(new Paragraph("-------------------"));
	            }

	            document.close();
	            return baos.toByteArray();

	        } catch (Exception e) {
	        	 // Use information from the first customer if available
	            String customerInfo = customers.isEmpty() ? "no customers data found" : String.valueOf(customers.get(0).getId());
	            throw new RuntimeException("Failed to generate PDF for customer " + customerInfo, e);
	        }
	 }

	 
}
