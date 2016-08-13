package com.packt.webstore.domain;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class CartTest {
	
	private Cart cart;
	
	@Before
	public void setup() {
	cart = new Cart();
	}
	
	@Test
	public void cart_test_in_case_of_one_quantity()
	{
		Product iphone = new Product("P1234","iPhone 5s", new BigDecimal(1300));
		Product ipad =  new Product("P1235","iPad", new BigDecimal(700));
	
		CartItem cart1 = new CartItem();
		CartItem cart2 = new CartItem();
		
		cart1.setProduct(iphone);
		cart2.setProduct(ipad);
		
		cart.addCartItem(cart1);
		cart.addCartItem(cart2);
		
		Double grandTotal = cart.getGrandTotal().doubleValue();
		Double sum = cart1.getProduct().getUnitPrice().doubleValue() + cart2.getProduct().getUnitPrice().doubleValue();
		
		System.out.println(grandTotal);
		System.out.println("\n" + sum);
		
		Assert.assertEquals(grandTotal,sum);		
	}

}
