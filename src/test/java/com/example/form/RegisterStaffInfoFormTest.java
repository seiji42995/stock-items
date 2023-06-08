package com.example.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

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
class RegisterStaffInfoFormTest {

	@Autowired
	private Validator validator;

	private RegisterStaffInfoForm form = new RegisterStaffInfoForm();
	private BindingResult result = new BindException(form, "form");

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		form.setFirstName("一郎");
		form.setLastName("鈴木");
		form.setEmail("regtest@test.com");
		form.setPassword("Suzuki1234");
		form.setConfirmationPassword("Suzuki1234");
		form.setAuthority(0);
		form.setShopName("鈴木商店");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト（正常系）")
	void testSuccessPattern() {
		validator.validate(form, result);
		assertNull(result.getFieldError());
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：性なし）")
	void testLastNameNull() {
		form.setLastName("");
		validator.validate(form, result);
		assertEquals("必須入力項目です", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：名なし）")
	void testFirstNameNull() {
		form.setFirstName("");
		validator.validate(form, result);
		assertEquals("必須入力項目です", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：メールアドレスなし）")
	void testEmailNull() {
		form.setEmail("");
		validator.validate(form, result);
		assertEquals("必須入力項目です", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：パスワードなし）")
	void testPasswordNull() {
		form.setPassword("");
		validator.validate(form, result);
		List<String> errors = new ArrayList<>();
		for (ObjectError error : result.getAllErrors()) {
			FieldError fieldError = (FieldError) error;
			errors.add(fieldError.getDefaultMessage());
		}
		for (String message : errors) {
			if ("必須入力項目です".equals(message)) {
				assertEquals("必須入力項目です", message, "バリデーションが機能していません");
			} else if ("8字以上、16文字以内で設定してください".equals(message)) {
				assertEquals("8字以上、16文字以内で設定してください", message, "バリデーションが機能していません");
			} else {
				assertEquals("数字、半角英字（小文字・大文字）をそれぞれ1文字以上ずつ含めたパスワードにしてください", message, "バリデーションが機能していません");
			}
		}
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：8字未満）")
	void testPassword7chars() {
		form.setPassword("Suzu123");
		validator.validate(form, result);
		assertEquals("8字以上、16文字以内で設定してください", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：英字（大・小）、数字を1字ずつ含まない）")
	void testPasswordWithBadPattern() {
		form.setPassword("suzuki1234");
		validator.validate(form, result);
		assertEquals("数字、半角英字（小文字・大文字）をそれぞれ1文字以上ずつ含めたパスワードにしてください", result.getFieldError().getDefaultMessage(),
				"バリデーションが機能していません");
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：確認パスワードなし）")
	void testConfirmationPasswordNull() {
		form.setConfirmationPassword("");
		validator.validate(form, result);
		assertEquals("確認用パスワードも入力してください", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：編集権限選択なし）")
	void testAuthorityNotChoice() {
		form.setAuthority(null);
		validator.validate(form, result);
		assertEquals("権限設定を選択してください", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("スタッフ登録フォームテスト(異常系：店舗情報なし")
	void testShopNameIsNull() {
		form.setShopName("");
		validator.validate(form, result);
		assertEquals("店舗を選択してください", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}
}
