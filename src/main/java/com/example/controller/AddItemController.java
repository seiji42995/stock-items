package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Brand;
import com.example.domain.Category;
import com.example.domain.LoginStaff;
import com.example.form.AddItemForm;
import com.example.form.ItemSearchForm;
import com.example.repository.BrandRepository;
import com.example.service.CategoryService;
import com.example.service.ItemService;

/**
 * 追加商品情報を操作するコントローラークラス.
 * 
 * @author seiji_kitahara
 *
 */
@Controller
@RequestMapping("/add")
public class AddItemController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ItemListController itemListController;
	@Autowired
	private ItemService itemService;
	
	/**
	 * 商品追加画面に遷移する.
	 * 
	 * @param form 入力欄に記入された追加商品を受け取るためのフォームクラス
	 * @param model リクエストパラメーター
	 * @return 商品追加画面に遷移
	 */
	@GetMapping("")
	public String index(AddItemForm form, Model model) {
		List<List<Category>> categoryList = categoryService.findCategoryList();
		// カテゴリーID：1は空欄のため、リストからは削除
		categoryList.get(0).remove(0);
//		System.out.println(categoryList);
		List<Brand> brandList = brandRepository.findAll();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("brandList", brandList);
		return "add";
	}
	
	@PostMapping("/insert")
	public String insertItem(@Validated AddItemForm form, BindingResult result, Model model) {
		System.out.println(form);
		if(result.hasErrors()) {
			return index(form, model);
		}
		itemService.addItem(form);
		return "redirect:/add/to-showList";
	}
	
	@GetMapping("to-showList")
	public String toShowList(Model model, ItemSearchForm form, @AuthenticationPrincipal LoginStaff loginStaff) {
		return itemListController.showList(model, form, loginStaff);
	}
	
}
