package unilife.com.unilife.profile

import java.util.ArrayList

class CompleteProfileModel() {

    var qustion: String=""
    var qustion_type: String=""
    var id: String=""
    var intrest_name:String=""
    var hobby_name:String=""
    var categories_name:String=""
    var categories_id:String=""
    var hobby_id:String=""
    var intrest_id:String=""
    var categoryList: ArrayList<CategoriesProfileModel> = ArrayList()




    // for data saving
    var ques_answer: String = ""
    var category_list: ArrayList<String> = ArrayList()
    var hobby_list: ArrayList<String> = ArrayList()
    var interest_list: ArrayList<String> = ArrayList()


}
