package com.suri.job.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.suri.entity.Customer;

@Configuration
public class CsvFileReader {
	
	@Bean
	public FlatFileItemReader<Customer> reader(){

		
		return new FlatFileItemReaderBuilder<Customer>()
				.name("CustomerReader")
				.resource(new ClassPathResource("input/data.csv"))
				.delimited()
				.names("name","email","age")
				.fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
					 setTargetType(Customer.class);					
				}}).build();
				
				
		
	}

}
