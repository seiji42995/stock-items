package com.example.repository;

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

import com.example.domain.Category;

@SpringBootTest
@Transactional
@Rollback
class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("カテゴリーID検索テスト")
	void testFindByCategoryId() {
		Category category1 = categoryRepository.findByCategoryId(5);
		Category category2 = categoryRepository.findByCategoryId(1443);
		assertEquals("Bathing Accessories", category1.getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Beauty/Bath & Body/Bathing Accessories", category1.getNameAll(), "正常な値が取得できませんでした");
		assertEquals("Totes & Shoppers", category2.getCategoryName(), "正常な値が取得できません");
		assertEquals("Women/Women's Handbags/Totes & Shoppers", category2.getNameAll(), "正常な値が取得できません");
	}
	
	@Test
	@DisplayName("階層検索テスト")
	void testFindByHierarchy() {
		List<List<Category>> categoryList = categoryRepository.findByHierarchy();
		assertEquals("Beauty", categoryList.get(0).get(1).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Women", categoryList.get(0).get(10).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Bath & Body", categoryList.get(1).get(0).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Women's Handbags", categoryList.get(1).get(137).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Bath", categoryList.get(2).get(0).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Totes & Shoppers", categoryList.get(2).get(1285).getCategoryName(), "正常な値が取得できませんでした");
	}
	
	@Test
	@DisplayName("親カテゴリーも含めたカテゴリー検索テスト")
	void testFindCategoryAllByCategoryId() {
		List<Category> categoryList = categoryRepository.findCategoryAllByCategoryId(4);
		List<Category> categoryList2 = categoryRepository.findCategoryAllByCategoryId(110);
		List<Category> categoryList3 = categoryRepository.findCategoryAllByCategoryId(1433);
		assertEquals("Beauty", categoryList.get(0).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Bath & Body", categoryList.get(1).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Bath", categoryList.get(2).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Electronics", categoryList2.get(0).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Computers & Tablets", categoryList2.get(1).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("iPad", categoryList2.get(2).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Tablet", categoryList2.get(3).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("eBook Access", categoryList2.get(4).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Women", categoryList3.get(0).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Women's Accessories", categoryList3.get(1).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Watches", categoryList3.get(2).getCategoryName(), "正常な値が取得できませんでした");
		
	}
	
}
