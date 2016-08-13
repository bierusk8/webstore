package com.packt.webstore.domain.repository;

import java.util.List;

import com.packt.webstore.domain.RenamedCustomer;

public interface CustomerRepository {

	List<RenamedCustomer> getAllCustomers();
	
}
