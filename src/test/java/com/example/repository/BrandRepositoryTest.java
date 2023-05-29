package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.domain.Brand;

@SpringBootTest
class BrandRepositoryTest {

	@Autowired
	private BrandRepository brandRepository;
	
//	@Autowired
//	private static NamedParameterJdbcTemplate template;
	
	
//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterAll
//	static void tearDownAfterClass() throws Exception {
//	}

//	@BeforeEach
//	void setUp() throws Exception {
//		System.out.println("DB初期化処理開始");
//		
//		
//		
//	}
//
//	@AfterEach
//	void tearDown() throws Exception {
//	}

	@Test
	@DisplayName("全件検索テスト")
	void testFindAll() {
		System.out.println("全件検索テスト開始");
		List<Brand> brandList = new ArrayList<>();
		brandList = brandRepository.findAll();
		assertEquals("!iT Jeans", brandList.get(1).getBrandName(), "該当するブランド名がありません");
		assertEquals("Adams Golf", brandList.get(99).getBrandName(), "該当するブランド名がありません");
		assertEquals("wallis", brandList.get(4809).getBrandName(), "該当するブランド名がありません");
		System.out.println("全件検索テスト終了");
	}
	
	@Test
	@DisplayName("ブランド名検索テスト")
	void testFindByBrandName() {
		System.out.println("ブランド名検索テスト開始");
		Brand brand = brandRepository.findByBrandName("butter LONDON");
		assertEquals(4748, brand.getBrandId(), "ブランドIDが一致しませんでした");
		brand = brandRepository.findByBrandName("");
		assertEquals(1, brand.getBrandId(), "ブランドIDが一致しませんでした");
		System.out.println("ブラント名検索テスト終了");
	}
	
	@Test
	@DisplayName("ブランドの新規追加テスト")
	void testInsert() {
		System.out.println("ブランド新規追加テスト開始");
		brandRepository.insert("テストブランド");
		List<Brand> brandList = brandRepository.findAll();
		Brand brand = brandRepository.findByBrandName("テストブランド");
		assertEquals(brandList.get(brandList.size() - 1).getBrandId(), brand.getBrandId(), "ブランドが挿入できていません");
		System.out.println("ブランド新規追加テスト終了");
	}
	
	@AfterAll
	static void tearDownAfterClass(@Autowired NamedParameterJdbcTemplate template) throws Exception {
		String sql = "DELETE FROM brands WHERE brand_name = :brandName;";
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("brandName", "テストブランド");
		try {
			template.update(sql, param);			
		}catch(Exception e) {
			System.err.println("例外処理が発生しました");
			e.printStackTrace();
		}
		System.out.println("ブランド新規追加テストで挿入したデータは削除しました");
	}

}
