package com.suri.job.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.suri.entity.Customer;

@Component
public class CustomerProcessor implements ItemProcessor<Customer, Customer>{

	@Override
	public Customer process(Customer item) throws Exception {
		
		  // Transform name to uppercase
        item.setName(item.getName().toUpperCase());
        
        //Validate email (skip if invalid)
        
        if(item.getEmail()==null || !item.getEmail().contains("@")) {
        	 return null; // Spring Batch will skip this record
        }
        
        //Validate age (skip if invalid)
        if (item.getAge() == null || item.getAge() < 18) {
            return null; // skip invalid age
        }
        // return processed item
		return item;
	}

}
