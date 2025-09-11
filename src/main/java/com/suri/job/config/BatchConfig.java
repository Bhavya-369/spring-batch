package com.suri.job.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.suri.entity.Customer;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Step step1(JobRepository jobRepo,
                      PlatformTransactionManager transactionManager,
                      ItemReader<Customer> reader,
                      ItemProcessor<Customer, Customer> processor,
                      ItemWriter<Customer> writer) {

        return new StepBuilder("step1", jobRepo)
                .<Customer, Customer>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importCustomerJob(JobRepository jobRepo, Step step1) {
        return new JobBuilder("importCustomerJob", jobRepo)
                .start(step1)
                .build();
    }
}
