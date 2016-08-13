package com.packt.webstore.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.packt.webstore.domain.Product;

public class ProductImageValidator implements Validator {
	
	private long allowedSize;
	

	@Override
	public boolean supports(Class<?> clazz) {

	return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Product product = (Product) obj;
		
		MultipartFile img = product.getProductImage();
		
		if(img.getSize()>allowedSize)
		errors.rejectValue("productImage", "com.packt.webstore.validator.ProductImageValidator.message");
		
	}

	public long getAllowedSize() {
		return allowedSize;
	}

	public void setAllowedSize(long allowedSize) {
		this.allowedSize = allowedSize;
	}
	
	

}
