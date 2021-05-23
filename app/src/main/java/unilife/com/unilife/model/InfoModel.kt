package unilife.com.unilife.model

class InfoModel{

     var name: String=""
     var image:String=""
     var id:String=""
     var friend_id:String=""
     var title: String? = ""
     var isSelected: Boolean = false

     fun getSelecteds(): Boolean {
          return isSelected
     }

     fun setSelecteds(selected: Boolean) {
          isSelected = selected
     }

}
