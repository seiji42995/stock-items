package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.AddItemForm;
import com.example.form.EditItemForm;
import com.example.form.ItemSearchForm;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;

/**
 * 商品情報を操作するサービスクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	/*
	 * items, treepaths, brands, categoriesテーブルを結合して、全件検索.
	 * 
	 * @return 結合した結果、取得できたItemドメインが詰まったリスト
	 */
	public List<Item> findAll() {
		List<Item> itemList = itemRepository.findAll();
		for (Item item : itemList) {
			Integer categoryId = item.getCategoryList().get(0).getCategoryId();
			List<Category> categoryList = categoryRepository.findCategoryAllByCategoryId(categoryId);
			item.setCategoryList(categoryList);
		}
		return itemList;
	}

	/**
	 * 商品追加画面で入力された値をitemsテーブルに追加する.
	 * 
	 * @param 商品追加用フォーム
	 */
	public void addItem(AddItemForm form) {
		itemRepository.addItem(form);
	}
	
	public void editItem(EditItemForm form, Integer itemId) {
		itemRepository.editItem(form, itemId);
	}

	/**
	 * 商品IDに合致する商品情報を取得する.
	 * 
	 * @param 商品ID
	 * @return 検索の結果合致した商品情報。該当しない場合はnullを返す。
	 */
	public Item findByItemId(Integer itemId) {
		Item item = itemRepository.findByItemId(itemId);
		Integer categoryId = item.getCategoryList().get(0).getCategoryId();
		List<Category> categoryList = categoryRepository.findCategoryAllByCategoryId(categoryId);
		item.setCategoryList(categoryList);
		return item;
	}

	/**
	 * 次の商品30件を取得する.
	 * 
	 * @param itemId
	 * @return 商品リスト
	 */
	public List<Item> getNextItem(Integer pageNum) {
		Integer offset = 0;
		if (pageNum <= 1) {
			offset = 0;
		} else {
			offset = (pageNum - 1) * 100;
		}
		List<Item> itemList = itemRepository.getNextItemList(offset);
		for (Item item : itemList) {
			Integer categoryId = item.getCategoryList().get(0).getCategoryId();
			List<Category> categoryList = categoryRepository.findCategoryAllByCategoryId(categoryId);
			item.setCategoryList(categoryList);
		}
		return itemList;
	}

//	public List<Item> goToPage(Integer pageNum) {
//		Integer offset = 0;
//		if (pageNum <= 1) {
//			offset = 0;
//		} else {
//			offset = (pageNum - 1) * 100;
//		}
//		List<Item> itemList = itemRepository.getNextItemList(offset);
//		for (Item item : itemList) {
//			Integer categoryId = item.getCategoryList().get(0).getCategoryId();
//			List<Category> categoryList = categoryRepository.findCategoryAllByCategoryId(categoryId);
//			item.setCategoryList(categoryList);
//		}
//		return itemList;
//	}

//	/**
//	 * 現在表示されている商品の先頭より10件分前の商品リストを取得する. 一番先頭の場合、商品IDが1の場合はID1~10のリスト返す
//	 * 
//	 * @param itemId
//	 * @return
//	 */
//	public List<Item> getPreviousItem(Integer pageNum) {
//		Integer offset = (pageNum - 1) * 100;
//		List<Item> itemList = itemRepository.getNextItemList(offset);
//		for (Item item : itemList) {
//			Integer categoryId = item.getCategoryList().get(0).getCategoryId();
//			List<Category> categoryList = categoryRepository.findCategoryAllByCategoryId(categoryId);
//			item.setCategoryList(categoryList);
//		}
//		return itemList;
//	}

	/**
	 * 商品テーブルの商品ID最大値を取得する.
	 * 
	 * @return 商品ID最大値
	 */
	public Integer getMaxItemId() {
		Integer maxItemId = itemRepository.findMaxItemId();
		return maxItemId;
	}

	/**
	 * 検索フォーム欄に入力された条件にヒットする商品情報を取得する.
	 * 
	 * @param 検索フォーム（商品名・カテゴリー・ブランド名）
	 * @param ページ番号
	 * @return 検索条件に合致した商品情報リスト
	 */
	public List<Item> searchItem(ItemSearchForm form, Integer pageNum) {

		List<Item> itemList = new ArrayList<>();
		Integer offset = 0;
		if (pageNum <= 1) {
			offset = 0;
		} else {
			offset = (pageNum - 1) * 100;
		}

		if (!(form.getItemName().equals("")) && form.getCategoryList().size() == 0 && form.getBrandName().equals("")) {
			itemList = itemRepository.findByItemName(form, offset);
		} else if (form.getItemName().equals("") && form.getCategoryList().size() != 0
				&& form.getBrandName().equals("")) {
			itemList = itemRepository.findByCategoryId(form, offset);
		} else if (form.getItemName().equals("") && form.getCategoryList().size() == 0
				&& !(form.getBrandName().equals(""))) {
			itemList = itemRepository.findByBrandName(form, offset);
		} else if (!(form.getItemName().equals("")) && form.getCategoryList().size() != 0
				&& form.getBrandName().equals("")) {
			itemList = itemRepository.findByItemNameAndCategoryId(form, offset);
		} else if (form.getItemName().equals("") && form.getCategoryList().size() != 0
				&& !(form.getBrandName().equals(""))) {
			itemList = itemRepository.findByCategoryIdAndBrandName(form, offset);
		} else if (!(form.getItemName().equals("")) && form.getCategoryList().size() == 0
				&& !(form.getBrandName().equals(""))) {
			itemList = itemRepository.findByItemNameAndBrandName(form, offset);
		} else {
			itemList = itemRepository.findBySearchFormAll(form, offset);
		}

		if(itemList == null) {
			return itemList;
		}

		for (int i = itemList.size() - 1; i >= 0; i--) {
			for (int j = i - 1; j >= 0; j--) {
				if (itemList.get(i).getItemId() == itemList.get(j).getItemId()) {
					itemList.remove(j);
				}
			}
		}
		for (Item item : itemList) {
			Integer categoryId = item.getCategoryList().get(0).getCategoryId();
			List<Category> categoryList = categoryRepository.findCategoryAllByCategoryId(categoryId);
			item.setCategoryList(categoryList);
		}

		return itemList;
	}
	
	/**
	 * 商品検索フォーム内のカテゴリーリストにNullが存在する場合は該当要素を削除する.
	 * 
	 * @param 商品検索フォーム
	 * @return カテゴリーリストからNullが無くなった商品検索フォーム
	 */
	public ItemSearchForm cleanupCategory(ItemSearchForm form) {
		for (int i = form.getCategoryList().size() - 1; i >= 0; i--) {
			if (form.getCategoryList().get(i) == null) {
				form.getCategoryList().remove(i);
			}
		}
		return form;
	}

	/**
	 * 入力された検索フォームに応じた商品件数を取得する.
	 * （商品検索条件の各パターンに応じたSQLを実行する）
	 * 
	 * @param 商品検索フォーム
	 * @return 入力された検索フォームに応じた商品件数
	 */
	public Integer getMaxCount(ItemSearchForm form) {
		Integer countItem = 0;

		if (!(form.getItemName().equals("")) && form.getCategoryList().size() == 0 && form.getBrandName().equals("")) {
			countItem = itemRepository.countItemIdBySearchItemName(form);
		} else if (form.getItemName().equals("") && form.getCategoryList().size() != 0
				&& form.getBrandName().equals("")) {
			countItem = itemRepository.countItemIdBySearchCategory(form);
		} else if (form.getItemName().equals("") && form.getCategoryList().size() == 0
				&& !(form.getBrandName().equals(""))) {
			countItem = itemRepository.countItemIdBySearchBrandName(form);
		} else if (!(form.getItemName().equals("")) && form.getCategoryList().size() != 0
				&& form.getBrandName().equals("")) {
			countItem = itemRepository.countItemIdBySearchItemNameAndCategory(form);
		} else if (form.getItemName().equals("") && form.getCategoryList().size() != 0
				&& !(form.getBrandName().equals(""))) {
			countItem = itemRepository.countItemIdBySearchCategoryAndBrandName(form);
		} else if (!(form.getItemName().equals("")) && form.getCategoryList().size() == 0
				&& !(form.getBrandName().equals(""))) {
			countItem = itemRepository.countItemIdBySearchItemNameAndBrandName(form);
		} else {
			countItem = itemRepository.countItemIdBySearchFormAll(form);
		}

		return countItem;
	}
}
