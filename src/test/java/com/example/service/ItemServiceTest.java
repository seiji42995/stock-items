package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.form.AddItemForm;
import com.example.form.ItemSearchForm;

@SpringBootTest
@Transactional
@Rollback
class ItemServiceTest {

	@Autowired
	private ItemService itemService;

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
		String alter = "SELECT SETVAL('items_item_id_seq', 1482536, false)";
		template.execute(alter);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("初期商品一覧取得")
	void testFindAll() {
		List<Item> itemList = itemService.findAll();
		assertEquals("Black and Red Baseball Tee", itemList.get(24).getName(), "正常な値が取得できませんでした");
		assertEquals(100, itemList.size(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("商品追加テスト")
	void testAddItem() {
		AddItemForm form = new AddItemForm();
		form.setName("service_test_item");
		form.setPrice(50.0);
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(786);
		categoryList.add(787);
		categoryList.add(789);
		form.setCategoryList(categoryList);
		form.setBrandName("Alia");
		form.setCondition(2);
		form.setDescription("サービスクラスのテスト");
		itemService.addItem(form);
		Item item = itemService.findByItemId(1482536);
		assertEquals("service_test_item", item.getName(), "正常な値が取得できませんでした");
		assertEquals(50.0, item.getPrice(), "正常な値が取得できませんでした");
		assertEquals("Jackets", item.getCategoryList().get(item.getCategoryList().size() - 1).getCategoryName(),
				"正常な値が取得できませんでした");
		assertEquals("Alia", item.getBrand().getBrandName(), "正常な値が取得できませんでした");
		assertEquals(2, item.getConditionId(), "正常な値が取得できませんでした");
		assertEquals("サービスクラスのテスト", item.getDescription(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("次ページ商品リスト取得リスト")
	void testGetNextItem() {
		List<Item> itemList = itemService.getNextItem(2);
		assertEquals(101, itemList.get(0).getItemId(), "正常な値が取得できませんでした");
		assertEquals("Muscle t-shirt", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals(12.0, itemList.get(0).getPrice(), "正常な値が取得できませんでした");
		assertEquals("T-Shirts", itemList.get(0).getCategoryList().get(2).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals(200, itemList.get(itemList.size() - 1).getItemId(), "正常な値が取得できませんでした");
		assertEquals("Pregnancy sash", itemList.get(itemList.size() - 1).getName(), "正常な値が取得できませんでした");
		assertEquals(25.0, itemList.get(itemList.size() - 1).getPrice(), "正常な値が取得できませんでした");
		assertEquals("Belts", itemList.get(itemList.size() - 1).getCategoryList().get(2).getCategoryName(),
				"正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("商品ID最大値取得テスト")
	void testGetMaxItemId() {
		Integer maxItemId = itemService.getMaxItemId();
		assertEquals(1482535, maxItemId, "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("商品検索テスト")
	void testSearchItem() {
		// 商品名のみ入力した場合
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		List<Integer> categoryList = new ArrayList<>();
		form.setCategoryList(categoryList);
		form.setBrandName("");
		List<Item> itemList = itemService.searchItem(form, 0);
		assertEquals("MLB Cincinnati Reds T Shirt Size XL", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("MLB Los Angeles Dodgers 47 brand hat", itemList.get(99).getName(), "正常な値が取得できませんでした");

		// カテゴリーのみ選択した場合
		form.setItemName("");
		categoryList.add(68);
		form.setCategoryList(categoryList);
		itemList = itemService.searchItem(form, 0);
		assertEquals("Razer BlackWidow Chroma Keyboard", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Micro Usb 3in1 Otg Hub Extension Adapter", itemList.get(99).getName(), "正常な値が取得できませんでした");

		// ブランド名のみ入力した場合
		categoryList.remove(0);
		form.setCategoryList(categoryList);
		form.setBrandName("nintendo");
		itemList = itemService.searchItem(form, 0);
		assertEquals("Goosebumps HorrorLand DS Game", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Nintendo DSi", itemList.get(99).getName(), "正常な値が取得できませんでした");

		// 商品名とカテゴリーを入力した場合
		form.setItemName("mlb");
		categoryList.add(68);
		form.setBrandName("");
		itemList = itemService.searchItem(form, 0);
		assertEquals("XBOX One", itemList.get(5).getName(), "正常な値が取得できませんでした");
		assertEquals("Medal Of Honor European Assault Xbox", itemList.get(99).getName(), "正常な値が取得できませんでした");

		// カテゴリーとブランド名を入力した場合
		form.setItemName("");
		categoryList.remove(0);
		// Homeをセット
		categoryList.add(461);
		form.setBrandName("nintendo");
		itemList = itemService.searchItem(form, 0);
		assertEquals("Leather Horse Statues", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Pokémon Y and starwar game", itemList.get(99).getName(), "正常な値が取得できませんでした");

		// 商品名とブランド名を入力した場合
		form.setItemName("picture");
		categoryList.remove(0);
		form.setBrandName("Jeffree Star Cosmetics");
		itemList = itemService.searchItem(form, 0);
		assertEquals("Picture frame", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Peanuts Picture Frame", itemList.get(99).getName(), "正常な値が取得できませんでした");

		// フォーム全入力
		categoryList.add(68);
		form.setItemName("mlb");
		form.setCategoryList(categoryList);
		form.setBrandName("nintendo");
		itemList = itemService.searchItem(form, 0);
		assertEquals("Triple car charger", itemList.get(3).getName(), "正常な値が取得できませんでした");
		assertEquals("Selling", itemList.get(99).getName(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("カテゴリーリスト整理テスト")
	void testCleanupCategory() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("");
		List<Integer> categoryList = new ArrayList<>(3);
		categoryList.add(null);
		categoryList.add(null);
		categoryList.add(null);
		categoryList.set(0, 68);
		form.setCategoryList(categoryList);
		assertEquals(3, categoryList.size(), "正常な値が取得できませんでした");
		form = itemService.cleanupCategory(form);
		assertEquals(1, form.getCategoryList().size(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("レコード数取得")
	void testGetMaxCount() {
		Integer countItem = 0;
		
		// 商品名のみ入力した場合
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		List<Integer> categoryList = new ArrayList<>();
		form.setCategoryList(categoryList);
		form.setBrandName("");
		countItem = itemService.getMaxCount(form);
		assertEquals(284, countItem, "正常な値が取得できませんでした");

		// カテゴリーのみ選択した場合
		form.setItemName("");
		categoryList.add(68);
		form.setCategoryList(categoryList);
		countItem = itemService.getMaxCount(form);
		assertEquals(122690, countItem, "正常な値が取得できませんでした");

		// ブランド名のみ入力した場合
		categoryList.remove(0);
		form.setCategoryList(categoryList);
		form.setBrandName("nintendo");
		countItem = itemService.getMaxCount(form);
		assertEquals(15007, countItem, "正常な値が取得できませんでした");

		// 商品名とカテゴリーを入力した場合
		form.setItemName("mlb");
		categoryList.add(461);
		form.setBrandName("");
		countItem = itemService.getMaxCount(form);
		assertEquals(68151, countItem, "正常な値が取得できませんでした");

		// カテゴリーとブランド名を入力した場合
		form.setItemName("");
		categoryList.remove(0);
		// カテゴリーにHomeをセット
		categoryList.add(461);
		form.setBrandName("nintendo");
		countItem = itemService.getMaxCount(form);
		assertEquals(82853, countItem, "正常な値が取得できませんでした");

		// 商品名とブランド名を入力した場合
		form.setItemName("picture");
		categoryList.remove(0);
		form.setBrandName("Jeffree Star Cosmetics");
		countItem = itemService.getMaxCount(form);
		assertEquals(1467, countItem, "正常な値が取得できませんでした");

		// フォーム全入力
		form.setItemName("mlb");
		categoryList.add(68);
		form.setBrandName("nintendo");
		form.setCategoryList(categoryList);
		countItem = itemService.getMaxCount(form);
		assertEquals(124277, countItem, "正常な値が取得できませんでした");
	}

}
