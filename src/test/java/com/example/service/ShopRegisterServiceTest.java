package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Shop;
import com.example.form.RegisterShopForm;
import com.example.repository.ShopRepository;

@SpringBootTest
@Transactional
@Rollback
class ShopRegisterServiceTest {

	@Autowired
	private ShopRegisterService service;
	@Autowired
	private ShopRepository repository;
	
	RegisterShopForm form = new RegisterShopForm();
	
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
	void testInsertShopInfo() {
		service.insertShopInfo(form);
		List<Shop> shopList = repository.findAll();
		assertEquals("テスト店", shopList.get(shopList.size() - 1).getShopName(), "正常に値が挿入できていません");
		assertEquals("東京都新宿区", shopList.get(shopList.size() - 1).getAddress(), "正常に値が挿入できていません");
		assertEquals("080-1234-5678", shopList.get(shopList.size() - 1).getTelephone(), "正常に値が挿入できていません");
		assertEquals("鈴木 一郎", shopList.get(shopList.size() - 1).getOrnerName(), "正常に値が挿入できていません");
		assertEquals("テスト店舗登録", shopList.get(shopList.size() - 1).getShopDescription(), "正常に値が挿入できていません");
	}

}
