package com.example.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.domain.LoginStaff;
import com.example.service.StaffDetailsServiceImpl;

@Transactional
@Rollback
@SpringBootTest
class EditItemControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private StaffDetailsServiceImpl staffDetailsServiceImpl; 
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("商品修正画面表示テスト")
	void testEdit() throws Exception {
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		this.mockMvc.perform(get("/item/edit")
				.param("itemId", "1")
				.param("page", "0")
				.with(user(loginStaff)))
		.andExpect(status().isOk())
		.andExpect(authenticated())
		.andExpect(view().name("edit"));
	}
	
	@Test
	@DisplayName("商品修正テスト（正常系）")
	void testEditItem() throws Exception{
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(68);
		categoryList.add(100);
		categoryList.add(108);
		String categoryLists = categoryList.stream().map(Object::toString).collect(Collectors.joining(","));
		this.mockMvc.perform(post("/item/edit-item")
				.param("name", "MLB Cincinnati Reds T Shirt Size XL")
				.param("price", "12.0")
				.param("categoryList", categoryLists)
				.param("brandName", "Nintendo")
				.param("condition", "3")
				.param("description", "テスト編集")
				.param("itemId", "1")
				.param("page", "0")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(user(loginStaff)))
		.andExpect(status().is3xxRedirection())
		.andExpect(authenticated())
		.andExpect(view().name("redirect:/item/to-show_detail"));
	}
	@Test
	@DisplayName("商品修正テスト（異常系：商品名空欄）")
	void testFailureEditItemByNoName() throws Exception{
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(68);
		categoryList.add(100);
		categoryList.add(108);
		String categoryLists = categoryList.stream().map(Object::toString).collect(Collectors.joining(","));
		this.mockMvc.perform(post("/item/edit-item")
				.param("price", "10.0")
				.param("categoryList", categoryLists)
				.param("brandName", "Nintendo")
				.param("condition", "3")
				.param("description", "テスト編集")
				.param("itemId", "1")
				.param("page", "0")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(user(loginStaff)))
		.andExpect(status().isOk())
		.andExpect(authenticated())
		.andExpect(view().name("edit"));		
	}
	@Test
	@DisplayName("商品修正テスト（異常系：価格空欄）")
	void testFailureEditItemByNoPrice() throws Exception{
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(68);
		categoryList.add(100);
		categoryList.add(108);
		String categoryLists = categoryList.stream().map(Object::toString).collect(Collectors.joining(","));
		this.mockMvc.perform(post("/item/edit-item")
				.param("name", "MLB Cincinnati Reds T Shirt Size XL")
				.param("categoryList", categoryLists)
				.param("brandName", "Nintendo")
				.param("condition", "3")
				.param("description", "テスト編集")
				.param("itemId", "1")
				.param("page", "0")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(user(loginStaff)))
		.andExpect(status().isOk())
		.andExpect(authenticated())
		.andExpect(view().name("edit"));		
	}
	@Test
	@DisplayName("商品修正テスト（異常系：商品概要空欄）")
	void testFailureEditItemByNoDescription() throws Exception{
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(68);
		categoryList.add(100);
		categoryList.add(108);
		String categoryLists = categoryList.stream().map(Object::toString).collect(Collectors.joining(","));
		this.mockMvc.perform(post("/item/edit-item")
				.param("name", "MLB Cincinnati Reds T Shirt Size XL")
				.param("price", "10.0")
				.param("categoryList", categoryLists)
				.param("brandName", "Nintendo")
				.param("condition", "3")
				.param("itemId", "1")
				.param("page", "0")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(user(loginStaff)))
		.andExpect(status().isOk())
		.andExpect(authenticated())
		.andExpect(view().name("edit"));		
	}
	

}
