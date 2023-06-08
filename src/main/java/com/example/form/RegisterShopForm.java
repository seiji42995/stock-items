package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 登録店舗情報を受け取るためのフォームクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class RegisterShopForm {

	/** 店舗名 */
	@NotBlank(message = "必須入力項目です")
	private String name;
	/** 郵便番号 */
	@NotBlank(message = "必須入力項目です")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String zipcode;
	/** 店舗住所 */
	@NotBlank(message = "必須入力項目です")
	private String address;
	/** 店舗電話番号 */
	@NotBlank(message = "必須入力項目です")
	@Pattern(regexp = "^(070|080|090)-\\d{4}-\\d{4}$", message = "電話番号はXXX-XXXX-XXXXの形式で入力してください")
	private String telephone;
	/** オーナー性 */
	@NotBlank(message = "必須入力項目です")
	private String ornerLastName;
	/** オーナー名 */
	@NotBlank(message = "必須入力項目です")
	private String ornerFirstName;
	/** 店舗概要 */
	@NotBlank(message = "必須入力項目です")
	private String description;

	@Override
	public String toString() {
		return "RegisterShopForm [name=" + name + ", zipcode=" + zipcode + ", address=" + address + ", telephone="
				+ telephone + ", ornerLastName=" + ornerLastName + ", ornerFirstName=" + ornerFirstName
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

	public String getOrnerLastName() {
		return ornerLastName;
	}

	public void setOrnerLastName(String ornerLastName) {
		this.ornerLastName = ornerLastName;
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
