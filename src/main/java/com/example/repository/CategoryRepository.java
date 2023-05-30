package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

/**
 * @author seiji_kitahara
 *
 */
@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static String TABLE_NAME_CATEGORY = "categories";
	private final static String TABLE_NAME_TREEPATH = "treepaths";
	private final static String TABLE_NAME_ITEM = "items";
	
	private final static RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) -> {
	
		Category category = new Category();
		category.setCategoryId(rs.getInt("category_id"));
		category.setParentId(rs.getInt("parent_id"));
		category.setCategoryName(rs.getString("category_name"));
		category.setNameAll(rs.getString("name_all"));
		category.setHierarchy(rs.getInt("hierarchy"));
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
	
	private final static ResultSetExtractor<List<List<Category>>> RESULT_SET_EXTRACTOR_FOR_COUNTITEM = (rs) -> {

		List<List<Category>> categoryListByHierarchy = new ArrayList<>();
		List<Category> categoryList = null;
		int previousHierarchy = -1;
		int nowHierarchy = 0;

		while (rs.next()) {
			nowHierarchy = rs.getInt("c_hierarchy");
			if(nowHierarchy != previousHierarchy) {
				categoryList = new ArrayList<>();
				categoryListByHierarchy.add(categoryList);
			}
			// Categoryの空リストを作成
			Category category = new Category();
			category.setCategoryId(rs.getInt("c_category_id"));
			category.setParentId(rs.getInt("c_parent_id"));
			category.setCategoryName(rs.getString("c_categoy_name"));
			category.setNameAll(rs.getString("c_name_all"));
			category.setHierarchy(rs.getInt("c_hierarchy"));
//			int itemNum = rs.getInt("count(i.item_id)");
			category.setItemNum(rs.getInt("item_count"));
			categoryList.add(category);
			
			previousHierarchy = nowHierarchy;
		}

		return categoryListByHierarchy;
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
	public List<List<Category>> findByHierarchy(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.category_id AS c_category_id, c.parent_id AS c_parent_id, c.category_name AS c_categoy_name, ");
		sql.append("c.name_all AS c_name_all, c.hierarchy AS c_hierarchy, count(i.item_id) AS item_count ");
		sql.append("FROM " + TABLE_NAME_CATEGORY + " AS c ");
		sql.append("JOIN " + TABLE_NAME_TREEPATH + " AS t ON c.category_id = t.ancestor_id ");
		sql.append("JOIN " + TABLE_NAME_ITEM + " AS i ON t.descendant_id = i.category_id ");
		sql.append("GROUP BY c.category_id ORDER BY c.hierarchy, c.category_id;");
		List<List<Category>> categoryListByHierarchy = template.query(sql.toString(), RESULT_SET_EXTRACTOR_FOR_COUNTITEM);
		if(categoryListByHierarchy.size() == 0) {
			return null;
		}
		return categoryListByHierarchy;
	}
	
	/**
	 * 指定カテゴリーIDを元に上位層（親）カテゴリーを取得する.
	 * 
	 * @param categoryId
	 * @return
	 */
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
