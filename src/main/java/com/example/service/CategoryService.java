package com.example.service;

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
		List<List<Category>> categoryList = categoryRepository.findByHierarchy();
		categoryList.get(0).get(0).setCategoryName("カテゴリー無し");
		return categoryList;
	}
}
