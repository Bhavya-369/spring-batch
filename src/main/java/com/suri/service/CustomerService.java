package com.suri.service;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import com.suri.entity.Customer;
import com.suri.repo.CustomerRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final JobLauncher jobLauncher;
    private final Job importCustomerJob;
    private final CustomerRepo customerRepo;
    private final CustomerPdfService customerPdfService;

    // Launches the batch job manually
    public String launchJobManually() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(importCustomerJob, jobParameters);

            return "Job Status: " + jobExecution.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "Job failed: " + e.getMessage();
        }
    }

    // Get all customers from DB
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Generate PDF for all customers using CustomerPdfService
    public byte[] generatePdfForAllCustomers() {
        return customerPdfService.generatePdfForAll(getAllCustomers());
    }
}
