package com.packt.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.packt.webstore.service.InfoService;

@Controller
@RequestMapping("/info")
public class ShoppingInfoController {
	
	@Autowired
	private InfoService info;
	
	@RequestMapping("/orders")
	public String getOrdersInfo(Model model){
		
		model.addAttribute("orders", info.getAllOrdersInfo());
		return "orders";
	}

}
