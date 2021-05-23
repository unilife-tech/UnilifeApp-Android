package unilife.com.unilife.model

import java.io.Serializable

data class UniModel(
    var id:String="",
    var isSelected: Boolean = false,
    var name: String = "",
    var domain_name: String = ""
):Serializable
