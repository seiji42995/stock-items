package com.example.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.domain.LoginStaff;
import com.example.service.StaffDetailsServiceImpl;

@Transactional
@SpringBootTest
class AddItemControllerTest {

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
	@DisplayName("商品追加画面テスト")
	void testAddIndex() throws Exception {
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		this.mockMvc.perform(get("/add").with(user(loginStaff)))
		.andExpect(status().isOk())
		.andExpect(authenticated())
		.andExpect(view().name("add"));
	}
	
	@Test
	@DisplayName("商品追加テスト（正常系）")
	void testSuccessInsertItem() throws Exception{
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(68);
		categoryList.add(100);
		categoryList.add(108);
		String categoryLists = categoryList.stream().map(Object::toString).collect(Collectors.joining(","));
		this.mockMvc.perform(post("/add/insert")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(user(loginStaff))
				.param("name", "test")
				.param("price", "100.0")
				.param("categoryList", categoryLists)
				.param("brandName", "ARTNIA")
				.param("condition", "3")
				.param("description", "商品追加（テスト）"))
		.andExpect(status().is3xxRedirection())
		.andExpect(authenticated())
		.andExpect(view().name("redirect:/add/to-showList"));
	}
	
	@Test
	@DisplayName("商品追加テスト（異常系）")
	void testInsertItem() throws Exception{
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(68);
		categoryList.add(100);
		categoryList.add(108);
		String categoryLists = categoryList.stream().map(Object::toString).collect(Collectors.joining(","));
		this.mockMvc.perform(post("/add/insert")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(user(loginStaff))
				.param("price", "100.0")
				.param("categoryList", categoryLists)
				.param("brandName", "ARTNIA")
				.param("condition", "3")
				.param("description", "商品追加（テスト）"))
		.andExpect(model().hasErrors())
		.andExpect(authenticated())
		.andExpect(view().name("add"));
	}
	
}
