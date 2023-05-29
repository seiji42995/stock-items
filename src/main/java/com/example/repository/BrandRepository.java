package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Brand;

/**
 * ブランド情報を操作するリポジトリクラス.	
 * 
 * @author seiji_kitahara
 *
 */
@Repository
public class BrandRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static String TABLE_NAME = "brands";
	
	private final static RowMapper<Brand> BRAND_ROW_MAPPER = (rs, i) -> {
		
		Brand brand = new Brand();
		brand.setBrandId(rs.getInt("brand_id"));
		brand.setBrandName(rs.getString("brand_name"));
		return brand;
	};
	
	/**
	 * 全ブランド情報を取得する.
	 * 
	 * @return 全ブランド情報が詰まったリスト
	 */
	public List<Brand> findAll(){
		String sql = "SELECT brand_id, brand_name FROM " + TABLE_NAME + " ORDER BY brand_id;";
		List<Brand> brandList = template.query(sql, BRAND_ROW_MAPPER);
		if(brandList.size() == 0) {
			return null;
		}
		return brandList;
	}
	
	/**
	 * ブランド名から該当する情報を取得する.
	 * 
	 * @param ブランド名
	 * @return 検索結果として該当したブランド情報。該当しない場合はnullを返す
	 */
	public Brand findByBrandName(String brandName) {
		String sql = "SELECT brand_id, brand_name FROM " + TABLE_NAME + " WHERE brand_name = :brandName;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("brandName", brandName);
		List<Brand> brandList = template.query(sql, param, BRAND_ROW_MAPPER);
		if(brandList.size() == 0) {
			return null;
		}
		return brandList.get(0);
	}
	
	/**
	 * 新規ブランドの場合、新規追加する.
	 * 
	 * @param ブランド名
	 */
	public void insert(String brandName) {
		String sql = "INSERT INTO " + TABLE_NAME + " (brand_name) VALUES(:brandName);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("brandName", brandName);
		template.update(sql, param);
	}
	
}
