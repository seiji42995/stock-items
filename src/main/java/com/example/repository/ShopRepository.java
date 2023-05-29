package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Shop;
import com.example.form.RegisterShopForm;

/**
 * 店舗情報を取得するリポジトリクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Repository
public class ShopRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static String TABLE_NAME = "shops";
	
	private final static RowMapper<Shop> SHOP_ROW_MAPPER = (rs, i) -> {
		
		Shop shop = new Shop();
		shop.setShopId(rs.getInt("shop_id"));
		shop.setShopName(rs.getString("shop_name"));
		shop.setZipcode(rs.getString("shop_zipcode"));
		shop.setAddress(rs.getString("shop_address"));
		shop.setTelephone(rs.getString("shop_telephone"));
		shop.setOrnerName(rs.getString("shop_orner_name"));
		shop.setShopDescription(rs.getString("shop_description"));
		return shop;
	};
	
	/**
	 * 全店舗情報を取得する.
	 * 
	 * @return 取得した全店舗情報リスト
	 */
	public List<Shop> findAll(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT shop_id, shop_name, shop_zipcode, shop_address, shop_telephone,");
		sql.append(" shop_orner_name, shop_description FROM " + TABLE_NAME + " ORDER BY shop_id;");
		List<Shop> shopList = template.query(sql.toString(), SHOP_ROW_MAPPER);
		return  shopList;
	}
	
	/**
	 * 店舗情報を登録する.
	 * 
	 * @param 登録対象となる店舗情報
	 */
	public void insertShopInfo(RegisterShopForm form) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO " + TABLE_NAME);
		insertSql.append("(shop_name, shop_zipcode, shop_address, shop_telephone, shop_orner_name, shop_description) ");
		insertSql.append("VALUES(:shopName, :shopZipcode, :shopAddress, :shopTelephone, :shopOrnerName, :shopDescription);");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("shopName", form.getName()).addValue("shopZipcode", form.getZipcode())
				.addValue("shopAddress", form.getAddress()).addValue("shopTelephone", form.getTelephone())
				.addValue("shopOrnerName", form.getOrneaLastName() + " " + form.getOrnerFirstName()).addValue("shopDescription", form.getDescription());
		template.update(insertSql.toString(), param);	
	}
	
	public Integer findByShopName(String shopName) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT shop_id, shop_name, shop_zipcode, shop_address, shop_telephone,");
		sql.append(" shop_orner_name, shop_description FROM " + TABLE_NAME + " WHERE shop_name = :shopName ORDER BY shop_id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("shopName", shopName);
		List<Shop> shopList = template.query(sql.toString(), param, SHOP_ROW_MAPPER);
		Integer shopId = shopList.get(0).getShopId();
		return shopId;
	}
}
