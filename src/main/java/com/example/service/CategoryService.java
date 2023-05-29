package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

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
	
	/**
	 * 階層毎にリスト化したカテゴリーリストを取得する
	 * 
	 * @return
	 */
	public List<List<Category>> findCategoryList(){
		
		// カテゴリー階層の最大値を確認
//		Integer maxHierarchy = categoryRepository.checkMaxHierarchy();
		
		List<List<Category>> categoryList = new ArrayList<>();
		
		// カテゴリー毎にリスト化し、リストに詰める
		for(int i = 0 ; i <= 4 ; i ++) {
			List<Category> categorySmallList = new ArrayList<>();
			categorySmallList = categoryRepository.findByHierarchy(i);
			if(i == 0) {
				categorySmallList.get(i).setCategoryName("カテゴリー無し");
			}
			categoryList.add(categorySmallList);
		}
		return categoryList;
	}
	
}
