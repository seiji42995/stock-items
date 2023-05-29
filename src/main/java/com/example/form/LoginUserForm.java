package com.example.form;

import jakarta.validation.constraints.NotBlank;

/**
 * ログイン時の情報を表すフォームクラス.
 * 
 * @author seiji_kitahara
 *
 */
public class LoginUserForm {

	/** メールアドレス */
	@NotBlank(message = "値が入力されていません")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message = "値が入力されていません")
	private String password;

	@Override
	public String toString() {
		return "LoginUserForm [mailAddress=" + mailAddress + ", password=" + password + "]";
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
