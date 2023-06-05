package com.example.form;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EditItemForm {

	/** 商品名 */
	@NotBlank(message = "商品名を入力してください")
	private String name;
	/** 価格 */
	@NotNull(message = "価格を入力してください")
	private Double price;
	/** カテゴリーリスト */
	@NotNull(message = "カテゴリーを選択してください")
	private List<Integer> categoryList;
	/** ブランド名 */
	private String brandName;
	/** コンディション */
	@NotNull(message = "コンディションを選択してください")
	private Integer condition;
	/** 商品概要 */
	@NotBlank(message = "商品概要を入力してください")
	private String description;

	@Override
	public String toString() {
		return "EditItemForm [name=" + name + ", price=" + price + ", categoryList=" + categoryList + ", brandName="
				+ brandName + ", condition=" + condition + ", description=" + description + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
