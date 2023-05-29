	package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Brand;
import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.AddItemForm;
import com.example.form.EditItemForm;
import com.example.form.ItemSearchForm;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private BrandRepository brandRepository;

	private final static String TABLE_NAME_ITEM = "items";
	private final static String TABLE_NAME_BRAND = "brands";
	private final static String TABLE_NAME_TREEPATH = "treepaths";
	private final static String TABLE_NAME_CATEGORY = "categories";
	private final static int LIMITCOUNT = 100;
	private final static int LIMIT_MAX = 500;

	private final static ResultSetExtractor<List<Item>> RESULT_SET_EXTRACTOR = (rs) -> {

		List<Item> itemList = new ArrayList<>();
		List<Category> categoryList = null;
		int count = 0;
		int previousItemId = 0;
		int nowItemId = 0;

		while (rs.next() && count < LIMITCOUNT) {
			nowItemId = rs.getInt("i_item_id");
			if(nowItemId != previousItemId) {
				count++;
				Item item = new Item();
				item.setItemId(rs.getInt("i_item_id"));
				item.setTrainId(rs.getInt("i_train_id"));
				item.setName(rs.getString("i_name"));
				item.setConditionId(rs.getInt("i_condition_id"));
				item.setShippingId(rs.getInt("i_shipping_id"));
				item.setPrice(rs.getDouble("i_price"));
				item.setDescription(rs.getString("i_description"));
				item.setShopId(rs.getInt("i_shop_id"));
				
				// Categoryの空リストを作成
				categoryList = new ArrayList<>();
				Category category = new Category();
				category.setCategoryId(rs.getInt("i_category_id"));
				categoryList.add(category);
				item.setCategoryList(categoryList);
				
				Brand brand = new Brand();
				brand.setBrandId(rs.getInt("b_brand_id"));
				brand.setBrandName(rs.getString("b_brand_name"));
				item.setBrand(brand);
				itemList.add(item);				
			}
			previousItemId = nowItemId;
		}

		return itemList;
	};

	/**
	 * items, treepaths, brands, categoriesテーブルを結合して、全件検索.
	 * 
	 * @return 結合した結果、取得できたItemドメインが詰まったリスト
	 */
	public List<Item> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append(" ORDER BY i.item_id LIMIT " + LIMIT_MAX + ";");
		List<Item> itemList = template.query(sql.toString(), RESULT_SET_EXTRACTOR);
		return itemList;
	}

	/**
	 * 次の商品情報を取得する.
	 * 
	 * @param itemId
	 * @return 商品リスト
	 */
	public List<Item> getNextItemList(Integer offset) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append(" ORDER BY i.item_id LIMIT " + LIMIT_MAX + " OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset);
		List<Item> itemList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		return itemList;
	}

	/**
	 * 商品情報を検索する（商品名）.
	 * 
	 * @param 検索フォームの入力値
	 * @param ページネーション用のオフセット値
	 * @return 検索の結果、該当した商品情報リスト。なかった場合はNullを返す。
	 */
	public List<Item> findByItemName(ItemSearchForm form, Integer offset) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name, ");
		sql.append("c.category_id AS c_category_id, c.parent_id AS c_parent_id, ");
		sql.append("c.category_name AS c_category_name, c.name_all AS c_name_all ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON i.category_id = c.category_id WHERE i.name ILIKE :name");
		sql.append(" ORDER BY i.item_id LIMIT " + LIMIT_MAX + " OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + form.getItemName() + "%")
				.addValue("offset", offset);
		List<Item> itemList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (itemList.size() == 0) {
			return null;
		}
		return itemList;
	}


	/**
	 * 商品情報を検索する（ブランド名のみ）.
	 * 
	 * @param 検索フォームに入力された値
	 * @param ページネーションに使用するオフセット値
	 * @return 検索の結果、該当した商品情報のリスト。なかった場合はNullを返す。
	 */
	public List<Item> findByBrandName(ItemSearchForm form, Integer offset) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append(" WHERE b.brand_name ILIKE :brandName ORDER BY i.item_id LIMIT " + LIMIT_MAX + " OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("brandName", "%" + form.getBrandName() + "%")
				.addValue("offset", offset);
		List<Item> itemList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (itemList.size() == 0) {
			return null;
		}
		return itemList;
	}

	/**
	 * 商品情報を検索する（カテゴリーのみ）.
	 * 
	 * @param 検索フォームに入力された値
	 * @param ページネーションに使用するoffset値
	 * @return 検索の結果、該当した商品情報のリスト。なかった場合はNullを返す。
	 */
	public List<Item> findByCategoryId(ItemSearchForm form, Integer offset) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("t.ancestor_id AS t_ancestor_id, t.descendant_id AS t_descendant_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name, ");
		sql.append("c.category_id AS c_category_id, c.parent_id AS c_parent_id, ");
		sql.append("c.category_name AS c_category_name, c.name_all AS c_name_all ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON c.category_id = t.ancestor_id ");
		sql.append("WHERE t.ancestor_id = :categoryId ");
		sql.append("ORDER BY i.item_id LIMIT " + LIMIT_MAX + " OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1))
				.addValue("offset", offset);
		List<Item> itemList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (itemList.size() == 0) {
			return null;
		}
		return itemList;
	}
	
	/**
	 * 商品情報を検索する（商品名、カテゴリー）.
	 * 
	 * @param 検索入力欄の入力値が詰まったフォームクラス
	 * @param ページネーションの基準となるoffset値
	 * @return 検索の結果、該当する商品情報リスト
	 */
	public List<Item> findByItemNameAndCategoryId(ItemSearchForm form, Integer offset){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("t.ancestor_id AS t_ancestor_id, t.descendant_id AS t_descendant_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name, ");
		sql.append("c.category_id AS c_category_id, c.parent_id AS c_parent_id, ");
		sql.append("c.category_name AS c_category_name, c.name_all AS c_name_all ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON c.category_id = t.ancestor_id ");
		sql.append("WHERE ((i.name ILIKE :name AND c.name_all IS NOT NULL) AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND c.name_all IS NULL)) ");
		sql.append("OR (t.ancestor_id = :categoryId AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND c.name_all IS NOT NULL))");
		sql.append("AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId) ");
		sql.append("ORDER BY i.item_id LIMIT " + LIMIT_MAX + " OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%" + form.getItemName() + "%")
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1))
				.addValue("offset", offset);
		List<Item> itemList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (itemList.size() == 0) {
			return null;
		}
		return itemList;
	}
	
	/**
	 * 商品情報を検索する（カテゴリー、ブランド名）.
	 * 
	 * @param 検索入力欄に記入された値
	 * @param ページネーションの基準となるoffset値
	 * @return 検索の結果、該当する商品情報リスト
	 */
	public List<Item> findByCategoryIdAndBrandName(ItemSearchForm form, Integer offset){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("t.ancestor_id AS t_ancestor_id, t.descendant_id AS t_descendant_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name, ");
		sql.append("c.category_id AS c_category_id, c.parent_id AS c_parent_id, ");
		sql.append("c.category_name AS c_category_name, c.name_all AS c_name_all ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON c.category_id = t.ancestor_id ");
		sql.append("WHERE (t.ancestor_id = :categoryId OR (b.brand_name ILIKE :brandName AND c.name_all IS NOT NULL)) ");
		sql.append("AND NOT(t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName) ");
		sql.append("ORDER BY i.item_id LIMIT " + LIMIT_MAX + " OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1))
				.addValue("brandName", "%" + form.getBrandName() + "%")
				.addValue("offset", offset);
		List<Item> itemList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (itemList.size() == 0) {
			return null;
		}
		return itemList;
	}
	
	/**
	 * 商品情報を検索する（商品名、ブランド名）.
	 * 
	 * @param 検索フォーム
	 * @param ページネーション時に必要となるoffset値
	 * @return 検索値で合致した商品情報リスト
	 */
	public List<Item> findByItemNameAndBrandName(ItemSearchForm form, Integer offset){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("WHERE i.name ILIKE :name OR b.brand_name ILIKE :brandName ");
		sql.append("ORDER BY i.item_id LIMIT " + LIMIT_MAX + " OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%" + form.getItemName() + "%")
				.addValue("brandName", "%" + form.getBrandName() + "%")
				.addValue("offset", offset);
		List<Item> itemList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (itemList.size() == 0) {
			return null;
		}
		return itemList;
	}
	
	/**
	 * 商品情報を検索する（商品名、カテゴリー、ブランド名）.
	 * 
	 * @param 検索入力フォーム
	 * @param ページネーションに活用するoffset値
	 * @return 検索の結果、該当する商品情報リスト
	 */
	public List<Item> findBySearchFormAll(ItemSearchForm form, Integer offset){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("t.ancestor_id AS t_ancestor_id, t.descendant_id AS t_descendant_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name, ");
		sql.append("c.category_id AS c_category_id, c.parent_id AS c_parent_id, ");
		sql.append("c.category_name AS c_category_name, c.name_all AS c_name_all ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON c.category_id = t.ancestor_id ");
		sql.append("WHERE ((i.name ILIKE :name AND c.name_all IS NOT NULL) AND NOT(t.ancestor_id = :categoryId OR b.brand_name ILIKE :brandName)) ");
		sql.append("OR (t.ancestor_id = :categoryId AND NOT((i.name ILIKE :name OR b.brand_name ILIKE :brandName) AND c.name_all IS NULL)) ");
		sql.append("OR ((b.brand_name ILIKE :brandName AND c.name_all IS NOT NULL) AND NOT(i.name ILIKE :name OR t.ancestor_id = :categoryId)) ");
		sql.append("OR ((i.name ILIKE :name AND t.ancestor_id = :categoryId) AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName)) ");
		sql.append("OR ((t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName AND c.name_all IS NOT NULL) AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName)) ");
		sql.append("OR (((i.name ILIKE :name AND b.brand_name ILIKE :brandName) AND c.name_all IS NOT NULL) AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName)) ");
		sql.append("OR (i.name ILIKE :name AND t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName) ");
		sql.append("ORDER BY i.item_id LIMIT " + LIMIT_MAX + " OFFSET :offset;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%" + form.getItemName() + "%")
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1))
				.addValue("brandName", "%" + form.getBrandName() + "%")
				.addValue("offset", offset);
		List<Item> itemList = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (itemList.size() == 0) {
			return null;
		}
		return itemList;
	}
	

	/**
	 * 商品情報を追加する.
	 * 
	 * @param 追加対象となる商品情報のフォームクラス
	 */
	public void addItem(AddItemForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO " + TABLE_NAME_ITEM + "(train_id, name, condition_id, category_id, brand_id, ");
		sql.append(
				"price, description, shipping_id, shop_id) VALUES(:trainId, :name, :conditionId, :categoryId, :brandId, ");
		sql.append(":price, :description, :shippingId, :shopId);");

		// ブランドIDを検索
		Brand brand = brandRepository.findByBrandName(form.getBrandName());
		if (brand == null) {
			brandRepository.insert(form.getBrandName());
			brand = brandRepository.findByBrandName(form.getBrandName());
		}

		// 最新のtrainIDを取得
		Integer maxTrainId = findMaxTrainId();

		SqlParameterSource param = new MapSqlParameterSource().addValue("trainId", maxTrainId + 1)
				.addValue("name", form.getName()).addValue("conditionId", form.getCondition())
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1))
				.addValue("brandId", brand.getBrandId()).addValue("price", form.getPrice())
				.addValue("description", form.getDescription()).addValue("shippingId", null).addValue("shopId", null);
		template.update(sql.toString(), param);
	}
	
	/**
	 * 商品情報を修正.
	 * 
	 * @param 修正情報が詰まったフォーム
	 * @param 修正対象の商品ID
	 */
	public void editItem(EditItemForm form, Integer itemId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE " + TABLE_NAME_ITEM + " SET name = :name, condition_id = :conditionId, ");
		sql.append("brand_id = :brandId, price = :price, description = :description ");
		sql.append("WHERE item_id = :itemId");
		
		Brand brand = brandRepository.findByBrandName(form.getBrandName());
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", form.getName())
				.addValue("conditionId", form.getCondition())
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1))
				.addValue("brandId", brand.getBrandId()).addValue("price", form.getPrice())
				.addValue("description", form.getDescription()).addValue("itemId", itemId);
		template.update(sql.toString(), param);
	}

	/**
	 * 商品IDに合致する商品情報を取得する.
	 * 
	 * @param 商品ID
	 * @return 検索の結果合致した商品情報。該当しない場合はnullを返す。
	 */
	public Item findByItemId(Integer itemId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.item_id AS i_item_id, i.train_id AS i_train_id, i.name AS i_name, ");
		sql.append("i.condition_id AS i_condition_id, i.category_id AS i_category_id, ");
		sql.append("i.brand_id AS i_brand_id, i.price AS i_price, ");
		sql.append("i.shipping_id AS i_shipping_id, i.description AS i_description, i.shop_id AS i_shop_id, ");
		sql.append("t.ancestor_id AS t_ancestor_id, t.descendant_id AS t_descendant_id, ");
		sql.append("b.brand_id AS b_brand_id, b.brand_name AS b_brand_name, ");
		sql.append("c.category_id AS c_category_id, c.parent_id AS c_parent_id, ");
		sql.append("c.category_name AS c_category_name, c.name_all AS c_name_all ");
		sql.append("FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON c.category_id = t.ancestor_id WHERE i.item_id = :itemId ORDER BY i.item_id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		List<Item> item = template.query(sql.toString(), param, RESULT_SET_EXTRACTOR);
		if (item.size() == 0) {
			return null;
		}
		return item.get(0);
	}

	/**
	 * train_idのMax値を取得する.
	 * 
	 * @return train_idのMax値
	 */
	public Integer findMaxTrainId() {
		String sql = "SELECT max(train_id) FROM " + TABLE_NAME_ITEM;
		Integer maxTrainID = jdbcTemplate.queryForObject(sql, Integer.class);
		return maxTrainID;
	}

	/**
	 * item_idのMax値を取得する.
	 * 
	 * @return item_idのMax値
	 */
	public Integer findMaxItemId() {
		String sql = "SELECT max(item_id) FROM " + TABLE_NAME_ITEM;
		Integer maxItemID = jdbcTemplate.queryForObject(sql, Integer.class);
		return maxItemID;
	}
	
	/** 
	 * レコード数取得（商品名のみ入力）.
	 * 
	 * @param  検索フォーム
	 * @return レコード数
	 */
	public Integer countItemIdBySearchItemName(ItemSearchForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(i.item_id) FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON i.category_id = c.category_id WHERE i.name ILIKE :name;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + form.getItemName() + "%");
		Integer countNum = template.queryForObject(sql.toString(), param, Integer.class);
		return countNum;
	}
	
	/**
	 * レコード数取得（カテゴリーのみ入力）.
	 * 
	 * @param 検索フォーム
	 * @return レコード数
	 */
	public Integer countItemIdBySearchCategory(ItemSearchForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(i.item_id) FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON c.category_id = t.ancestor_id WHERE t.ancestor_id = :categoryId;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1));
		Integer countNum = template.queryForObject(sql.toString(), param, Integer.class);
		return countNum;
	}
	
	/**
	 * レコード数取得（ブランド名のみ入力）.
	 * 
	 * @param 検索フォーム
	 * @return レコード数
	 */
	public Integer countItemIdBySearchBrandName(ItemSearchForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(i.item_id) FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append(" WHERE b.brand_name ILIKE :brandName;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("brandName", "%" + form.getBrandName() + "%");
		Integer countNum = template.queryForObject(sql.toString(), param, Integer.class);
		return countNum;
	}
	
	/**
	 * レコード数取得（商品名、カテゴリー入力）.
	 * 
	 * @param 検索フォーム
	 * @return レコード数
	 */
	public Integer countItemIdBySearchItemNameAndCategory(ItemSearchForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(i.item_id) AS count_item FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON c.category_id = t.ancestor_id ");
		sql.append("WHERE ((i.name ILIKE :name AND c.name_all IS NOT NULL) AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND c.name_all IS NULL)) ");
		sql.append("OR (t.ancestor_id = :categoryId AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND c.name_all IS NOT NULL))");
		sql.append("AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId);");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%" + form.getItemName() + "%")
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1 ))
				.addValue("brandName", "%" + form.getBrandName() + "%");
		Integer countNum = template.queryForObject(sql.toString(), param, Integer.class);
		return countNum;
	}
	
	/**
	 * レコード数取得（カテゴリー、ブランド名入力）.
	 * 
	 * @param 検索フォーム
	 * @return レコード数
	 */
	public Integer countItemIdBySearchCategoryAndBrandName(ItemSearchForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(i.item_id) AS count_item FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY + " AS c ON c.category_id = t.ancestor_id ");
		sql.append("WHERE (t.ancestor_id = :categoryId OR (b.brand_name ILIKE :brandName AND c.name_all IS NOT NULL)) ");
		sql.append("AND NOT(t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName);");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1 ))
				.addValue("brandName", "%" + form.getBrandName() + "%");
		Integer countNum = template.queryForObject(sql.toString(), param, Integer.class);
		return countNum;
	}
	
	/**
	 * 商品名、ブランド名で検索した時のレコード数を取得する.
	 * 
	 * @param 検索フォーム
	 * @return レコード数
	 */
	public Integer countItemIdBySearchItemNameAndBrandName(ItemSearchForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct count_item FROM ");
		sql.append("(SELECT count(i.item_id) AS count_item FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("WHERE i.name ILIKE :name OR b.brand_name ILIKE :brandName) AS count;");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%" + form.getItemName() + "%")
				.addValue("brandName", "%" + form.getBrandName() + "%");
		Integer countNum = template.queryForObject(sql.toString(), param, Integer.class);
		return countNum;
	}
	
	/**
	 * 検索フォーム全て入力した時のレコード数を取得する.
	 * 
	 * @param 検索フォーム
	 * @return レコード数
	 */
	public Integer countItemIdBySearchFormAll(ItemSearchForm form) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(i.item_id) AS count_item FROM " + TABLE_NAME_ITEM + " AS i ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_TREEPATH + " AS t ON i.category_id = t.descendant_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_BRAND + " AS b ON i.brand_id = b.brand_id ");
		sql.append("LEFT OUTER JOIN " + TABLE_NAME_CATEGORY);
		sql.append(" AS c ON c.category_id = t.ancestor_id ");
		sql.append("WHERE ((i.name ILIKE :name AND c.name_all IS NOT NULL) AND NOT(t.ancestor_id = :categoryId OR b.brand_name ILIKE :brandName)) ");
		sql.append("OR (t.ancestor_id = :categoryId AND NOT((i.name ILIKE :name OR b.brand_name ILIKE :brandName) AND c.name_all IS NULL)) ");
		sql.append("OR ((b.brand_name ILIKE :brandName AND c.name_all IS NOT NULL) AND NOT(i.name ILIKE :name OR t.ancestor_id = :categoryId)) ");
		sql.append("OR ((i.name ILIKE :name AND t.ancestor_id = :categoryId) AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName)) ");
		sql.append("OR ((t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName AND c.name_all IS NOT NULL) AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName)) ");
		sql.append("OR (((i.name ILIKE :name AND b.brand_name ILIKE :brandName) AND c.name_all IS NOT NULL) AND NOT(i.name ILIKE :name AND t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName)) ");
		sql.append("OR (i.name ILIKE :name AND t.ancestor_id = :categoryId AND b.brand_name ILIKE :brandName);");
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%" + form.getItemName() + "%")
				.addValue("categoryId", form.getCategoryList().get(form.getCategoryList().size() - 1 ))
				.addValue("brandName", "%" + form.getBrandName() + "%");
		Integer countNum = template.queryForObject(sql.toString(), param, Integer.class);
		return countNum;
	}

}
