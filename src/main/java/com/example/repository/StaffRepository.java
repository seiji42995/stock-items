package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Staff;
import com.example.form.RegisterStaffInfoForm;

/**
 * スタッフ情報を操作するリポジトリクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Repository
public class StaffRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired
	private ShopRepository shopRepository;

	private final static String TABLE_NAME = "staffs";

	private final static RowMapper<Staff> STAFF_ROW_MAPPER = (rs, i) -> {

		Staff staff = new Staff();
		staff.setStaffId(rs.getInt("staff_id"));
		staff.setStaffName(rs.getString("staff_name"));
		staff.setMailAddress(rs.getString("staff_email"));
		staff.setPassword(rs.getString("staff_password"));
		staff.setStaffAuthority(rs.getInt("staff_authority"));
		staff.setShopId(rs.getInt("shop_id"));
		return staff;
	};

	/**
	 * スタッフ登録情報をstaffsテーブルにinsertする.
	 * 
	 * @param スタッフ登録画面で入力されたフォーム情報
	 */
	public void insertStaff(RegisterStaffInfoForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO " + TABLE_NAME + "(staff_name, staff_email, staff_password, staff_authority, shop_id)");
		sql.append(" VALUES(:name, :email, :password, :authority, :shopId);");

		// フォームには選択した店舗が店舗名で入っているため、ここで店舗IDを取得
		Integer shopId = shopRepository.findByShopName(form.getShopName());

		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", form.getLastName() + " " + form.getFirstName()).addValue("email", form.getEmail())
				.addValue("password", form.getPassword()).addValue("authority", form.getAuthority())
				.addValue("shopId", shopId);
		template.update(sql.toString(), param);
	}

	public Staff findByMailAddress(String mailAddress) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT staff_id, staff_name, staff_email, staff_password, staff_authority, shop_id ");
		sql.append("FROM " + TABLE_NAME + " WHERE staff_email = :staffEmail;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("staffEmail", mailAddress);
		List<Staff> staffList = template.query(sql.toString(), param, STAFF_ROW_MAPPER);
		if (staffList.size() == 0) {
			return null;
		}
		return staffList.get(0);
	}

}
