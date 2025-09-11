package com.suri.job.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.suri.entity.Customer;
import com.suri.repo.CustomerRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerWriter implements ItemWriter<Customer> {

	private final CustomerRepo customerRepo;

	@Override
	public void write(Chunk<? extends Customer> chunk) throws Exception {
		customerRepo.saveAll(chunk.getItems());

	}
}
