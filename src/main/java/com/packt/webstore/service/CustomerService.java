package com.packt.webstore.service;

import java.util.List;

import com.packt.webstore.domain.RenamedCustomer;

public interface CustomerService {
	
	List<RenamedCustomer> getAllCustomers();

}
