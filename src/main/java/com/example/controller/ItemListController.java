package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Brand;
import com.example.domain.Category;
import com.example.domain.Item;
import com.example.domain.LoginStaff;
import com.example.form.ItemSearchForm;
import com.example.repository.BrandRepository;
import com.example.service.CategoryService;
import com.example.service.ItemService;

import jakarta.servlet.http.HttpSession;

/**
 * 商品リストを操作するコントローラークラス.
 * 
 * @author seiji_kitahara
 *
 */
@Controller
@RequestMapping("/item")
public class ItemListController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ItemService itemService;
	@Autowired
	private HttpSession session;

	/**
	 * 商品ID1~100の初回表示する.
	 * 
	 * @param model
	 * @return 商品リスト
	 */
	@GetMapping("/show-list")
	public String showList(Model model, ItemSearchForm form, @AuthenticationPrincipal LoginStaff loginStaff) {
		// 全商品情報を取得
		List<Item> itemList = itemService.findAll();
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", 1);
		// 最終商品IDを取得
		Integer maxCount = itemService.getMaxItemId();
		model.addAttribute("maxCount", maxCount);
		// ページ数を取得。10件ずつの表示としているため、10で除算
		Double pageTotal = Math.ceil(maxCount / 100.0);
		model.addAttribute("pageTotal", pageTotal);
		// カテゴリー一覧を取得
		List<List<Category>> categoryList = categoryService.findCategoryList();
		model.addAttribute("categoryList", categoryList);
		// ブランド一覧を取得
		List<Brand> brandList = brandRepository.findAll();
		model.addAttribute("brandList", brandList);
		model.addAttribute("staffName", loginStaff.getStaff().getStaffName());
		return "list";
	}

	/**
	 * 次の商品情報100件を取得する
	 * 
	 * @param page 現在のページ番号
	 * @param model リクエストパラメータ
	 * @param form　検索フォーム
	 * @param loginStaff ログインユーザー情報
	 * @return リクエストパラメータ元で表示していた商品情報から次の商品情報100件
	 */
	@GetMapping("/next-page")
	public String nextPage(Integer page, Model model, ItemSearchForm form,
			@AuthenticationPrincipal LoginStaff loginStaff) {
		ItemSearchForm searchForm = (ItemSearchForm) session.getAttribute("form");
		List<Item> itemList = null;
		Integer maxCount = 0;
		if (searchForm != null) {
			itemList = itemService.searchItem(searchForm, page + 1);
			if (itemList.size() == 0) {
				model.addAttribute("notFound", "検索の結果何も見つかりませんでした。商品ID順で最初の10件を表示します");
				return showList(model, form, loginStaff);
			}
			maxCount = itemService.getMaxCount(searchForm);
		} else {
			itemList = itemService.getNextItem(page + 1);
			maxCount = itemService.getMaxItemId();
		}
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page + 1);
		model.addAttribute("maxCount", maxCount);
		Double pageTotal = Math.ceil(maxCount / 100.0);
		model.addAttribute("pageTotal", pageTotal);
		// カテゴリー一覧を取得
		List<List<Category>> categoryList = categoryService.findCategoryList();
		model.addAttribute("categoryList", categoryList);
		// ブランド一覧を取得
		List<Brand> brandList = brandRepository.findAll();
		model.addAttribute("brandList", brandList);
		model.addAttribute("staffName", loginStaff.getStaff().getStaffName());
		return "list";
	}

	/**
	 * 前の商品情報100件を取得する.
	 * 
	 * @param page 現在のページ
	 * @param model リクエストパラメーター
	 * @param form 検索フォーム
	 * @param loginStaff ログインユーザー情報
	 * @return 前の商品情報100件リスト
	 */
	@GetMapping("/previous-page")
	public String previousPage(Integer page, Model model, ItemSearchForm form,
			@AuthenticationPrincipal LoginStaff loginStaff) {
		ItemSearchForm searchForm = (ItemSearchForm) session.getAttribute("form");
		List<Item> itemList = null;
		Integer maxCount = 0;
		if (searchForm != null) {
			itemList = itemService.searchItem(searchForm, page - 1);
			maxCount = itemService.getMaxCount(searchForm);
			if (itemList.size() == 0) {
				model.addAttribute("notFound", "検索の結果何も見つかりませんでした。商品ID順で最初の10件を表示します");
				return showList(model, form, loginStaff);
			}
		} else {
			itemList = itemService.getNextItem(page - 1);
			maxCount = itemService.getMaxItemId();
		}
		model.addAttribute("itemList", itemList);
		model.addAttribute("page", page - 1);
		model.addAttribute("maxCount", maxCount);
		Double pageTotal = Math.ceil(maxCount / 100.0);
		model.addAttribute("pageTotal", pageTotal);
		// カテゴリー一覧を取得
		List<List<Category>> categoryList = categoryService.findCategoryList();
		model.addAttribute("categoryList", categoryList);
		// ブランド一覧を取得
		List<Brand> brandList = brandRepository.findAll();
		model.addAttribute("brandList", brandList);
		model.addAttribute("staffName", loginStaff.getStaff().getStaffName());
		return "list";
	}

	/**
	 * 指定ページの商品情報100件取得する.
	 * 
	 * @param page 取得対象のページ数
	 * @param model リクエストパラメーター
	 * @param form 検索フォーム
	 * @param loginStaff ログインユーザー情報
	 * @return 取得した商品情報100件
	 */
	@GetMapping("/goto-page")
	public String gotoPage(Integer page, Model model, ItemSearchForm form, @AuthenticationPrincipal LoginStaff loginStaff) {
		ItemSearchForm searchForm = (ItemSearchForm) session.getAttribute("form");
		List<Item> itemList = null;
		Integer maxCount = 0;
		if (searchForm != null) {
			itemList = itemService.searchItem(searchForm, page);
			maxCount = itemService.getMaxCount(searchForm);
		} else {
			itemList = itemService.getNextItem(page);
			maxCount = itemService.getMaxItemId();
		}
		Double pageTotal = Math.ceil(maxCount / 100.0);
		model.addAttribute("itemList", itemList);
		model.addAttribute("maxCount", maxCount);
		model.addAttribute("pageTotal", pageTotal);
		// カテゴリー一覧を取得
		List<List<Category>> categoryList = categoryService.findCategoryList();
		model.addAttribute("categoryList", categoryList);
		// ブランド一覧を取得
		List<Brand> brandList = brandRepository.findAll();
		model.addAttribute("page", page);
		model.addAttribute("brandList", brandList);
		model.addAttribute("staffName", loginStaff.getStaff().getStaffName());
		return "list";
	}

	/**
	 * 検索フォームに該当する商品情報100件取得する.
	 * 
	 * @param model リクエストパラメーター
	 * @param form 商品検索フォーム
	 * @param loginStaff ログインユーザー情報
	 * @return 検索フォームに該当する商品情報100件リスト
	 */
	@PostMapping("/search")
	public String search(Model model, ItemSearchForm form, @AuthenticationPrincipal LoginStaff loginStaff) {
		System.out.print("フォーム確認 : ");
		System.out.println(form);
		form = itemService.cleanupCategory(form);
		// 検索フォームに何も入力せずに検索を押下した場合、商品IDで昇順表示
		if (form.getItemName().equals("") && form.getCategoryList().size() == 0 && form.getBrandName().equals("")) {
			model.addAttribute("notFound", "検索フォーム未入力のため、商品ID順で最初の100件を表示します");
			return showList(model, form, loginStaff);
		}
		Integer pageNum = 1;
		List<Item> itemList = itemService.searchItem(form, pageNum);
		if (itemList == null) {
			model.addAttribute("notFound", "検索の結果何も見つかりませんでした。商品ID順で最初の100件を表示します");
			System.out.println("ここには入っている");
			return showList(model, form, loginStaff);
		}
		model.addAttribute("itemList", itemList);
		// 最終商品IDを取得
		Integer maxCount = itemService.getMaxCount(form);
		model.addAttribute("maxCount", maxCount);
		// ページ数を取得。100件ずつの表示としているため、100で除算
		Double pageTotal = Math.ceil(maxCount / 100.0);
		model.addAttribute("pageTotal", pageTotal);
		// カテゴリー一覧を取得
		List<List<Category>> categoryList = categoryService.findCategoryList();
		model.addAttribute("categoryList", categoryList);
		// ブランド一覧を取得
		List<Brand> brandList = brandRepository.findAll();
		model.addAttribute("brandList", brandList);
		model.addAttribute("page", pageNum);
		session.setAttribute("form", form);
		model.addAttribute("staffName", loginStaff.getStaff().getStaffName());
		return "list";
	}

	/**
	 * カテゴリーのリンククリック時に該当する商品情報100件取得する.
	 * 
	 * @param categoryId カテゴリーID
	 * @param model リクエストパラメーター
	 * @param loginStaff ログインユーザー情報
	 * @return 該当する商品情報100件分取得する
	 */
	@GetMapping("/search-by-category")
	public String searchByCategory(Integer categoryId, Model model, @AuthenticationPrincipal LoginStaff loginStaff) {
		Integer page = 0;
		List<Integer> category = new ArrayList<>();
		category.add(categoryId);
		ItemSearchForm form = new ItemSearchForm();
		form.setItemName("");
		form.setCategoryList(category);
		form.setBrandName("");
		List<Item> itemList = itemService.searchItem(form, page);
		model.addAttribute("itemList", itemList);
		// 最終商品IDを取得
		Integer maxCount = itemService.getMaxCount(form);
		model.addAttribute("maxCount", maxCount);
		// ページ数を取得。100件ずつの表示としているため、100で除算
		Double pageTotal = Math.ceil(maxCount / 100.0);
		model.addAttribute("pageTotal", pageTotal);
		// カテゴリー一覧を取得
		List<List<Category>> categoryList = categoryService.findCategoryList();
		model.addAttribute("categoryList", categoryList);
		// ブランド一覧を取得
		List<Brand> brandList = brandRepository.findAll();
		model.addAttribute("brandList", brandList);
		model.addAttribute("page", page + 1);
		session.setAttribute("form", form);
		model.addAttribute("staffName", loginStaff.getStaff().getStaffName());
		return "list";
	}
	
	/**
	 * showListメソッドに遷移する.
	 * 
	 * @param model リクエストパラメーター
	 * @param form 検索フォーム
	 * @param loginStaff ログインユーザー情報
	 * @return showListメソッドに遷移する
	 */
	@GetMapping("/to-showlist")
	public String toShowlist(Model model, ItemSearchForm form, @AuthenticationPrincipal LoginStaff loginStaff) {
		session.removeAttribute("form");
		return showList(model, form, loginStaff);
	}
}
