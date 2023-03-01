package com.dhairya.org.Ewallet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EwalletController {

	@RequestMapping("/test")
	public String test() {
		return "API succesfull";
	}
}
