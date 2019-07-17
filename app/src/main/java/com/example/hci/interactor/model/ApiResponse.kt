package com.example.hci.interactor.model
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("data")
    var data: List<Data>
) {
    fun getSectionItems(section: String): Data?{
        for(row in data){
            if(row.section == section)
                return row
        }
        return null
    }
}

data class Data(
    @SerializedName("items")
    var items: List<Item>,
    @SerializedName("section")
    var section: String,
    @SerializedName("section_title")
    var sectionTitle: String?
)

data class Item(
    @SerializedName("article_image")
    var articleImage: String?,
    @SerializedName("article_title")
    var articleTitle: String?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("product_name")
    var productName: String?,
    @SerializedName("product_image")
    var productImage: String?
)