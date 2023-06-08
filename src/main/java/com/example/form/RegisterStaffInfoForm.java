package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterStaffInfoForm {

	/** 名 */
	@NotBlank(message = "必須入力項目です")
	private String firstName;
	/** 性 */
	@NotBlank(message = "必須入力項目です")
	private String lastName;
	/** メールアドレス */
	@NotBlank(message = "必須入力項目です")
	private String email;
	/** パスワード */
	@NotBlank(message = "必須入力項目です")
	@Size(min = 8, max = 16, message="8字以上、16文字以内で設定してください")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$", message="数字、半角英字（小文字・大文字）をそれぞれ1文字以上ずつ含めたパスワードにしてください")
	private String password;
	/** 確認用パスワード */
	@NotBlank(message = "確認用パスワードも入力してください")
	private String confirmationPassword;
	/** 編集権限 */
	@NotNull(message = "権限設定を選択してください")
	private Integer authority;
	/** 店舗ID */
	@NotBlank(message = "店舗を選択してください")
	private String shopName;

	@Override
	public String toString() {
		return "RegisterStaffInfoForm [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", confirmationPassword=" + confirmationPassword + ", authority="
				+ authority + ", shopName=" + shopName + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
