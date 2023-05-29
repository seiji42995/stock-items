package com.example.domain;

import java.util.List;

/**
 * 商品情報を表すドメインクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class Item {

	/** 商品ID */
	private Integer itemId;
	/** 商品管理ID */
	private Integer trainId;
	/** 商品名 */
	private String name;
	/** 状態管理ID */
	private Integer conditionId;
	/** 商品カテゴリーID */
	private List<Category> categoryList;
	/** ブランド */
	private Brand brand;
	/** 商品金額 */
	private Double price;
	/** 配送状況管理ID */
	private Integer shippingId;
	/** 商品説明 */
	private String description;
	/** 出店店舗ID */
	private Integer shopId;

	public Item() {
	}

	public Item(Integer itemId, Integer trainId, String name, Integer conditionId, List<Category> categoryList,
			Brand brand, Double price, Integer shippingId, String description, Integer shopId) {
		super();
		this.itemId = itemId;
		this.trainId = trainId;
		this.name = name;
		this.conditionId = conditionId;
		this.categoryList = categoryList;
		this.brand = brand;
		this.price = price;
		this.shippingId = shippingId;
		this.description = description;
		this.shopId = shopId;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", trainId=" + trainId + ", name=" + name + ", conditionId=" + conditionId
				+ ", categoryList=" + categoryList + ", brand=" + brand + ", price=" + price + ", shippingId="
				+ shippingId + ", description=" + description + ", shopId=" + shopId + "]";
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getTrainId() {
		return trainId;
	}

	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getConditionId() {
		return conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getShippingId() {
		return shippingId;
	}

	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

}
