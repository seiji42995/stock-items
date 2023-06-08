package com.example.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

@SpringBootTest
class RegisterShopFormTest {

	@Autowired
	private Validator validator;
	
	private RegisterShopForm form = new RegisterShopForm();
	private BindingResult result = new BindException(form, "form");
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		form.setName("テスト店");
		form.setZipcode("160-0022");
		form.setAddress("東京都新宿区");
		form.setTelephone("080-1234-5678");
		form.setOrnerLastName("鈴木");
		form.setOrnerFirstName("一郎");
		form.setDescription("テスト店舗登録");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("店舗登録テスト（正常系）")
	void testSuccessForm() {
		validator.validate(form, result);
		assertNull(result.getFieldError());
	}
	
	@Test
	@DisplayName("店舗登録テスト（異常系：全て空欄）")
	void testFormAllNull() {
		form.setName("");
		form.setZipcode("");
		form.setAddress("");
		form.setTelephone("");
		form.setOrnerLastName("");;
		form.setOrnerFirstName("");
		form.setDescription("");
		validator.validate(form, result);
		Map<String, String> errors = new HashMap<>();
		for(ObjectError error : result.getAllErrors()) {
			FieldError fieldError = (FieldError) error;
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		assertEquals("必須入力項目です", errors.get("name"), "バリデーションが機能していません");
		if(errors.get("zipcode").equals("必須入力項目です")) {
			assertEquals("必須入力項目です", errors.get("zipcode"), "バリデーションが機能していません");
		}else {
			assertEquals("郵便番号はXXX-XXXXの形式で入力してください", errors.get("zipcode"), "バリデーションが機能していません");			
		}
		assertEquals("必須入力項目です", errors.get("address"), "バリデーションが機能していません");
		if(errors.get("telephone").equals("必須入力項目です")) {
			assertEquals("必須入力項目です", errors.get("telephone"), "バリデーションが機能していません");
		}else {
			assertEquals("電話番号はXXX-XXXX-XXXXの形式で入力してください", errors.get("telephone"), "バリデーションが機能していません");			
		}
		assertEquals("必須入力項目です", errors.get("ornerLastName"), "バリデーションが機能していません");
		assertEquals("必須入力項目です", errors.get("ornerFirstName"), "バリデーションが機能していません");
		assertEquals("必須入力項目です", errors.get("description"), "バリデーションが機能していません");
		
	}

	@Test
	@DisplayName("店舗登録テスト（異常系：店舗名のみ空欄")
	void testShopNameIsNull() {
		form.setName("");
		validator.validate(form, result);
		assertEquals("必須入力項目です", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}
	
	@Test
	@DisplayName("店舗登録テスト（異常系：郵便番号なし）")
	void testZipcodeIsNull() {
		form.setZipcode("");
		validator.validate(form, result);
		List<String> errors = new ArrayList<>();
		for(ObjectError error : result.getAllErrors()) {
			FieldError fieldError = (FieldError) error;
			errors.add(fieldError.getDefaultMessage());
		}
		for(String message : errors) {
			if("必須入力項目です".equals(message)) {
				assertEquals("必須入力項目です", message, "バリデーションが機能していません");
			}else {
				assertEquals("郵便番号はXXX-XXXXの形式で入力してください", message, "バリデーションが機能していません");
			}
		}
	}
	
	@Test
	@DisplayName("店舗登録テスト（異常系：住所なし）")
	void testAddressIsNull() {
		form.setAddress("");
		validator.validate(form, result);
		assertEquals("必須入力項目です", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}
	
	@Test
	@DisplayName("店舗登録テスト（異常系：電話番号なし）")
	void testTeleohoneIsNull() {
		form.setTelephone("");
		validator.validate(form, result);
		List<String> errors = new ArrayList<>();
		for(ObjectError error : result.getAllErrors()) {
			FieldError fieldError = (FieldError) error;
			errors.add(fieldError.getDefaultMessage());
		}
		for(String message : errors) {
			if("必須入力項目です".equals(message)) {
				assertEquals("必須入力項目です", message, "バリデーションが機能していません");
			}else {
				assertEquals("電話番号はXXX-XXXX-XXXXの形式で入力してください", message, "バリデーションが機能していません");
			}
		}
	}
	
	@Test
	@DisplayName("店舗登録テスト（異常系：オーナー性なし）")
	void testOrnerLastNameIsNull() {
		form.setOrnerLastName("");
		validator.validate(form, result);
		assertEquals("必須入力項目です", result.getFieldError().getDefaultMessage(), "バリデーションが機能しています");
	}
	
	@Test
	@DisplayName("店舗登録テスト（異常系：オーナー名なし）")
	void testOrnerFirstNameIsNull() {
		form.setOrnerFirstName("");
		validator.validate(form, result);
		assertEquals("必須入力項目です", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}
	
	@Test
	@DisplayName("店舗登録テスト（異常系：店舗概要なし）")
	void testDescriptionIsNull() {
		form.setDescription("");
		validator.validate(form, result);
		assertEquals("必須入力項目です", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}
}
