package com.ardine.fruturity.data.response

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fruits")
data class FruitResponse(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("isBookmark")
	val isBookmark: Boolean,

	@field:SerializedName("ripeness")
	val ripeness: String,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("notes")
	val notes: String
)