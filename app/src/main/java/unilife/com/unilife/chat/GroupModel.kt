package unilife.com.unilife.chat

import java.io.Serializable


class GroupModel : Serializable{

    var id : String = ""
    var user_id : String = ""
    var group_id : String = ""
    var group_admin : String = ""

    var group_name : String = ""
    var group_image : String = ""
    var group_members_count : String = ""


    var membersList:ArrayList<GroupModel.MembersModel>?=null


    class MembersModel(var memId:String,var memName:String,var memImg:String) : Serializable

}
