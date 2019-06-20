package com.azp.foodapp.models;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem{

	@SerializedName("strCategory")
	private String strCategory;

	@SerializedName("strCategoryThumb")
	private String strCategoryThumb;

	@SerializedName("strCategoryDescription")
	private String strCategoryDescription;

	@SerializedName("idCategory")
	private String idCategory;

	public void setStrCategory(String strCategory){
		this.strCategory = strCategory;
	}

	public String getStrCategory(){
		return strCategory;
	}

	public void setStrCategoryDescription(String strCategoryDescription){
		this.strCategoryDescription = strCategoryDescription;
	}

	public String getStrCategoryDescription(){
		return strCategoryDescription;
	}

	public void setIdCategory(String idCategory){
		this.idCategory = idCategory;
	}

	public String getIdCategory(){
		return idCategory;
	}

	public void setStrCategoryThumb(String strCategoryThumb){
		this.strCategoryThumb = strCategoryThumb;
	}

	public String getStrCategoryThumb(){
		return strCategoryThumb;
	}
}