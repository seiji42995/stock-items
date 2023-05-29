/**
 * 商品一覧画面
 */
"use strict";
function firtstCategorySelect(category1, category2, category3, categoryList) {
  // カテゴリー1のoptionリストを取得
  let category1List = category1.options;
  // カテゴリー3のIDを取得
  let category3Id = item.categoryList[item.categoryList.length - 1].categoryId;
  // カテゴリー2,1のカテゴリーIDを取得
  let category2Id = getParentCategoryId(category3Id, categoryList);
  let category1Id = getParentCategoryId(category2Id, categoryList);
  // カテゴリー1を選択済みにする
  categoryCheck(category1List, category1Id);
  // カテゴリー2のリストを作り直して、セットし直す
  let category2List = getChildrenCategoryList(category1Id, categoryList);
  setChildrenList(category2, category2List);
  // カテゴリー2,を選択済みにする
  categoryCheck(category2, category2Id);

  // カテゴリー3のリストを取得
  let category3List = getChildrenCategoryList(category2Id, categoryList);
  setChildrenList(category3, category3List);
  selectedCategoryCheck(category3Id, category3);
}

function firstCategorySelectForOver4() {}

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

// リストを消す
function clearList(selectId) {
  let options = selectId.options;
  if (options != null) {
    for (let i = options.length - 1; i >= 0; i--) {
      selectId.removeChild(options[i]);
    }
  }
}

// 子カテゴリーリストを取得する関数
function getChildrenCategoryList(parentCategoryId, categoryList) {
  let childrenList = new Array();
  for (let i = 0; i < categoryList.length; i++) {
    for (let j = 0; j < categoryList[i].length; j++) {
      if (parentCategoryId == categoryList[i][j].parentId) {
        childrenList.push(categoryList[i][j]);
      }
    }
  }
  return childrenList;
}

// 親カテゴリーIDを取得する関数
function getParentCategoryId(childrenCategoryId) {
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

// 自カテゴリー選択を改めて選択状態にする関数
function selectedCategoryCheck(selectedCategoryId, selectedCategory) {
  for (let i = 0; i < selectedCategory.length; i++) {
    if (selectedCategoryId == selectedCategory[i].value) {
      selectedCategory[i].selected = true;
    }
  }
}

// カテゴリー階層が４以上のものを削除する関数
function removeSelect(selectCount) {
  let selectId = "category" + (selectCount + 1);
  let element = document.getElementById(selectId);
  element.remove();
}

// selectタグを作成する関数
function createSelectForHierarchy(childrenCategoryList, hierarchyNum) {
  let jsSelectBox = document.querySelector(".js-selectbox");
  let select = document.createElement("select");
  let categoryName = "category" + (hierarchyNum + 1);
  select.setAttribute("name", "categoryList");
  select.setAttribute("id", categoryName);
  select.classList.add("form-control");
  let defaultOption = document.createElement("option");
  defaultOption.value = "";
  defaultOption.textContent = "カテゴリーを選択してください";
  defaultOption.hidden = true;
  select.appendChild(defaultOption);
  let grandChildCategoryList = new Array();
  for (let i = 0; i < childrenCategoryList.length; i++) {
    let option = document.createElement("option");
    option.value = childrenCategoryList[i].categoryId;
    option.textContent = childrenCategoryList[i].categoryName;
    select.appendChild(option);
    jsSelectBox.appendChild(select);
    grandChildCategoryList.push(
      getChildrenCategoryList(childrenCategoryList[i].categoryId, categoryList)
    );
  }
  return grandChildCategoryList;
  // if(grandChildCategoryList.length !== 0){
  //   let selectId = select.getAttribute('id');
  //   let hierarchyNum = parseInt(selectId.substring(8));
  //   createSelectForHierarchy(grandChildCategoryList, hierarchyNum);
  // }
}

function createSelectForGrandChild(grandChildList, hierarchyNum) {
  let jsSelectBox = document.querySelector(".js-selectbox");
  let select = document.createElement("select");
  let categoryName = "category" + (hierarchyNum + 1);
  select.setAttribute("name", "categoryList");
  select.setAttribute("id", categoryName);
  select.classList.add("form-control");
  let defaultOption = document.createElement("option");
  defaultOption.value = "";
  defaultOption.textContent = "カテゴリーを選択してください";
  defaultOption.hidden = true;
  select.appendChild(defaultOption);
  for (let i = 0; i < grandChildList.length; i++) {
    for (let j = 0; j < grandChildList[i].length; j++) {
      let option = document.createElement("option");
      option.value = grandChildList[i][j].categoryId;
      option.textContent = grandChildList[i][j].categoryName;
      select.appendChild(option);
      jsSelectBox.appendChild(select);
    }
  }
}
