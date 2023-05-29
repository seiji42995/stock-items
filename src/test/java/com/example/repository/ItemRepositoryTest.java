package com.example.repository;

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
import com.example.form.EditItemForm;
import com.example.form.ItemSearchForm;

@SpringBootTest
@Transactional
@Rollback
class ItemRepositoryTest {

	@Autowired
	private ItemRepository itemRepository;
	
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
	@DisplayName("初期商品一覧表示テスト")
	void testFindAll() {
		List<Item> itemList = itemRepository.findAll();
		assertEquals("MLB Cincinnati Reds T Shirt Size XL", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Size 6 Watercolor Inspire Crop", itemList.get(99).getName(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("次ページ商品一覧表示テスト")
	void testGetNextItemList() {
		List<Item> itemList1 = itemRepository.getNextItemList(100);
		List<Item> itemList2 = itemRepository.getNextItemList(1482500);
		assertEquals("Muscle t-shirt", itemList1.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Pregnancy sash", itemList1.get(99).getName(), "正常な値が取得できませんでした");
		assertEquals("Brandy Melville crop top", itemList2.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Brand new lux de ville wallet", itemList2.get(34).getName(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("検索テスト（商品名のみ）")
	void testFindByItemName() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		List<Item> itemList1 = itemRepository.findByItemName(form, 0);
		assertEquals("MLB Cincinnati Reds T Shirt Size XL", itemList1.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("MLB Los Angeles Dodgers 47 brand hat", itemList1.get(99).getName(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("検索テスト（ブランド名のみ）")
	void testFindByBrandName() {
		ItemSearchForm form = new ItemSearchForm();
		form.setBrandName("nike");
		List<Item> itemList1 = itemRepository.findByBrandName(form, 0);
		assertEquals("Girls Nike Pro shorts", itemList1.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Boys shoes bundle", itemList1.get(99).getName(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("検索テスト（カテゴリーのみ選択）")
	void testFindByCategoryId() {
		// 第1カテゴリーのみ入力
		ItemSearchForm form = new ItemSearchForm();
		List<Integer> categoryList = new ArrayList<>();
		// Beautyを選択
		categoryList.add(2);
		form.setCategoryList(categoryList);
		List<Item> itemList1 = itemRepository.findByCategoryId(form, 0);
		assertEquals("Smashbox primer", itemList1.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Reserved for Travis Mendez", itemList1.get(99).getName(), "正常な値が取得できませんでした");
		// Womenを選択
		categoryList.add(1273);
		form.setCategoryList(categoryList);
		itemList1 = itemRepository.findByCategoryId(form, 0);
		assertEquals("AVA-VIV Blouse", itemList1.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Apt. 9 Hand/shoulder Bag", itemList1.get(99).getName(), "正常な値が取得できませんでした");
	}
	
	@Test
	@DisplayName("検索テスト（商品名＆カテゴリー）")
	void testFindByItemNameAndCategoryId() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(2);
		form.setCategoryList(categoryList);
		List<Item> itemList = itemRepository.findByItemNameAndCategoryId(form, 0);
		assertEquals("MLB Cincinnati Reds T Shirt Size XL", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Physicians Formula Bronzer", itemList.get(99).getName(), "正常な値が取得できませんでした");
	}

	@Test
	@DisplayName("検索テスト（カテゴリー＆ブランド名）")
	void testFindByCategoryIdAndBrandName() {
		ItemSearchForm form = new ItemSearchForm();
		List<Integer> categoryList = new ArrayList<>();
		// カテゴリーSports & Outdoorsを選択 
		categoryList.add(994);
		form.setCategoryList(categoryList);
		form.setBrandName("nike");
		List<Item> itemList = itemRepository.findByCategoryIdAndBrandName(form, 0);
		assertEquals("Girls cheer and tumbling bundle of 7", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Women's pink roshe shoes", itemList.get(99).getName(), "正常な値が取得できませんでした");
	}
	
	@Test
	@DisplayName("検索テスト（商品名＆ブランド名）")
	void testFindItemNameAndBrandName() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		form.setBrandName("nike");
		List<Item> itemList = itemRepository.findByItemNameAndBrandName(form, 0);
		assertEquals("MLB Cincinnati Reds T Shirt Size XL", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("Nike Pro Leggings", itemList.get(99).getName(), "正常な値が取得できませんでした");
	}
	
	@Test
	@DisplayName("検索テスト（商品名＆カテゴリー＆ブランド名）")
	void testFindBySearchFormAll() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("new");
		List<Integer> categoryList = new ArrayList<>();
		// Handmadeを選択
		categoryList.add(138);
		form.setCategoryList(categoryList);
		form.setBrandName("nintendo");
		List<Item> itemList = itemRepository.findBySearchFormAll(form, 0);
		assertEquals("New vs pi k body mists", itemList.get(0).getName(), "正常な値が取得できませんでした");
		assertEquals("D3 faux nose ring 22g body jewelry 3/[rm]", itemList.get(99).getName(), "正常な値が取得できませんでした");
	}
	
	@Test
	@DisplayName("商品追加テスト")
	void testAddItem() {
		AddItemForm form = new AddItemForm();
		form.setName("testItem");
		form.setPrice(10.0);
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(786);
		categoryList.add(902);
		categoryList.add(909);
		form.setCategoryList(categoryList);
		form.setBrandName("NIKE");
		form.setCondition(3);
		form.setDescription("test for add item");
		itemRepository.addItem(form);
		Item item = itemRepository.findByItemId(1482536);
		assertEquals(1482536, item.getItemId(), "正常な値が挿入できませんでした");
		assertEquals("testItem", item.getName(), "正常な値が挿入できませんでした");
		assertEquals("NIKE", item.getBrand().getBrandName(), "正常な値が挿入できませんでした");
	}
	
	@Test
	@DisplayName("商品情報編集テスト")
	void testEditItem() {
		EditItemForm form = new EditItemForm();
		form.setName("MLB Cincinnati Reds T Shirt Size XL");
		form.setPrice(12.0);
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(786);
		categoryList.add(902);
		categoryList.add(909);
		form.setCategoryList(categoryList);
		form.setBrandName("Nintendo");
		form.setCondition(3);
		form.setDescription("Test description");
		itemRepository.editItem(form, 1);
		Item item = itemRepository.findByItemId(1);
		assertEquals(12.0, item.getPrice(), "商品情報の修正に失敗しました");
		assertEquals("Nintendo", item.getBrand().getBrandName(), "商品情報の修正に失敗しました");
		assertEquals("Test description", item.getDescription(), "商品情報の修正に失敗しました");
	}
	
	@Test
	@DisplayName("検索テスト（商品ID）")
	void testFindByItemId() {
		Item item = itemRepository.findByItemId(3);
		assertEquals("AVA-VIV Blouse", item.getName(), "正常な値が返ってきていません");
		assertEquals(10.0, item.getPrice(), "正常な値が返ってきていません");
		assertEquals(1408, item.getCategoryList().get(0).getCategoryId(), "正常な値が返ってきていません");
	}
	
	@Test
	@DisplayName("商品追跡ID最大値取得テスト")
	void testFindMaxTrainId() {
		Integer maxTrainId = itemRepository.findMaxTrainId();
		assertEquals(1482534, maxTrainId, "正常な値が取得できていません");
	}
	
	@Test
	@DisplayName("商品ID最大値取得テスト")
	void testFindMaxItemId() {
		Integer maxItemId = itemRepository.findMaxItemId();
		assertEquals(1482535, maxItemId, "正常な値が取得できていません");
	}
	
	@Test
	@DisplayName("レコード数確認テスト（商品名のみ）")
	void testCountItemIdBySearchItemName() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		Integer countNum = itemRepository.countItemIdBySearchItemName(form);
		assertEquals(284, countNum, "正常な値が取得できていません");
	}
	
	@Test
	@DisplayName("レコード数確認テスト（カテゴリーのみ）")
	void testCountItemIdBySearchCategory() {
		ItemSearchForm form = new ItemSearchForm();
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(2);
		form.setCategoryList(categoryList);
		Integer countNum = itemRepository.countItemIdBySearchCategory(form);
		assertEquals(207828, countNum, "正常な値が取得できていません");
	}
	
	@Test
	@DisplayName("レコード数確認テスト（ブランドのみ）")
	void testCountItemIdBySearchBrandName() {
		ItemSearchForm form = new ItemSearchForm();
		form.setBrandName("nike");
		Integer countNum = itemRepository.countItemIdBySearchBrandName(form);
		assertEquals(54147, countNum, "正常な値が取得できていません");
	}
	
	@Test
	@DisplayName("レコード数確認テスト（商品名＆カテゴリー）")
	void testCountItemIdBySearchItemNameAndCategory() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		List<Integer> categoryList = new ArrayList<>();
		categoryList.add(2);
		form.setCategoryList(categoryList);
		Integer countNum = itemRepository.countItemIdBySearchItemNameAndCategory(form);
		assertEquals(208110, countNum, "正常な値が取得できていません");
	}
	
	@Test
	@DisplayName("レコード数確認テスト（カテゴリー＆ブランド）")
	void testCountItemIdBySearchCategoryAndBrandName() {
		ItemSearchForm form = new ItemSearchForm();
		List<Integer> categoryList = new ArrayList<>();
		// Electronics, media, CDを選択
		categoryList.add(68);
		categoryList.add(112);
		categoryList.add(114);
		form.setCategoryList(categoryList);
		form.setBrandName("nike");
		Integer countNum = itemRepository.countItemIdBySearchCategoryAndBrandName(form);
		assertEquals(55618, countNum, "正常な値が取得できていません");
	}
	
	@Test
	@DisplayName("レコード数確認テスト（商品名＆ブランド名）")
	void testCountItemIdBySearchItemNameAndBrandName() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		form.setBrandName("nintendo");
		Integer countNum = itemRepository.countItemIdBySearchItemNameAndBrandName(form);
		assertEquals(15290, countNum, "正常な値が取得できていません");
	}
	
	@Test
	@DisplayName("レコード数確認（フォーム全入力）")
	void testCountItemIdBySearchFormAll() {
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("mlb");
		List<Integer> categoryList = new ArrayList<>();
		// Electronics, media, CDを選択
		categoryList.add(68);
		categoryList.add(112);
		categoryList.add(114);
		form.setCategoryList(categoryList);
		form.setBrandName("nintendo");
		Integer countNum = itemRepository.countItemIdBySearchFormAll(form);
		assertEquals(16819, countNum, "正常な値が取得できていません");
	}
}
