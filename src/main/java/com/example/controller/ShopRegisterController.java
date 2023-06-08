package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RegisterShopForm;
import com.example.form.RegisterStaffInfoForm;
import com.example.service.ShopRegisterService;

@Controller
@RequestMapping("/register")
public class ShopRegisterController {

	@Autowired
	private ShopRegisterService shopRegisterService;
	@Autowired
	private RegisterStaffInfoController regiterStaffInfoController;
	
	@GetMapping("/shop")
	public String index(RegisterShopForm form) {
		return "register-shop";
	}
	
	@PostMapping("/insert-shop-info")
	public String registerShop(@Validated RegisterShopForm form, BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			return index(form);
		}
		shopRegisterService.insertShopInfo(form);
		return "redirect:/register/to-register-staff";
	}
	
	@GetMapping("/to-register-staff")
	public String toRegisterStaff(RegisterStaffInfoForm form, Model model) {
		return regiterStaffInfoController.index(form, model);
	}
}
