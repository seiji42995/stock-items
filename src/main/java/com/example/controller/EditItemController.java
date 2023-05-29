package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Brand;
import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.EditItemForm;
import com.example.repository.BrandRepository;
import com.example.service.CategoryService;
import com.example.service.ItemService;

import jakarta.servlet.http.HttpSession;

/**
 * 商品詳細の編集するControllerクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Controller
@RequestMapping("/item")
public class EditItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ShowItemDetailController showitemDetailController;
	@Autowired
	private HttpSession httpSession;

	@GetMapping("/edit")
	public String edit(Integer itemId, Integer page, EditItemForm form, Model model) {
		Item item = itemService.findByItemId(itemId);
		model.addAttribute("item", item);
		System.out.println(page);
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setBrandName(item.getBrand().getBrandName());
		form.setCondition(item.getConditionId());
		form.setDescription(item.getDescription());
		List<List<Category>> categoryList = categoryService.findCategoryList();
		categoryList.get(0).remove(0);
		List<Brand> brandList = brandRepository.findAll();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("brandList", brandList);
		model.addAttribute("page", page);
		model.addAttribute("itemId", itemId);
		return "edit";
	}

	@PostMapping("/edit-item")
	public String editItem(@Validated EditItemForm form, BindingResult result,Integer itemId, Integer page, Model model) {
		if(result.hasErrors()) {
			return edit(itemId, page, form, model);
		}
		itemService.editItem(form, itemId);
		httpSession.setAttribute("sessionItemId", itemId);
		httpSession.setAttribute("sessionPage", page);
		return "redirect:/item/to-show_detail";
	}

	@GetMapping("/to-show_detail")
	public String toShowDetail(Model model) {
		Integer itemId = (Integer)httpSession.getAttribute("sessionItemId");
		Integer page = (Integer)httpSession.getAttribute("sessionPage");
		return showitemDetailController.showDetail(model, itemId, page);
	}
}
