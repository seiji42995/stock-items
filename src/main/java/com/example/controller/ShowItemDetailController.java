package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ItemService;

/**
 * 商品詳細を表すControllerクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Controller
@RequestMapping("/item")
public class ShowItemDetailController {
	
	@Autowired
	private ItemService itemService;

	/**
	 * 商品IDに合致する商品情報を取得し、詳細画面に出力する.
	 * 
	 * @param model リクエストパラメーター
	 * @param 商品ID
	 * @return 商品詳細画面に遷移
	 */
	@GetMapping("/show-detail")
	public String showDetail(Model model, Integer itemId, Integer page) {
		Item item = itemService.findByItemId(itemId);
		model.addAttribute("page", page);
		model.addAttribute("item", item);
		return "detail";
	}
}
