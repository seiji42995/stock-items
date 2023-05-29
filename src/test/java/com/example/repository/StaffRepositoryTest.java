package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.example.domain.Staff;
import com.example.form.RegisterStaffInfoForm;

@SpringBootTest
@Transactional
@Rollback
class StaffRepositoryTest {
	
	@Autowired
	private StaffRepository staffRepository;
	
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
		String alter = "SELECT SETVAL('staffs_staff_id_seq', 2, false)";
		template.execute(alter);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("メールアドレスでの検索テスト")
	void testFindByMailAddress() {
		Staff staff = staffRepository.findByMailAddress("jun.sato@test.com");
		assertEquals(1, staff.getStaffId(), "スタッフ情報が異なります");
		assertEquals("佐藤 潤", staff.getStaffName(), "スタッフ情報が異なります");
	}
	
	@Test
	@DisplayName("スタッフ情報挿入テスト")
	void testInsertStaff() {
		RegisterStaffInfoForm form = new RegisterStaffInfoForm();
		form.setFirstName("匠");
		form.setLastName("井上");
		form.setPassword("12345678");
		form.setEmail("takumi.inoue@test.com");
		form.setAuthority(1);
		form.setShopName("伊勢丹新宿店");
		staffRepository.insertStaff(form);
		Staff staff = staffRepository.findByMailAddress("takumi.inoue@test.com");
		assertEquals(2, staff.getStaffId(), "スタッフ情報が異なります");
		assertEquals("井上 匠", staff.getStaffName(), "スタッフ情報が異なります");
	}

}
