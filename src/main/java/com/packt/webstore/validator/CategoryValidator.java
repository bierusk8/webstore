package com.packt.webstore.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class CategoryValidator implements ConstraintValidator<Category,String> {

	List<String> allowedCategories=new ArrayList<String>();
	
	public CategoryValidator()
	{
		allowedCategories.add("Smartfon");
		allowedCategories.add("Laptop");
		allowedCategories.add("Tablet");
		allowedCategories.add("TV");
	}
	
	@Override
	public void initialize(Category arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if(value==null)
			return true;
		
		if(value.equals(""))
			return true;
		
		if(allowedCategories.contains(value))
			return true;
		
		return false;		
	}

}
