package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.form.RegisterStaffInfoForm;
import com.example.repository.StaffRepository;

/**
 * スタッフ登録画面で入力された値を操作するサービスクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Service
@Transactional
public class RegisterStaffInfoService {

	@Autowired
	private StaffRepository staffRepository;
	
	/**
	 * スタッフ登録情報をstaffsテーブルにinsertする.
	 * 
	 * @param スタッフ登録画面で入力されたフォーム情報
	 */
	public void insertStaffInfo(RegisterStaffInfoForm form) {
		staffRepository.insertStaff(form);
	}
}
