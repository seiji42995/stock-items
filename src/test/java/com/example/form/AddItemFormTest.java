package com.example.form;

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
		
	}
	
}
