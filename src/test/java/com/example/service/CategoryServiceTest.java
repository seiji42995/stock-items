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

import com.example.domain.Category;

@SpringBootTest
@Transactional
@Rollback
class CategoryServiceTest {
	
	@Autowired
	private CategoryService categoryService;

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
	@DisplayName("カテゴリーリスト取得テスト")
	void testFindCategoryList() {
		List<List<Category>> categoryList = categoryService.findCategoryList();
		assertEquals("Beauty", categoryList.get(0).get(1).getCategoryName(), "正常な値が取得できませんでした");
		assertEquals("Fragrance", categoryList.get(1).get(1).getCategoryName(), "正常な値が取得できませんでした");
	}

}
