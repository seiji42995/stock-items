package com.example.form;

import java.util.List;

/**
 * 商品検索用のフォームクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class ItemSearchForm {

	/** 商品名 */
	private String itemName;
	/** カテゴリーリスト */
	private List<Integer> categoryList;
	/** ブランド名 */
	private String brandName;

	@Override
	public String toString() {
		return "ItemSearchForm [itemName=" + itemName + ", categoryList=" + categoryList + ", brandName=" + brandName
				+ "]";
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public List<Integer> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Integer> categoryList) {
		this.categoryList = categoryList;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
