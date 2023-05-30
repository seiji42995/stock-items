package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.form.ItemSearchForm;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;

/**
 * カテゴリー情報を操作するサービスクラス.
 * 
 * @author seiji_kitahara
 *
 */
@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 階層毎にリスト化したカテゴリーリストを取得する
	 * 
	 * @return
	 */
	public List<List<Category>> findCategoryList(){
		
		// カテゴリー階層の最大値を確認
//		Integer maxHierarchy = categoryRepository.checkMaxHierarchy();
		
		List<List<Category>> categoryList = categoryRepository.findByHierarchy();
		categoryList.get(0).get(0).setCategoryName("カテゴリー無し");
		
		// カテゴリー毎にリスト化し、リストに詰める
//		for(int i = 0 ; i <= 4 ; i ++) {
//			List<Category> categorySmallList = new ArrayList<>();
//			categorySmallList = categoryRepository.findByHierarchy(i);
//			if(i == 0) {
//				categorySmallList.get(i).setCategoryName("カテゴリー無し");
//			}
//			categorySmallList = getItemNumByCategory(categorySmallList);
//			categoryList.add(categorySmallList);
//		}
		return categoryList;
	}
	
	public List<Category> getItemNumByCategory(List<Category> categoryList){
		ItemSearchForm form = new ItemSearchForm();
		List<Integer> category = new ArrayList<>();
		for(int i = 0 ; i < categoryList.size() ; i++) {
			category.add(categoryList.get(i).getCategoryId());
			form.setCategoryList(category);
			Integer countItem = itemRepository.countItemIdBySearchCategory(form);
			categoryList.get(i).setItemNum(countItem);
		}
		return categoryList;
	}
	
}
