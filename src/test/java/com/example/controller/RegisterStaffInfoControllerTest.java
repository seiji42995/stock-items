package com.example.controller;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
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

@Transactional
@Rollback
@SpringBootTest
class RegisterStaffInfoControllerTest {

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
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build(); 
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); 
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("アカウント登録画面遷移テスト")
	void testIndex() throws Exception {
		this.mockMvc.perform(get("/register"))
		.andExpect(status().isOk())
		.andExpect(unauthenticated())
		.andExpect(view().name("register"));
	}
	
	@Test
	@DisplayName("アカウント登録テスト（正常系）")
	void testRegisteStaffInfo() throws Exception{
		this.mockMvc.perform(post("/register/insert-staff-info")
				.param("firstName", "太郎")
				.param("lastName", "田中")
				.param("email", "taro.tanaka@test.com")
				.param("password", "Tarotanaka12")
				.param("confirmationPassword", "Tarotanaka12")
				.param("authority", "0")
				.param("shopName", "人生酒場"))
		.andExpect(status().is3xxRedirection())
		.andExpect(unauthenticated())
		.andExpect(view().name("redirect:/register/to-login"));
	}
	
	@Test
	@DisplayName("アカウント登録テスト（異常系：名前なし）")
	void testFailureRegisteStaffInfoByNoFirstName() throws Exception{
		this.mockMvc.perform(post("/register/insert-staff-info")
				.param("lastName", "田中")
				.param("email", "taro.tanaka@test.com")
				.param("password", "Tarotanaka12")
				.param("confirmationPassword", "Tarotanaka12")
				.param("authority", "0")
				.param("shopName", "人生酒場"))
		.andExpect(status().isOk())
		.andExpect(unauthenticated())
		.andExpect(view().name("register"));
	}
	
	@Test
	@DisplayName("アカウント登録テスト（異常系：苗字なし）")
	void testFailureRegisteStaffInfoByNoLastName() throws Exception{
		this.mockMvc.perform(post("/register/insert-staff-info")
				.param("firstName", "太郎")
				.param("email", "taro.tanaka@test.com")
				.param("password", "Tarotanaka12")
				.param("confirmationPassword", "Tarotanaka12")
				.param("authority", "0")
				.param("shopName", "人生酒場"))
		.andExpect(status().isOk())
		.andExpect(unauthenticated())
		.andExpect(view().name("register"));
	}
	
	@Test
	@DisplayName("アカウント登録テスト（異常系：メールアドレスなし）")
	void testFailureRegisteStaffInfoByNoMailAddress() throws Exception{
		this.mockMvc.perform(post("/register/insert-staff-info")
				.param("firstName", "太郎")
				.param("lastName", "田中")
				.param("password", "Tarotanaka12")
				.param("confirmationPassword", "Tarotanaka12")
				.param("authority", "0")
				.param("shopName", "人生酒場"))
		.andExpect(status().isOk())
		.andExpect(unauthenticated())
		.andExpect(view().name("register"));
	}
	
	@Test
	@DisplayName("アカウント登録テスト（異常系：パスワードなし）")
	void testFailureRegisteStaffInfoByNoPassword() throws Exception{
		this.mockMvc.perform(post("/register/insert-staff-info")
				.param("firstName", "太郎")
				.param("lastName", "田中")
				.param("email", "taro.tanaka@test.com")
				.param("password", "")
				.param("confirmationPassword", "Tarotanaka12")
				.param("authority", "0")
				.param("shopName", "人生酒場"))
		.andExpect(status().isOk())
		.andExpect(unauthenticated())
		.andExpect(view().name("register"));
	}
	
	@Test
	@DisplayName("アカウント登録テスト（異常系：確認用パスワードなし）")
	void testFailureRegisteStaffInfoByNoConfirmationPassword() throws Exception{
		this.mockMvc.perform(post("/register/insert-staff-info")
				.param("firstName", "太郎")
				.param("lastName", "田中")
				.param("email", "taro.tanaka@test.com")
				.param("password", "Tarotanaka12")
				.param("confirmationPassword", "")
				.param("authority", "0")
				.param("shopName", "人生酒場"))
		.andExpect(status().isOk())
		.andExpect(unauthenticated())
		.andExpect(view().name("register"));
	}
	
	@Test
	@DisplayName("アカウント登録テスト（異常系：所属店舗選択なし）")
	void testFailureRegisteStaffInfoByNoShopName() throws Exception{
		this.mockMvc.perform(post("/register/insert-staff-info")
				.param("firstName", "太郎")
				.param("lastName", "田中")
				.param("email", "taro.tanaka@test.com")
				.param("password", "Tarotanaka12")
				.param("confirmationPassword", "Tarotanaka12")
				.param("authority", "0"))
		.andExpect(status().isOk())
		.andExpect(unauthenticated())
		.andExpect(view().name("register"));
	}
	
	@Test
	@DisplayName("アカウント登録テスト（異常系：確認用パスワード不一致）")
	void testFailureRegisteStaffInfoByNoMatchPassword() throws Exception{
		this.mockMvc.perform(post("/register/insert-staff-info")
				.param("firstName", "太郎")
				.param("lastName", "田中")
				.param("email", "taro.tanaka@test.com")
				.param("password", "Tarotanaka12")
				.param("confirmationPassword", "Tarotanaka13")
				.param("authority", "0")
				.param("shopName", "人生酒場"))
		.andExpect(status().isOk())
		.andExpect(unauthenticated())
		.andExpect(view().name("register"));
	}

}
