package com.example.domain;

/**
 * 店舗情報を表すドメインクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class Shop {

	/** 店舗ID */
	private Integer shopId;
	/** 店舗名 */
	private String shopName;
	/** 郵便番号 */
	private String zipcode;
	/** 住所 */
	private String address;
	/** 電話番号 */
	private String telephone;
	/** オーナー氏名 */
	private String ornerName;
	/** 店舗概要 */
	private String shopDescription;

	public Shop() {
	}

	public Shop(Integer shopId, String shopName, String zipcode, String address, String telephone, String ornerName,
			String shopDescription) {
		super();
		this.shopId = shopId;
		this.shopName = shopName;
		this.zipcode = zipcode;
		this.address = address;
		this.telephone = telephone;
		this.ornerName = ornerName;
		this.shopDescription = shopDescription;
	}

	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", shopName=" + shopName + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", ornerName=" + ornerName + ", shopDescription=" + shopDescription
				+ "]";
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOrnerName() {
		return ornerName;
	}

	public void setOrnerName(String ornerName) {
		this.ornerName = ornerName;
	}

	public String getShopDescription() {
		return shopDescription;
	}

	public void setShopDescription(String shopDescription) {
		this.shopDescription = shopDescription;
	}

}
