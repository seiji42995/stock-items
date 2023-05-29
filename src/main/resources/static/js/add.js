/**
 * 商品追加画面
 */
"use strict";
// 子リストを作成する関数
function setChildrenList(childrenSelect, childrenList) {
  clearList(childrenSelect);
  let defaultOption = document.createElement("option");
  defaultOption.value = "";
  defaultOption.textContent = "カテゴリーを選択してください";
  defaultOption.hidden = true;
  childrenSelect.appendChild(defaultOption);
  for (let childrenCategory of childrenList) {
    let option = document.createElement("option");
    option.value = childrenCategory.categoryId;
    option.textContent = childrenCategory.categoryName;
    childrenSelect.appendChild(option);
  }
}

// 子カテゴリーリストを取得する関数
function getChildrenCategoryList(funcCategoryId, categoryList) {
  let childrenList = new Array();
  for (let i = 0; i < categoryList.length; i++) {
    for (let j = 0; j < categoryList[i].length; j++) {
      if (funcCategoryId == categoryList[i][j].parentId) {
        childrenList.push(categoryList[i][j]);
      }
    }
  }
  return childrenList;
}

// 親カテゴリーIDを取得する関数
function getParentCategoryId(childrenCategoryId, categoryList) {
  let parentCategoryId = 0;
  for (let i = 0; i < categoryList.length; i++) {
    for (let j = 0; j < categoryList[i].length; j++) {
      if (childrenCategoryId == categoryList[i][j].categoryId) {
        parentCategoryId = categoryList[i][j].parentId;
      }
    }
  }
  return parentCategoryId;
}

// 親カテゴリーを確認して、選択状態にする関数
function categoryCheck(parentCategory, selectedCategory) {
  for (let i = 0; i < parentCategory.length; i++) {
    if (parentCategory[i].value == selectedCategory) {
      parentCategory[i].selected = true;
    }
  }
}

// リストを消す
function clearList(selectId) {
  let options = selectId.options;
  if (options != null) {
    for (let i = options.length - 1; i >= 0; i--) {
      selectId.removeChild(options[i]);
    }
  }
}

// 自カテゴリー選択を改めて選択状態にする関数
function selectedCategoryCheck(selectedCategoryId, selectedCategory) {
  for (let i = 0; i < selectedCategory.length; i++) {
    if (selectedCategoryId == selectedCategory[i].value) {
      selectedCategory[i].selected = true;
    }
  }
}
