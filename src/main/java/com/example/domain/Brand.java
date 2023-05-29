package com.example.domain;

/**
 * ブランド情報を表すドメインクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class Brand {

	/** ブランドID */
	private Integer brandId;
	/** ブランド名 */
	private String brandName;

	public Brand() {
	}

	public Brand(Integer brandId, String brandName) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
	}

	@Override
	public String toString() {
		return "Brand [brandId=" + brandId + ", brandName=" + brandName + "]";
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
