package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	@GetMapping("/")
	public String top(Model model) {
		model.addAttribute("message", "Hello, Thymeleaf");
		return ("top");
	}

}
