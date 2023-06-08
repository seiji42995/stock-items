package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.form.RegisterShopForm;
import com.example.repository.ShopRepository;

/**
 * 店舗登録を行うサービスクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Service
@Transactional
public class ShopRegisterService {

	@Autowired
	private ShopRepository shopRepository;
	
	/**
	 * 店舗情報を登録する.
	 * 
	 * @param 登録対象店舗情報
	 */
	public void insertShopInfo(RegisterShopForm form) {
		shopRepository.insertShopInfo(form);
	}
}
