package com.example.form;

/**
 * 登録店舗情報を受け取るためのフォームクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class RegisterShopForm {

	/** 店舗名 */
	private String name;
	/** 郵便番号 */
	private String zipcode;
	/** 店舗住所 */
	private String address;
	/** 店舗電話番号 */
	private String telephone;
	/** オーナー性 */
	private String orneaLastName;
	/** オーナー名 */
	private String ornerFirstName;
	/** 店舗概要 */
	private String description;

	@Override
	public String toString() {
		return "RegisterShopForm [name=" + name + ", zipcode=" + zipcode + ", address=" + address + ", telephone="
				+ telephone + ", orneaLastName=" + orneaLastName + ", ornerFirstName=" + ornerFirstName
				+ ", description=" + description + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getOrneaLastName() {
		return orneaLastName;
	}

	public void setOrneaLastName(String orneaLastName) {
		this.orneaLastName = orneaLastName;
	}

	public String getOrnerFirstName() {
		return ornerFirstName;
	}

	public void setOrnerFirstName(String ornerFirstName) {
		this.ornerFirstName = ornerFirstName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
