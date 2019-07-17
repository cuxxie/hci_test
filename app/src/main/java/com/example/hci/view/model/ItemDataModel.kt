package com.example.hci.view.model

import com.example.hci.interactor.model.ApiResponse

data class ItemDataModel(
    val type:String,
    val title:String,
    val image:String,
    val child:List<ItemDataModel>?,
    val url:String)


class Mapper {
    companion object {
        fun mapToItemDataModel(apiResponse: ApiResponse):List<ItemDataModel> {
            val result = mutableListOf<ItemDataModel>()
            val productList = apiResponse.getSectionItems("products")
            val articleList = apiResponse.getSectionItems("articles")
            if(productList != null) {
                val resultProducts = mutableListOf<ItemDataModel>()
                for (item in productList.items) {
                    resultProducts.add(ItemDataModel("product",
                        item.productName?:"",
                        item.productImage?:"",
                        null,
                        item.link?:""))
                }
                result.add(ItemDataModel("product_list","","",resultProducts,""))
            }
            if(articleList != null){
                for (item in articleList.items) {
                    result.add(ItemDataModel("article",
                        item.articleTitle?:"",
                        item.articleImage?:"",
                        null,
                        item.link?:""))
                }
            }
            return result
        }
    }
}