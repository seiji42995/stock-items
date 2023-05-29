package com.example.domain;

/**
 * スタッフ情報を表すドメインクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class Staff {

	/** スタッフID */
	private Integer staffId;
	/** スタッフ氏名 */
	private String staffName;
	/** パスワード */
	private String password;
	/** メールアドレス */
	private String mailAddress;
	/** 編集権限 */
	private Integer staffAuthority;
	/** 店舗ID */
	private Integer shopId;

	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", staffName=" + staffName + ", password=" + password + ", mailAddress="
				+ mailAddress + ", staffAuthority=" + staffAuthority + ", shopId=" + shopId + "]";
	}

	public Staff() {
	}

	public Staff(Integer staffId, String staffName, String password, String mailAddress, Integer staffAuthority,
			Integer shopId) {
		super();
		this.staffId = staffId;
		this.staffName = staffName;
		this.password = password;
		this.mailAddress = mailAddress;
		this.staffAuthority = staffAuthority;
		this.shopId = shopId;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public Integer getStaffAuthority() {
		return staffAuthority;
	}

	public void setStaffAuthority(Integer staffAuthority) {
		this.staffAuthority = staffAuthority;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

}
