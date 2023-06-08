package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.form.RegisterShopForm;

@SpringBootTest
@Transactional
@Rollback
class ShopRegisterControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		RegisterShopForm form = new RegisterShopForm();
		form.setName("テスト店舗");
		form.setZipcode("160-0022");
		form.setAddress("東京都新宿区");
		form.setTelephone("080-1234-5678");
		form.setOrnerLastName("田中");
		form.setOrnerFirstName("次郎");
		form.setDescription("テスト概要です");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("画面遷移テスト")
	void testIndex() throws Exception{
		mockMvc.perform(get("/register/shop"))
		.andExpect(status().isOk())
		.andExpect(view().name("register-shop"));
	}
	
	@Test
	@DisplayName("店舗情報登録テスト（正常系）")
	void testRegisterShop() throws Exception{
		mockMvc.perform(post("/register/insert-shop-info")
				.param("name", "テスト店舗")
				.param("zipcode", "160-0022")
				.param("address", "東京都新宿区")
				.param("telephone", "080-1234-5678")
				.param("ornerLastName", "田中")
				.param("ornerFirstName", "次郎")
				.param("description", "テスト概要です"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/register/to-register-staff"));
	}
	
	@Test
	@DisplayName("店舗情報登録テスト（異常系：店舗名なし）")
	void testRegisterShopWithoutShopName() throws Exception{
		mockMvc.perform(post("/register/insert-shop-info")
				.param("zipcode", "160-0022")
				.param("address", "東京都新宿区")
				.param("telephone", "080-1234-5678")
				.param("ornerLastName", "田中")
				.param("ornerFirstName", "次郎")
				.param("description", "テスト概要です"))
		.andExpect(status().isOk())
		.andExpect(view().name("register-shop"));
	}

	@Test
	@DisplayName("店舗情報登録テスト（異常系：郵便番号なし）")
	void testRegisterShopWithoutZipcode() throws Exception{
		mockMvc.perform(post("/register/insert-shop-info")
				.param("name", "テスト店舗")
				.param("address", "東京都新宿区")
				.param("telephone", "080-1234-5678")
				.param("ornerLastName", "田中")
				.param("ornerFirstName", "次郎")
				.param("description", "テスト概要です"))
		.andExpect(status().isOk())
		.andExpect(view().name("register-shop"));
	}
	
	@Test
	@DisplayName("店舗情報登録テスト（異常系：住所なし）")
	void testRegisterShopWithoutAddress() throws Exception{
		mockMvc.perform(post("/register/insert-shop-info")
				.param("name", "テスト店舗")
				.param("zipcode", "160-0022")
				.param("telephone", "080-1234-5678")
				.param("ornerLastName", "田中")
				.param("ornerFirstName", "次郎")
				.param("description", "テスト概要です"))
		.andExpect(status().isOk())
		.andExpect(view().name("register-shop"));
	}
	
	@Test
	@DisplayName("店舗情報登録テスト（異常系：電話番号なし）")
	void testRegisterShopWithoutTelephone() throws Exception{
		mockMvc.perform(post("/register/insert-shop-info")
				.param("name", "テスト店舗")
				.param("zipcode", "160-0022")
				.param("address", "東京都新宿区")
				.param("ornerLastName", "田中")
				.param("ornerFirstName", "次郎")
				.param("description", "テスト概要です"))
		.andExpect(status().isOk())
		.andExpect(view().name("register-shop"));
	}
	
	@Test
	@DisplayName("店舗情報登録テスト（異常系：オーナー姓なし）")
	void testRegisterShopWithoutOrnerLastName() throws Exception{
		mockMvc.perform(post("/register/insert-shop-info")
				.param("name", "テスト店舗")
				.param("zipcode", "160-0022")
				.param("address", "東京都新宿区")
				.param("telephone", "080-1234-5678")
				.param("ornerFirstName", "次郎")
				.param("description", "テスト概要です"))
		.andExpect(status().isOk())
		.andExpect(view().name("register-shop"));
	}
	
	@Test
	@DisplayName("店舗情報登録テスト（異常系：オーナー名なし）")
	void testRegisterShopWithoutOrnerFirstName() throws Exception{
		mockMvc.perform(post("/register/insert-shop-info")
				.param("name", "テスト店舗")
				.param("zipcode", "160-0022")
				.param("address", "東京都新宿区")
				.param("telephone", "080-1234-5678")
				.param("ornerLastName", "田中")
				.param("description", "テスト概要です"))
		.andExpect(status().isOk())
		.andExpect(view().name("register-shop"));
	}
	
	@Test
	@DisplayName("店舗情報登録テスト（異常系：店舗概要なし）")
	void testRegisterShopWithoutDescription() throws Exception{
		mockMvc.perform(post("/register/insert-shop-info")
				.param("name", "テスト店舗")
				.param("zipcode", "160-0022")
				.param("address", "東京都新宿区")
				.param("telephone", "080-1234-5678")
				.param("ornerLastName", "田中")
				.param("ornerFirstName", "次郎"))
		.andExpect(status().isOk())
		.andExpect(view().name("register-shop"));
	}
}
