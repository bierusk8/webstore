package com.packt.webstore.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Address;
import com.packt.webstore.domain.OrderInfo;
import com.packt.webstore.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {

	private Connection myConn;
	
	@Override
	public List<OrderInfo> getAllOrdersInfo() {
	
		
		List<OrderInfo> infoList = new ArrayList<OrderInfo>();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");		
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webstore" ,"root", "");		
			Statement myStmt = myConn.createStatement();
			
			
			/*ResultSet myRs = myStmt.executeQuery("SELECT c.name AS fullname, c.phoneNumber, ad.doorNo, ad.streetName, ad.zipCode, ad.country, sd.name AS nickname, sd.shippingDate, sd.totalPrice, p.name AS productName "
					+ "FROM customers c "
					+ "JOIN orders o ON c.customerId = o.customerId "
					+ "JOIN addresses ad ON ad.addressId = c.addressId "
					+ "JOIN shipping_details sd ON sd.addressId = ad.addressId "
					+ "JOIN cart_items ci ON ci.cartId = sd.cartId "
					+ "JOIN products p WHERE p.productId = ci.productId ");*/
			
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM summary");
			
			
			while(myRs.next()){

				OrderInfo order = new OrderInfo();
				
				order.setFullName(myRs.getString("fullname"));
				order.setPhone(myRs.getString("phoneNumber"));
				
				Address address = new Address();
				address.setDoorNo(myRs.getString("doorNo"));
				address.setStreetName(myRs.getString("streetName"));
				address.setZipCode(myRs.getString("zipCode"));
				address.setCountry(myRs.getString("country"));			
				order.setShippingAddress(address);
				
				order.setShippingDate(myRs.getDate("shippingDate"));
		
				order.setNickName(myRs.getString("nickname"));
				order.setTotalPrice(myRs.getBigDecimal("totalPrice"));
				order.setProductName(myRs.getString("productName"));
								
				
				infoList.add(order);	
			}
		
		}catch(Exception e){}
		
		
		return infoList;
	}
	
	

}
