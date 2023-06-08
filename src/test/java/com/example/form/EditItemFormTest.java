package com.example.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class EditItemFormTest {

	@Autowired
	private Validator validator;

	private EditItemForm form = new EditItemForm();
	private BindingResult result = new BindException(form, "form");

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(68);
		categoryList.add(100);
		categoryList.add(108);
		form.setName("テスト商品編集");
		form.setPrice(20.0);
		form.setCategoryList(categoryList);
		form.setBrandName("Nintendo");
		form.setCondition(1);
		form.setDescription("テスト修正");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("商品編集フォーム（正常系）")
	void testSuccessForm() {
		validator.validate(form, result);
		assertNull(result.getFieldError());
	}

	@Test
	@DisplayName("商品編集フォーム（異常系：全て空欄）")
	void testFormAllNull() {
		form.setName("");
		form.setPrice(null);
		form.setCategoryList(null);
		form.setBrandName("");
		form.setCondition(null);
		form.setDescription("");
		validator.validate(form, result);
		assertTrue(result.hasErrors());
		Map<String, String> errors = new HashMap<>();
		for (ObjectError error : result.getAllErrors()) {
			FieldError fieldError = (FieldError) error;
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		assertEquals("商品名を入力してください", errors.get("name"), "バリデーションが機能していません");
		assertEquals("価格を入力してください", errors.get("price"), "バリデーションが機能していません");
		assertEquals("コンディションを選択してください", errors.get("condition"), "バリデーションが機能していません");
		assertEquals("商品概要を入力してください", errors.get("description"), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("商品編集フォーム（異常系：商品名なし）")
	void testItemNameNull() {
		form.setName("");
		validator.validate(form, result);
		assertTrue(result.hasErrors());
		assertEquals("商品名を入力してください", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("商品編集フォーム（異常系：価格なし）")
	void testItemPriceNull() {
		form.setPrice(null);
		validator.validate(form, result);
		assertTrue(result.hasErrors());
		assertEquals("価格を入力してください", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("商品編集フォーム（異常系：コンディションなし）")
	void testConditionNull() {
		form.setCondition(null);
		validator.validate(form, result);
		assertTrue(result.hasErrors());
		assertEquals("コンディションを選択してください", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

	@Test
	@DisplayName("商品編集フォーム（異常系：商品概要なし）")
	void testDescriptionNull() {
		form.setDescription("");
		validator.validate(form, result);
		assertTrue(result.hasErrors());
		assertEquals("商品概要を入力してください", result.getFieldError().getDefaultMessage(), "バリデーションが機能していません");
	}

}
