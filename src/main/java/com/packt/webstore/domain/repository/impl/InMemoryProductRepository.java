package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.ProductNotFoundException;

@Repository
public class InMemoryProductRepository implements ProductRepository{
	
private List<Product> listOfProducts = new ArrayList<Product>();

@Autowired
private DataSource dataSource;

private Connection myConn;


public InMemoryProductRepository() {
	

/*Product iphone = new Product("P1234","iPhone 5s", new BigDecimal(1500));
iphone.setDescription("Apple iPhone 5s, smartfon z 4-calowym ekranem o rozdzielczoœci 640x1136 i 8-megapikselowym aparatem");
iphone.setCategory("Smartfon");
iphone.setManufacturer("Apple");
iphone.setUnitsInStock(1000);

Product laptop_dell = new Product("P1235","Dell Inspiron", new BigDecimal(700));
laptop_dell.setDescription("Dell Inspiron, 14-calowy laptop (czarny) z procesorem Intel Core 3. generacji");
laptop_dell.setCategory("Laptop");
laptop_dell.setManufacturer("Dell");
laptop_dell.setUnitsInStock(1000);

Product tablet_Nexus = new Product("P1236","Nexus 7", new BigDecimal(769));
tablet_Nexus.setDescription("Google Nexus 7 jest najl¿ejszym 7-calowym tabletem z 4-rdzeniowym procesorem Qualcomm Snapdragon™ S4 Pro");
tablet_Nexus.setCategory("Tablet");
tablet_Nexus.setManufacturer("Google");
tablet_Nexus.setUnitsInStock(1000);

Product lg_leon = new Product("P1237","LG Leon", new BigDecimal(499));
lg_leon.setDescription("LG Leon, 4G LTE aparat przedni 2MPix 6-cali");
lg_leon.setCategory("Smartfon");
lg_leon.setManufacturer("LG");
lg_leon.setUnitsInStock(800);*/
	
	
	try{
		Class.forName("com.mysql.jdbc.Driver");		
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webstore" ,"root", "");		
		Statement myStmt = myConn.createStatement();
		
		
		ResultSet myRs = myStmt.executeQuery("select * from products");
		
		
		while(myRs.next()){

			
			String productId = myRs.getString("productId");
			String name = myRs.getString("name");
			BigDecimal price = myRs.getBigDecimal("unitPrice");
			String description = myRs.getString("description");
			String manufacturer = myRs.getString("manufacturer");
			String category = myRs.getString("category");
			long unitsInStock = myRs.getLong("unitsInStock");
			
			
			Product p = new Product(productId, name, price);
			p.setDescription(description);
			p.setCategory(category);
			p.setManufacturer(manufacturer);
			p.setUnitsInStock(unitsInStock);
			
			
			listOfProducts.add(p);	
		}
		
		
	}
	catch(Exception e){
		e.printStackTrace();
	}

/*listOfProducts.add(laptop_dell);
listOfProducts.add(tablet_Nexus);
listOfProducts.add(lg_leon);*/
}


public List<Product> getAllProducts() {
	
return listOfProducts;
}

public Product getProductById(String productId) {

	Product productById = null;
	
	for(Product product : listOfProducts) {
		
	if(product!=null && product.getProductId()!=null && product.getProductId().equals(productId)){
	productById = product;
	break;
	
	}
	}
	if(productById == null){
		
	throw new ProductNotFoundException(productId);
	
	}
	return productById;
}

public List<Product> getProductsByCategory(String category) {

	List<Product> productsByCategory = new ArrayList<Product>();
	
	for(Product p : listOfProducts)
	{
		if(p.getCategory().equalsIgnoreCase(category))
		{
			productsByCategory.add(p);
		}
	}
	
	return productsByCategory;
}

public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
	
	Set<Product> productsByBrand = new HashSet<Product>();
	Set<Product> productsByCategory = new HashSet<Product>();
	Set<String> criterias = filterParams.keySet();
	
	if(criterias.contains("brand"))
	{
		for(String brandName : filterParams.get("brand"))
		{
			for(Product p : listOfProducts)
			{
				if(brandName.equalsIgnoreCase(p.getManufacturer()))
					productsByBrand.add(p);
					
			}
		}
	}
	if(criterias.contains("category"))
	{
		for(String category : filterParams.get("category"))
		{
			productsByCategory.addAll(this.getProductsByCategory(category));
		}
	}
	
	productsByCategory.retainAll(productsByBrand);
	return productsByCategory;
		
}

public Set<Product> getProductsByPriceFilter(Map<String, List<String>> filterParams) {
	
	Set<Product> productsByLowPrice = new HashSet<Product>();
	Set<Product> productsByHighPrice = new HashSet<Product>();
	Set<Product> productsByLowAndHighPrice = new HashSet<Product>();
	Set<String> criterias = filterParams.keySet();
	
			
			if(criterias.contains("low"))
			{
				for(String price : filterParams.get("low"))
				{
					for(Product p : listOfProducts)
					{
						if(Integer.valueOf(price)<=p.getUnitPrice().intValue())
							productsByLowPrice.add(p);
							
					}
				}
			}
		
			if(criterias.contains("high"))
			{
				for(String price : filterParams.get("high"))
				{
					for(Product p : listOfProducts)
					{
						if(Integer.valueOf(price)>=p.getUnitPrice().intValue())
							productsByHighPrice.add(p);
							
					}
				}
			}
	
	productsByLowAndHighPrice.addAll(productsByLowPrice);
	productsByLowAndHighPrice.retainAll(productsByHighPrice);
	
	return productsByLowAndHighPrice;
		
}

public List<Product> getProductsByManufacturer(String manufacturer) {

	List<Product> productsByCategory = new ArrayList<Product>();
	
	for(Product p : listOfProducts)
	{
		if(p.getManufacturer().equalsIgnoreCase(manufacturer))
		{
			productsByCategory.add(p);
		}
	}
	
	return productsByCategory;
}

public void addProduct(Product p) {

listOfProducts.add(p);

String insertTableSQL = "INSERT INTO products "
		+ "(productId, name, unitPrice, description, manufacturer, category, unitsInStock, discounted, cond) VALUES"
		+ "(?,?,?,?,?,?,?,?,?)";

try {
	PreparedStatement ps = myConn.prepareStatement(insertTableSQL);
	
	ps.setString(1, p.getProductId());
	ps.setString(2, p.getName());
	ps.setBigDecimal(3, p.getUnitPrice());
	ps.setString(4, p.getDescription());
	ps.setString(5, p.getManufacturer());
	ps.setString(6, p.getCategory());
	ps.setLong(7, p.getUnitsInStock());
	ps.setBoolean(8, p.isDiscontinued());
	ps.setString(9, p.getCondition());
	
	ps.executeUpdate();
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
}



}