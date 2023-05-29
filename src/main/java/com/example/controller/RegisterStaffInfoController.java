package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Shop;
import com.example.form.RegisterStaffInfoForm;
import com.example.repository.ShopRepository;
import com.example.service.RegisterStaffInfoService;

/**
 * アカウント登録を操作するControllerクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterStaffInfoController {

	@Autowired
	private ShopRepository shopRepository;
	@Autowired
	private RegisterStaffInfoService registerStaffInfoService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("")
	public String index(RegisterStaffInfoForm form, Model model) {
		List<Shop> shopList = shopRepository.findAll();
		// 編集権限のラジオボタンにチェックする為、値をセット
		form.setAuthority(0);
		model.addAttribute("shopList", shopList);
		return "register";
	}

	@PostMapping("/insert-staff-info")
	public String registerStaffInfo(@Validated RegisterStaffInfoForm form, BindingResult result, Model model) {
		System.out.println(form);
		if(!(form.getPassword().isEmpty()) && !(form.getConfirmationPassword().isEmpty())) {
			result = confirmValidate(form, result);			
		}

		if (result.hasErrors()) {
			return index(form, model);
		}
		// パスワードハッシュ化
		form.setPassword(passwordEncoder.encode(form.getPassword()));

		registerStaffInfoService.insertStaffInfo(form);
		return "redirect:/register/to-login";
	}

	@GetMapping("/to-login")
	public String tologin(Model model) {
		model.addAttribute("message", "登録が完了しました");
		return "login";
	}

	public BindingResult confirmValidate(RegisterStaffInfoForm form, BindingResult result) {

		// 確認用パスワードと一致しているかを確認
		if (form.getPassword().equals(form.getConfirmationPassword())) {
		} else {
			String notMatch = "確認用パスワードが一致していません。もう一度入力してください。";
			FieldError fieldError = new FieldError(result.getObjectName(), "confirmationPassword", notMatch);
			result.addError(fieldError);

		}

		return result;
	}

}
