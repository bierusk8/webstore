package com.packt.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
@RequestMapping("/")
public String welcome(Model model) {
model.addAttribute("greeting", "Witaj w sklepie internetowym!");
model.addAttribute("tagline", "Wyjatkowym i jedynym sklepie internetowym");
return "welcome";
}

@RequestMapping("/welcome/greeting")
public String greeting() {
return "welcome";
}

@RequestMapping("/welcome")
public String welc(Model model) {
	
model.addAttribute("greeting", "Gratulujemy!");
model.addAttribute("tagline", "Wprowadzono prawidlowy bon promocyjny - skorzystaj z naszej oferty");
	
return "welcome";
}
}