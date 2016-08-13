package com.packt.webstore.controller;


import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.ProductValidator;
import com.packt.webstore.validator.UnitsInStockValidator;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductValidator productValidator;	
	
	@RequestMapping
	public String list(Model model) {
		
	model.addAttribute("products", productService.getAllProducts());
	
	return "products";
	}
	
	@RequestMapping("/all")
	public String allProducts(Model model) {
	model.addAttribute("products", productService.getAllProducts());
	return "products";
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) 
	{
		List<Product> products = productService.getProductsByCategory(productCategory);
		
		if (products == null || products.isEmpty()) 
			throw new NoProductsFoundUnderCategoryException();		
				
	model.addAttribute("products", productService.getProductsByCategory(productCategory));
	return "products";
	}
	
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(@MatrixVariable(pathVar="ByCriteria") Map<String,List<String>> filterParams, Model model)
	{
		model.addAttribute("products", productService.getProductsByFilter(filterParams));		
		return "products";
	}
	
	@RequestMapping("/product/{id}")
	public String getProductById(@PathVariable("id") String id, Model model)
	{
		model.addAttribute("product", productService.getProductById(id));		
		return "product";
	}
	
	@RequestMapping("/{category}/{price}")
	public String getProductsByFilter(@PathVariable("category") String productCategory, @MatrixVariable(pathVar="price") Map<String,List<String>> filterParams,@RequestParam("manufacturer") String manufacturer , Model model) 
	{
		Set<Product> finalResults = new HashSet<Product>();
		finalResults.addAll(productService.getProductsByCategory(productCategory));
		finalResults.retainAll(productService.getProductsByPriceFilter(filterParams));
		finalResults.retainAll(productService.getProductsByManufacturer(manufacturer));
		
		model.addAttribute("products", finalResults);
		
		return "products";
	}
	
	@RequestMapping("/price/{ByPrice}")
	public String getProductsByPriceFilter(@MatrixVariable(pathVar="ByPrice") Map<String,List<String>> filterParams, Model model)
	{
		model.addAttribute("products", productService.getProductsByPriceFilter(filterParams));
		
		return "products";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
	Product newProduct = new Product();
	model.addAttribute("newProduct", newProduct);
	return "addProduct";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct, BindingResult result, HttpServletRequest request) {
	
	if(result.hasErrors())
	{
		return "addProduct";
	}	
			
	String[] suppressedFields = result.getSuppressedFields();
	
	if (suppressedFields.length > 0) 
	throw new RuntimeException("Próba wiązania niedozwolonych pól" + StringUtils.arrayToCommaDelimitedString(suppressedFields));
	
	
	MultipartFile productImage = newProduct.getProductImage();
	String rootDirectory = request.getSession().getServletContext().getRealPath("/");
	if (productImage!=null && !productImage.isEmpty()) {
	try {
	productImage.transferTo(new File(rootDirectory+"/resources/images/"+ newProduct.getProductId() + ".png"));
	} catch (Exception e) {
	throw new RuntimeException("Niepowodzenie podczas próby zapisu obrazka produktu", e);
	}
	}
	
	
	MultipartFile productPdf = newProduct.getProductPdf();
	if (productPdf!=null && !productPdf.isEmpty()) {
	try {
	productPdf.transferTo(new File(rootDirectory+"/resources/pdf/"+ newProduct.getProductId() + ".pdf"));
	} catch (Exception e) {
	throw new RuntimeException("Niepowodzenie podczas próby zapisu instrukcji produktu", e);
	}
	}
		
	productService.addProduct(newProduct);
	return "redirect:/products";
	}
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		
	binder.setDisallowedFields("unitsInOrder", "discontinued");
	binder.setAllowedFields("productId", "name", "unitPrice", "description", "manufacturer", "category", "unitsInStock", "productImage" ,"productPdf", "condition","language");
	
	binder.setValidator(productValidator);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req,ProductNotFoundException exception) {
		
	ModelAndView mav = new ModelAndView();
	
	mav.addObject("invalidProductId", exception.getProductId());
	mav.addObject("exception", exception);
	mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
	mav.setViewName("productNotFound");
	
	return mav;
	}
	
	@RequestMapping("/invalidPromoCode")
	public String invalidPromoCode() 
	{
	return "invalidPromoCode";
	}
	
}
