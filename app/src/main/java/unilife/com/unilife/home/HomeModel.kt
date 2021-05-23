package unilife.com.unilife.home
import java.io.Serializable



 class HomeModel : Serializable{

     var id:String=""
     var postUserId:String=""
     var caption:String=""
     var location_name:String=""
     var post_through_group:String=""
     var user_type:String=""
     var is_online:String=""
     var username:String=""
     var complete_profile:String=""
     var profile_image:String=""
     var university_school_id:String=""
     var university_school_email:String=""
     var profile_status:String=""
     var user_university_school_name:String=""
     var UserPostLikesStatus:String="0"
     var uploaded_at : String = ""
     var post_user_id : String = ""
     var admin_id : String = ""
     var group_name = ""
     var type = ""
     var group_image = ""
     var userlikedPost : Boolean = false




    var postAttachmentsList:ArrayList<PostAttachments>?=null
    class PostAttachments(var id:String,var post_id:String,var attachment_type:String,var attachment:String,var thumbnail:String)

/* todo @24 OCT START */

    var postCommentsList:ArrayList<PostComments>?=null
    var postLikesList:ArrayList<PostLikes>?=null
    var userPostLikesList:ArrayList<UserPostLikes>?=null
    var totalPostLikesList:ArrayList<TotalPostLikes>?=null
    var postTagGroupList:ArrayList<PostTagGroup>?=null
    var postTagUserList:ArrayList<PostTagUser>?=null


    class PostComments(var id:String,var post_id:String,var user_id:String,var comment:String) :Serializable
    class PostLikes(var id:String,var post_comment_id:String,var user_id:String,var type:String) :Serializable
    class UserPostLikes(var id:String,var post_comment_id:String,var user_id:String,var type:String) :Serializable
    class TotalPostLikes(
        var id: String,
        var post_comment_id: String,
        var user_id: String,
        var type: String
    ) :Serializable
    class PostTagGroup(var id:String,var group_id:String,var post_id:String,var group_name:String,var group_image:String) :Serializable
    class PostTagUser(var id:String,var user_id:String,var post_id:String,var username:String,var profile_image:String) :Serializable

    /* todo @24 OCT END */


     var categoriesBrandList:ArrayList<CategoriesBrandModel>?=null
     var brandOffersList: ArrayList<BrandOffers>?= null
     var blogOffersList: ArrayList<BlogModel>?= null

     class BrandOffers(
         var id: String,
         var brand_id: String,
         var title: String,
         var start_date: String,
         var exp_date: String,
         var discounttype: String,
         var discount_percent: String,
         var discountcode: String,
         var description: String,
         var termcondition: String,
         var image: String,
         var slider: String
     ) : Serializable

     class CategoriesBrandModel(var offerid : String, var offername : String, var offerimage : String, var offerstatus: String,
                                var id : String,var categories_id : String,var brand_name : String,var image : String,
                                var type : String,var description : String, var status: String , var brandsOffersList : ArrayList<BrandOffers> ):Serializable

     class BlogModel(var categories_id: String = "",
                     var category_name : String = "",
                     var blog_id : String = "",
                     var blog_name : String = "",
                     var blog_image : String = ""
                     )

}
