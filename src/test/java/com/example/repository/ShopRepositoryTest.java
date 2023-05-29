package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Shop;
import com.example.form.RegisterShopForm;

@SpringBootTest
@Transactional
@Rollback
class ShopRepositoryTest {

	@Autowired
	private ShopRepository shopRepository;
	
	@Autowired
	private JdbcTemplate template;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		String alter = "SELECT SETVAL('shops_shop_id_seq', 6, false)";
		template.execute(alter);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("店名全件検索テスト")
	void testFindAll() {
		System.out.println("店名全件検索テスト開始");
		List<Shop> shopList = shopRepository.findAll();
		assertEquals("鈴木商店", shopList.get(0).getShopName(), "店名が正しくありません");
		assertEquals("123-4567", shopList.get(0).getZipcode(), "郵便番号が正しくありません");
		assertEquals("鈴木 一郎", shopList.get(0).getOrnerName(), "オーナー名が正しくありません");
		assertEquals("ARTNIA", shopList.get(2).getShopName(), "店名が正しくありません");
		assertEquals("160-0022", shopList.get(2).getZipcode(), "郵便番号が正しくありません");
		assertEquals("新田三郎", shopList.get(2).getOrnerName(), "オーナー名が正しくありません");
		assertEquals("伊勢丹新宿店", shopList.get(4).getShopName(), "店名が正しくありません");
		assertEquals("160-0022", shopList.get(4).getZipcode(), "郵便番号が正しくありません");
		assertEquals("伊勢五郎", shopList.get(4).getOrnerName(), "オーナー名が正しくありません");
		System.out.println("店名全件検索テスト終了");
	}

	@Test
	@DisplayName("店舗名検索テスト")
	void testFindByShopName() {
		System.out.println("店舗名検索テスト開始");
		Integer shopId = shopRepository.findByShopName("人生酒場");
		assertThat(shopId).isEqualTo(4);
	}

	@Test
	@DisplayName("店舗情報登録テスト")
	void testInsertShopInfo() {
		System.out.println("店舗情報登録テスト開始");
		RegisterShopForm form = new RegisterShopForm();
		form.setName("東京タワー");
		form.setZipcode("105-0011");
		form.setAddress("東京都港区芝公園4-2-8");
		form.setTelephone("03-3433-5111");
		form.setOrneaLastName("佐藤");
		form.setOrnerFirstName("静");
		form.setDescription("1958年に竣工された高さ333mの電波塔。東京のシンボルとして知られ、高さ150mと250mの2つの展望台と飲食施設を備える");
		shopRepository.insertShopInfo(form);
		List<Shop> shopList = shopRepository.findAll();
		assertEquals("東京タワー", shopList.get(shopList.size() - 1).getShopName(), "insertされた値が正しくありません");
		assertEquals("佐藤 静", shopList.get(shopList.size() - 1).getOrnerName(), "insertされた値が正しくありません");
		assertEquals(6, shopList.get(shopList.size() - 1).getShopId(), "insertされた情報が正しくありません");
		System.out.println("店舗情報登録テスト終了");
	}

}
