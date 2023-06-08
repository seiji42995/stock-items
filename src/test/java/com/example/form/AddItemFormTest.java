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
class AddItemFormTest {

	@Autowired
	Validator validator;
	
	private AddItemForm addItemForm = new AddItemForm();
	private BindingResult result = new BindException(addItemForm, "addItemForm");
	
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
		addItemForm.setName("テスト追加商品");
		addItemForm.setPrice(100.0);
		addItemForm.setCategoryList(categoryList);
		addItemForm.setBrandName("Nintendo");
		addItemForm.setCondition(3);
		addItemForm.setDescription("商品追加テスト用説明概要");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("商品追加フォーム（正常系）")
	void testSuccessForm() throws Exception{
		validator.validate(addItemForm, result);
		assertNull(result.getFieldError());
	}
	
	@Test
	@DisplayName("商品追加フォーム（異常系：全て空欄）")
	void testAllNull() throws Exception{
		addItemForm.setName("");
		addItemForm.setPrice(null);
		addItemForm.setCategoryList(null);
		addItemForm.setBrandName("");
		addItemForm.setCondition(null);
		addItemForm.setDescription("");
		validator.validate(addItemForm, result);
		assertTrue(result.hasErrors());
		Map<String, String> errors = new HashMap<>();
		for(ObjectError error : result.getAllErrors()) {
			FieldError fieldError = (FieldError) error;
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		assertEquals("商品名を入力してください", errors.get("name"), "バリデーションが正しく機能していません");
		assertEquals("価格を入力してください", errors.get("price"), "バリデーションが正しく機能していません");
		assertEquals("ブランドを選択してください", errors.get("brandName"), "バリデーションが正しく機能していません");
		assertEquals("コンディションを選択してください", errors.get("condition"), "バリデーションが正しく機能していません");
		assertEquals("商品概要を入力してください", errors.get("description"), "バリデーションが正しく機能していません");
	}
	
	@Test
	@DisplayName("商品追加フォーム（異常系：名前のみ空欄）")
	void testItemNameNull() throws Exception{
		addItemForm.setName("");
		validator.validate(addItemForm, result);
		assertEquals("商品名を入力してください", result.getFieldError().getDefaultMessage(), "バリデーションが正しく機能していません");
	}
	
	@Test
	@DisplayName("商品追加フォーム（異常系：ブランドのみ空欄）")
	void testBrandNameNull() throws Exception{
		addItemForm.setBrandName("");
		validator.validate(addItemForm, result);
		assertEquals("ブランドを選択してください", result.getFieldError().getDefaultMessage(), "バリデーションが正しく機能していません");
	}
	
	@Test
	@DisplayName("商品追加フォーム（異常系：コンディション未選択）")
	void testConditionNull() throws Exception{
		addItemForm.setCondition(null);
		validator.validate(addItemForm, result);
		assertEquals("コンディションを選択してください", result.getFieldError().getDefaultMessage(), "バリデーションが正しく機能していません");
	}
	
	@Test
	@DisplayName("商品追加フォーム（異常系：商品概要未入力）")
	void testDescriptionNull() throws Exception{
		addItemForm.setDescription("");
		validator.validate(addItemForm, result);
		assertEquals("商品概要を入力してください", result.getFieldError().getDefaultMessage(), "バリデーションが正しく機能していません");
	}
	
}
