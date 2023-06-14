package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.domain.Item;
import com.example.domain.LoginStaff;
import com.example.repository.BrandRepository;
import com.example.service.CategoryService;
import com.example.service.ItemService;
import com.example.service.StaffDetailsServiceImpl;

@SpringBootTest
class ItemListControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CategoryService categoryService;
	@Mock
	private BrandRepository brandRepository;
	@Mock
	private ItemService itemService;
	@InjectMocks
	private ItemListController itemListController;

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
	@Disabled
	@DisplayName("商品表示テスト（初期表示）")
	void testShowItemList() throws Exception {
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		this.mockMvc.perform(get("/item/show-list").with(user(loginStaff)))
				.andExpect(status().isOk())
				.andExpect(authenticated())
				.andExpect(view().name("list"));
	}

	@Test
	@Disabled
	@DisplayName("商品表示テスト（次ページ表示）")
	void testNextPage() throws Exception {
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		this.mockMvc.perform(get("/item/next-page")
				.param("page", "0")
				.with(user(loginStaff)))
				.andExpect(status().isOk())
				.andExpect(authenticated())
				.andExpect(view().name("list"));
	}

	@Test
	@Disabled
	@DisplayName("商品表示テスト（前ページ表示）")
	void testPreviousPage() throws Exception {
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		this.mockMvc.perform(get("/item/previous-page")
				.param("page", "0")
				.with(user(loginStaff)))
				.andExpect(status().isOk())
				.andExpect(authenticated())
				.andExpect(view().name("list"));
	}

	@Test
	@Disabled
	@DisplayName("商品表示テスト（ページング表示）")
	void testGotoPage() throws Exception {
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		MvcResult result = this.mockMvc.perform(get("/item/goto-page")
				.param("page", "100")
				.with(user(loginStaff)))
				.andExpect(status().isOk())
				.andExpect(authenticated())
				.andExpect(view().name("list"))
				.andReturn();
		List<Item> itemList = (List<Item>)result.getModelAndView().getModel().get("itemList");
		assertEquals(itemList.get(0).getItemId(), 9901, "正常な値が取得できていません");
		assertEquals(itemList.get(0).getName(), "Sequin dress", "正常な値が取得できていません");
		assertEquals(itemList.get(0).getPrice(), 12.0, "正常な値が取得できていません");
	}

	@Test
	@Disabled
	@DisplayName("商品表示テスト（検索表示）")
	void testSearch() throws Exception {
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(68);
		categoryList.add(100);
		categoryList.add(108);
		
		String categoryLists = categoryList.stream().map(Object::toString).collect(Collectors.joining(","));
		this.mockMvc.perform(post("/item/search")
				.param("itemName", "mlb")
				.param("categoryList", categoryLists)
				.param("brandName", "nintendo")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(user(loginStaff)))
				.andExpect(status().isOk())
				.andExpect(authenticated())
				.andExpect(view().name("list"));
	}
	
	@Test
	@Disabled
	@DisplayName("showListメソッド遷移テスト")
	void testToShowlist() throws Exception{
		LoginStaff loginStaff = (LoginStaff) staffDetailsServiceImpl.loadUserByUsername("jun.sato@test.com");
		this.mockMvc.perform(get("/item/to-showlist")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(user(loginStaff)))
				.andExpect(status().isOk())
				.andExpect(authenticated())
				.andExpect(view().name("list"));
	}

}
