package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.RenamedCustomer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

	private List<RenamedCustomer> listOfCustomers = new ArrayList<RenamedCustomer>();
	
	public InMemoryCustomerRepository()
	{
		RenamedCustomer marcin = new RenamedCustomer("#1","Marcin","80.41.4.6","Ksi¹¿ka Harry Potter",3);
		RenamedCustomer andrzej = new RenamedCustomer("#2","Andrzej","47.50.10.11","Laptop Lenovo",10);
		RenamedCustomer anna = new RenamedCustomer("#3","Anna","96.75.31.2","Monitor Samsung",5);
		RenamedCustomer michal = new RenamedCustomer("#4","Micha³","87.96.2.1","S³uchawki Phliphs",1);
		RenamedCustomer john = new RenamedCustomer("#5","John","192.168.0.1","P³yty CD Pack x100",51);
		
		listOfCustomers.add(marcin);
		listOfCustomers.add(andrzej);
		listOfCustomers.add(anna);
		listOfCustomers.add(michal);
		listOfCustomers.add(john);
		
	}
	
	
	
	
	

	public List<RenamedCustomer> getAllCustomers() {

		return  listOfCustomers;
	}

}
