package com.example.domain;

/**
 * カテゴリー情報を表すドメインクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class Category {

	/** カテゴリーID */
	private Integer categoryId;
	/**
	 * 親カテゴリーID 最上階層のカテゴリーはnull
	 */
	private Integer parentId;
	/** カテゴリー名 */
	private String categoryName;
	/**
	 * カテゴリーパス 最下層のカテゴリーのみ持つ情報
	 */
	private String nameAll;
	/** 階層情報 */
	private Integer hierarchy;
	/** 商品数 */
	private Integer itemNum;

	public Category() {
	}

	public Category(Integer categoryId, Integer parentId, String categoryName, String nameAll, Integer hierarchy,
			Integer itemNum) {
		super();
		this.categoryId = categoryId;
		this.parentId = parentId;
		this.categoryName = categoryName;
		this.nameAll = nameAll;
		this.hierarchy = hierarchy;
		this.itemNum = itemNum;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	public Integer getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Integer hierarchy) {
		this.hierarchy = hierarchy;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

}
