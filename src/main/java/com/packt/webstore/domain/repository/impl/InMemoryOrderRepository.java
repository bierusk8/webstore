package com.packt.webstore.domain.repository.impl;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.CartItem;
import com.packt.webstore.domain.Order;
import com.packt.webstore.domain.repository.OrderRepository;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

	private Map<Long, Order> listOfOrders;
	private long nextOrderId;
	
	private Connection myConn;
	
	private int addressId=0;
	private int shippingId=0;
	Random random = new Random();

	
	public InMemoryOrderRepository() {
	listOfOrders = new HashMap<Long, Order>();
	nextOrderId = 1000;
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webstore" ,"root", "");		

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	private void getNextAddressAndShippingId()
	{
		try {
			
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT MAX(shippingId) FROM shipping_details");
			myRs.next();
			shippingId = myRs.getInt(1) + 1;
			
			myRs = myStmt.executeQuery("SELECT MAX(addressId) FROM addresses");
			myRs.next();
			addressId = myRs.getInt(1) + 1;
			
			myRs = myStmt.executeQuery("SELECT MAX(orderId) FROM orders");
			myRs.next();
			nextOrderId = myRs.getInt(1) + 1;

			
			/*Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT 1 FROM shipping_details WHERE shippingId = " + shippingId);
			
			while(myRs.first())
			{
				shippingId = random.nextInt();
				myRs = myStmt.executeQuery("SELECT 1 FROM shipping_details WHERE shippingId = " + shippingId);
			}
			
			myRs = myStmt.executeQuery("SELECT 1 FROM addresses WHERE addressId = " + addressId);

			while(myRs.first())
			{
				addressId = random.nextInt();
				myRs = myStmt.executeQuery("SELECT 1 FROM addresses WHERE addressId = " + addressId);
			}
			
			myRs = myStmt.executeQuery("SELECT 1 FROM orders WHERE orderId = " + nextOrderId);

			while(myRs.first())
			{
				nextOrderId++;
				myRs = myStmt.executeQuery("SELECT 1 FROM orders WHERE orderId = " + nextOrderId);
			}
			
			myRs.close();*/
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public Long saveOrder(Order order) {
		
		getNextAddressAndShippingId();
		
		order.setOrderId(nextOrderId);
		listOfOrders.put(order.getOrderId(), order);
		
		//CART ITEMS TABLE
	
		String insertTableCartItems = "INSERT INTO cart_items "
			+ "(cartId, quantity, productId) VALUES"
			+ "(?,?,?)";
	
	
	
	try {	
		
		Map<String, CartItem> map = order.getCart().getCartItems();
	
		PreparedStatement ps = myConn.prepareStatement(insertTableCartItems);
		
		for (String key: map.keySet()) {
			
			
			ps.setString(1, order.getCart().getCartId());
			ps.setInt(2,map.get(key).getQuantity());
			ps.setString(3, map.get(key).getProduct().getProductId());	
			
			ps.addBatch();

		}
		
		ps.executeBatch();

		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//ADDRESSES TABLE
	
	String insertTableAddresses = "INSERT INTO addresses "
			+ "(addressId, doorNo, streetName, zipCode, country) VALUES"
			+ "(?,?,?,?,?)";
	
	try {	
		PreparedStatement ps = myConn.prepareStatement(insertTableAddresses);
		
		ps.setInt(1,addressId);
		ps.setString(2, order.getCustomer().getBillingAddress().getDoorNo());
		ps.setString(3, order.getCustomer().getBillingAddress().getStreetName());
		ps.setString(4, order.getCustomer().getBillingAddress().getZipCode());
		ps.setString(5, order.getCustomer().getBillingAddress().getCountry());
		
		ps.executeUpdate();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//SHIPPING DETAILS TABLE
	
	String insertTableShippingDetails = "INSERT INTO shipping_details "
			+ "(shippingId, name, shippingDate, addressId, cartId, totalPrice) VALUES"
			+ "(?,?,?,?,?,?)";
	
	try {	
		PreparedStatement ps = myConn.prepareStatement(insertTableShippingDetails);
		
		ps.setInt(1,shippingId);
		ps.setString(2, order.getShippingDetail().getName());

		java.util.Date utilDate = order.getShippingDetail().getShippingDate();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    
		ps.setDate(3, sqlDate);
		
		ps.setInt(4, addressId);
		ps.setString(5, order.getCart().getCartId());
		ps.setBigDecimal(6, order.getCart().getGrandTotal());
		
		ps.executeUpdate();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//CUSTOMERS TABLE
	
	String insertTableCustomers = "INSERT INTO customers "
			+ "(customerId, name, addressId, phoneNumber) VALUES"
			+ "(?,?,?,?)";
	
	try {	
		PreparedStatement ps = myConn.prepareStatement(insertTableCustomers);
		
		
		ps.setString(1,order.getCustomer().getCustomerId());
		ps.setString(2, order.getCustomer().getName());    
		ps.setInt(3, addressId++);	
		ps.setString(4, order.getCustomer().getPhoneNumber());
		
		ps.executeUpdate();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	//ORDERS TABLE
	
	String insertTableOrders = "INSERT INTO orders "
			+ "(orderId, customerId, shippingId) VALUES"
			+ "(?,?,?)";
	
	try {	
		PreparedStatement ps = myConn.prepareStatement(insertTableOrders);
			
		ps.setLong(1,order.getOrderId());
		ps.setString(2, order.getCustomer().getCustomerId());    
		ps.setInt(3, shippingId++);	
		
		ps.executeUpdate();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return order.getOrderId();
	}
	
	private synchronized long getNextOrderId() {
	return nextOrderId++;
	}

}
