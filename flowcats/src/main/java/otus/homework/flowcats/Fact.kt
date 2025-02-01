package otus.homework.flowcats

import com.google.gson.annotations.SerializedName

data class Fact(
	@field:SerializedName("fact")
	val text: String,
	val length: Int
)