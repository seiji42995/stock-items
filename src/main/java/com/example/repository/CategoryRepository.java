package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final static String TABLE_NAME_CATEGORY = "categories";
	private final static String TABLE_NAME_TREEPATH = "treepaths";
	
	private final static RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) -> {
	
		Category category = new Category();
		category.setCategoryId(rs.getInt("category_id"));
		category.setParentId(rs.getInt("parent_id"));
		category.setCategoryName(rs.getString("category_name"));
		category.setNameAll(rs.getString("name_all"));
		category.setHierarchy(rs.getInt("hierarchy"));
//		category.setItemNum(rs.getInt(""));
		return category;
	};
	
	private final static RowMapper<Category> CATEGORYANDTREEPATH_ROW_MAPPER = (rs, i) -> {
		
		Category category = new Category();
		category.setCategoryId(rs.getInt("c_category_id"));
		category.setParentId(rs.getInt("c_parent_id"));
		category.setCategoryName(rs.getString("c_category_name"));
		category.setNameAll(rs.getString("c_name_all"));
		category.setHierarchy(rs.getInt("c_hierarchy"));
		return category;
	};
	
	/**
	 * 対象のカテゴリーID情報を取得する.
	 * 
	 * @param カテゴリーID
	 * @return 該当するカテゴリー情報
	 */
	public Category findByCategoryId(Integer categoryId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT category_id, parent_id, category_name, name_all, hierarchy ");
		sql.append("FROM " + TABLE_NAME_CATEGORY + " WHERE category_id = :categoryId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);
		List<Category> categoryList = template.query(sql.toString(), param, CATEGORY_ROW_MAPPER);
		if(categoryList.size() == 0) {
			return null;
		}
		return categoryList.get(0);
	}
	
	/**
	 * 階層毎のカテゴリー情報を取得する.
	 * 
	 * @param 階層数
	 * @return 対象階層に該当するカテゴリー情報が詰まったリスト
	 */
	public List<Category> findByHierarchy(Integer hierarchy){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT category_id, parent_id, category_name, name_all, hierarchy ");
		sql.append("FROM " + TABLE_NAME_CATEGORY + " WHERE hierarchy = :hierarchy ORDER BY category_id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("hierarchy", hierarchy);
		List<Category> categoryList = template.query(sql.toString(), param, CATEGORY_ROW_MAPPER);
		if(categoryList.size() == 0) {
			return null;
		}
		return categoryList;
	}
	
	public Integer checkMaxHierarchy() {
		String sql = "SELECT MAX(hierarchy) FROM " + TABLE_NAME_CATEGORY + ";";
//		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		Integer maxHierarchy = jdbcTemplate.queryForObject(sql, Integer.class);
		return maxHierarchy;
	}
	
	public List<Category> findCategoryAllByCategoryId(Integer categoryId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.category_id AS c_category_id, c.parent_id AS c_parent_id,");
		sql.append("c.category_name AS c_category_name, c.name_all AS c_name_all, c.hierarchy AS c_hierarchy ");
		sql.append("FROM " + TABLE_NAME_CATEGORY + " AS c ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON c.category_id = t.ancestor_id ");
		sql.append("WHERE t.descendant_id = :categoryId ORDER BY c_category_id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);
		List<Category> categoryList = template.query(sql.toString(), param, CATEGORYANDTREEPATH_ROW_MAPPER);
		if(categoryList.size() == 0) {
			return null;
		}
		return categoryList;
	}
	
}
