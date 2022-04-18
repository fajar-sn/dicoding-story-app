package com.bangkit.intermediate.dicodingstoryapp.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryListResponse(

	@field:SerializedName("listStory")
	val story: List<Story>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
) : Parcelable

@Parcelize
data class Story(
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String
) : Parcelable
