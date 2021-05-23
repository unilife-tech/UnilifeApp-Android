package unilife.com.unilife.retrofit

import java.text.SimpleDateFormat

object WebUrls {

    //    val BASE_URL = "http://15.206.103.14/api/wsapp/home/"
    val BASE_URL = "http://15.206.103.14:3006/"
    //    val BASE_URL = "http://13.59.5.253:5001/"
    val MSG_BASE_URL = "http://api.msg91.com/api/"
    val IMAGE_BASE_URL = "http://15.206.103.14/public/post_imgs/"
    val PROFILE_IMAGE = "http://15.206.103.14/public/profile_imgs/"
    val MYPROFILE_ICON = "http://15.206.103.14/public/unilife-icons/"
    var PROFILE_IMAGE_URL = "http://15.206.103.14/public/profile_imgs/"
    var POST_IMAGE_URL = "http://15.206.103.14/public/post_imgs/"
    var categoryImageUrl = "http://15.206.103.14/public/unilife-icons/"
    var offerImageUrl = "http://15.206.103.14/public/offer_imgs/"
    var brandImageUrl = "http://15.206.103.14/public/admin/brand/"
    var blogImageUrl = "http://15.206.103.14/public/blog_imgs/"
    val SOCKET_BASE_URL = "http://15.206.103.14:3007"
    var CHAT_MEDIA_URL = "http://15.206.103.14/public/chatdata/"

    val LOGIN = "login"
    val LOGIN_CODE = 1

    val SIGN_UP = "signup"
    val SIGN_UP_CODE = 2

    val FORGET_PASSWORD = "forget_password/"
    val FORGET_PASSWORD_CODE = 3

    val SEND_UNIVERSITY = "send_university"
    val SEND_UNIVERSITY_CODE = 4

    val SEND_PROFILE_QUSTION = "send_profile_questions/"
    val SEND_PROFILE_QUSTION_CODE = 5

    val SEND_PROFILE_ANSWER = "edit_profile"
    val SEND_PROFILE_ANSWER_CODE = 6

    val SEND_INTREST = "send_interests"
    val SEND_INTREST_CODE = 7

    val SEND_HOBBY = "send_hobbies"
    val SEND_HOBBY_CODE = 8

    val SEND_CATEGORIES = "send_categories_icon_profile"
    val SEND_CATEGORIES_CODE = 9

    val VIEW_PROFILE = "view_profile_questions_answer"
    val VIEW_PROFILE_CODE = 10

    val VIEW_FRIEND_LIST = "send_user_friends"
    val VIEW_FREIND_LIST_CODE = 11

    val ADD_POST_ATT = "add_post_attachment"
    val ADD_POST_ATT_CODE = 12

    val ADD_POST = "add_post"
    val ADD_POST_CODE = 13

    val SHOW_POST = "show_post"
    val SHOW_POST_CODE = 14

    val SEND_GROUP_LIST = "send_group_list"
    val SEND_GROUP_LIST_CODE = 15


    val VIEW_PROFILE_QUES_ANSWER = "view_profile_questions_answer_limit"
    val VIEW_PROFILE_QUES_ANSWER_CODE = 16


    val BLOG_DATA = "show_blog/"
    val BLOG_DATA_CODE = 17


    val LIKE_UNLIKE_COMMENT = "like_unlike_comment"
    val LIKE_UNLIKE_COMMENT_CODE = 18

    val VIEW_ALL_POST_LIKE = "view_all_post_like/"
    val VIEW_ALL_POST_LIKE_CODE = 19

    val VIEW_SAVED_BLOG = "user_list_saved_blog/"
    val VIEW_SAVED_BLOG_CODE = 20


    val SAVED_READ_BLOG = "user_list_read_blog/"
    val SAVED_READ_BLOG_CODE = 21


    val VIEW_LIKE_BLOG = "user_list_like_blog/"
    val VIEW_LIKE_BLOG_CODE = 22


    val VIEW_SHARED_BLOG = "user_list_shared_blog/"
    val VIEW_SHARED_BLOG_CODE = 23

    val SHOW_SINGLE_BLOG_DETAIL = "show_single_blog_detail"
    val SHOW_SINGLE_BLOG_DETAIL_CODE = 24

    val LIKE_UNLIKE_BLOG = "like_unlike_blog"
    val LIKE_UNLIKE_BLOG_CODE = 25

    val SAVE_BLOG = "user_blog_saved"
    val SAVE_BLOG_CODE = 26

    val VIEW_ALL_BLOGS_ACC_TO_CAT = "get_show_blog_categories/"
    val VIEW_ALL_BLOGS_ACC_TO_CAT_CODE = 27

    val SHOW_ALL_BLOGS = "get_blog_categories/"
    val SHOW_ALL_BLOGS_CODE = 28

    val SEARCH_BLOG = "send_search_blog"
    val SEARCH_BLOG_CODE = 29


    val READ_BLOG = "user_blog_read"
    val READ_BLOG_CODE = 30


    val SHOW_OFFERS = "show_offers/"
    val SHOW_OFFERS_CODE = 31

    val SHOW_OFFERS_ACC_CAT = "get_show_offer_categories/"
    val SHOW_OFFERS_ACC_CAT_CODE = 32

    val GET_OFFER_CAT = "get_offer_categories"
    val GET_OFFER_CAT_CODE = 33

    val GET_SINGLE_OFFER = "show_single_offer_detail"
    val GET_SINGLE_OFFER_CODE = 34

    val VIEW_ALL_RELATED = "show_single_brand_offer_detail/"
    val VIEW_ALL_RELATED_CODE = 35

    val SAVE_OFFER = "user_offer_saved"
    val SAVE_OFFER_CODE = 36


    val SEARCH_BRAND = "send_search_brand"
    val SEARCH_BRAND_CODE = 37

    val LIST_VIEW_BRANDS = "user_list_view_offer/"
    val LIST_VIEW_BRANDS_CODE = 38


    /* chat start */

    val SHOW_ONLINE_FRIENDS = "send_friends_online/"
    val SHOW_ONLINE_FRIENDS_CODE = 39

    val SHOW_FRIENDS_LISTING = "send_user_friends/"
    val SHOW_FRIENDS_LISTING_CODE = 40

    val VIEW_MORE_SUGGESTIONS = "send_view_more_suggestions_user/"
    val VIEW_MORE_SUGGESTIONS_CODE = 41

    val SEND_FRIEND_REQ = "send_friend_request"
    val SEND_FRIEND_REQ_CODE = 42

    val REJECT_FRIEND_REQ = "reject_friend_request"
    val REJECT_FRIEND_REQ_CODE = 43

    val ACCEPT_FRIEND_REQ = "accept_friend_request"
    val ACCEPT_FRIEND_REQ_CODE = 44

    val RCVD_FRIENDS_REQ = "send_Requested_friends/"
    val RCVD_FRIENDS_REQ_CODE = 45

    val GET_ALL_CHAT = "chat_data"
    val GET_ALL_CHAT_CODE = 46

    val CREATE_ROOM = "chat-room"
    val CREATE_ROOM_CODE = 47

    val LAST_MSG_LIST = "send_user_group_with_last_message/"
    val LAST_MSG_LIST_CODE = 48

    val BLOCK_USER = "block_user"
    val BLOCK_USER_CODE = 49

    /* chat end*/

    val LIST_SHARED_OFFER = "user_list_shared_offer/"
    val LIST_SHARED_OFFER_CODE = 50

    val LIST_SAVED_OFFER = "user_list_saved_offer/"
    val LIST_SAVED_OFFER_CODE = 51

    val SHARE_OFFER = "user_shared_offer"
    val SHARE_OFFER_CODE = 52

    val READ_OFFER = "user_view_offer"
    val READ_OFFER_CODE = 53

    val SHOW_SINGLE_POST_COMMENTS = "view_comments"
    val SHOW_SINGLE_POST_COMMENTS_CODE = 54

    val POST_COMMENT = "add_comment"
    val POST_COMMENT_CODE = 55

    val LIKE_UNLIKE_REPLY = "like_unlike_reply"
    val LIKE_UNLIKE_REPLY_CODE = 56

    val REPLY_COMMENT = "reply_comment"
    val REPLY_COMMENT_CODE = 57

    val POST_LIKE_UNLIKE = "like_unlike_post"
    val POST_LIKE_UNLIKE_CODE = 58

    val VIEW_COMMENT_LIKES = "view_all_post_comment_like/"
    val VIEW_COMMENT_LIKES_CODE = 59

    val VIEW_REPLY_LIKES = "view_all_post_reply_like/"
    val VIEW_REPLY_LIKES_CODE = 60

    val CANCEL_FRIEND_REQ = "cancel_friend_request"
    val CANCEL_FRIEND_REQ_CODE = 61

    val FAQ = "send_faq"
    val FAQ_CODE = 62

    val HELP = "help"
    val HELP_CODE = 63

    val FEEDBACK = "get_feedback"
    val FEEDBACK_CODE = 64

    val SHOW_BLOCKED_USERS = "send_block_list/"
    val SHOW_BLOCKED_USERS_CODE = 65

    val UNBLOCK_USER = "unblock_user"
    val UNBLOCK_USER_CODE = 66

    val VIEW_MULTIMEDIA = "view_multimedia_acc_type"
    val VIEW_MULTIMEDIA_CODE = 67

    val SHOW_OTHER_PROFILE = "show_user_detail"
    val SHOW_OTHER_PROFILE_CODE = 68

    val CREATE_GROUP = "create_group"
    val CREATE_GROUP_REQ_CODE = 69

    val UNJOIN_GROUP = "unjoin_the_group"
    val UNJOIN_GROUP_REQ_CODE = 70

    val CREATE_ROOM_FOR_GROUP = "chat-room-group"
    val CREATE_ROOM_FOR_GROUP_REQ_CODE = 71

    val GROUP_CHAT_DATA = "group_chat_data"
    val GROUP_CHAT_DATA_REQ_CODE = 72

    val GROUP_DETAILS = "group_image"
    val GROUP_DETAILS_REQ_CODE = 73

    val REDEEM_OFFER = "redeem_coupon"
    val REDEEM_OFFER_CODE = 74

    val SHOW_REDEEM_OFFER = "user_list_redeem_offer/"
    val SHOW_REDEEM_OFFER_CODE = 75

    val DELETE_ALL_CHAT = "delete_full_chat_data/"
    val DELETE_ALL_CHAT_CODE = 76

    val ARCHIVE_ALL_CHAT = "archieve_user_group/"
    val ARCHIVE_ALL_CHAT_CODE = 77

    val UNARCHIVE_ALL_CHAT = "unarchieve_user_group/"
    val UNARCHIVE_ALL_CHAT_CODE = 78

    val ARCHIVE_STATUS = "send_archieve_user/"
    val ARCHIVE_STATUS_CODE = 79

    val DELETE_MULTIMEDIA = "delete_multimedia_data"
    val DELETE_MULTIMEDIA_CODE = 80


    val HIDE_USER_STATUS = "hide_user_status"
    val HIDE_USER_STATUS_STATUS_CODE = 81

    val SEND_USER_CHAT_WALLPAPER = "send_user_chat_wallpaper/"
    val SEND_USER_CHAT_WALLPAPER_CODE = 82

    val UPLOAD_WALLPAPER = "user_chat_wallpaper"
    val UPLOAD_WALLPAPER_CODE = 83


    val CHAT_SEARCH = "search_user_group_with_last_message"
    val CHAT_SEARCH_CODE = 84

    val SEND_BACKUP_STATUS = "back_up"
    val SEND_BACKUP_STATUS_CODE = 85

    val GET_BACKUP = "get_back_up/"
    val GET_BACKUP_CODE = 86

    val OFFLINE_ONLINE = "make_user_online_offline"
    val OFFLINE_ONLINE_CODE = 87

    val USER_DEVICE = "User_device"
    val USER_DEVICE_CODE = 88

    val GET_NOTIFICATION = "get_notification/"
    val GET_NOTIFICATION_CODE = 89

    val DELETE_NOTIFICATION = "delete_notification/"
    val DELETE_NOTIFICATION_CODE = 90

    val DELETE_USER = "delete_User_device/"
    val DELETE_USER_CODE = 91

    val PAGI_POST = "new_show_post"
    val PAGI_POST_CODE = 92

    val CHANGE_PASSWORD = "change_user_password"
    val CHANGE_PASSWORD_CODE = 93

    val DELETE_POST = "delete-user-post"
    val DELETE_POST_CODE = 94

    val DELETE_ACCOUNT = "delete-user-account/"
    val DELETE_ACCOUNT_CODE = 95

    val SHARE_POST = "shareUserPost"
    val SHARE_POST_CODE = 96

    val SUBMIT_OTP = "user_otp_verify"
    val SUBMIT_OTP_CODE = 97

    val RESEND_OTP = "user_resend_otp/"
    val RESEND_OTP_CODE = 98

    val CHANGE_GROUP_ICON = "change_group_icon"
    val CHANGE_GROUP_ICON_CODE = 99

    val GROUP_DETAIL = "group_detail/"
    val GROUP_DETAIL_CODE = 100

    val ADD_PARTCIPANT = "add_participant"
    val ADD_PARTCIPANT_CODE = 101

    val REMOVE_PARTCIPANT = "remove_participant"
    val REMOVE_PARTCIPANT_CODE = 102

    val VIEW_MULTIMEDIA_CHAT = "view_multimedia"
    val VIEW_MULTIMEDIA_CHAT_CODE = 103

    val DELETE_CHAT = "delete_chat"
    val DELETE_CHAT_CODE = 104

    val ARCHIVE_CHAT = "archive_chat"
    val ARCHIVE_CHAT_CODE = 105

    val GROUP_BLOCK_LIST = "block_list"
    val GROUP_BLOCK_LIST_CODE = 106

    val SHOW_FRIEND_USER = "show_friend_user"
    val SHOW_FRIEND_USER_CODE = 107

    val SEND_MULTI_USER_FRIEND_REQUEST = "send_multiuser_friend_request"
    val SEND_MULTI_USER_FRIEND_REQUEST_CODE = 108


    /*----------------------Socket Keys---------------------------*/

    /*----------------------Socket Keys---------------------------*/

    val ROOM_ID = "room_id"
    val SENDER_ID = "sender_id"
    val RECEIVER_ID = "receiver_id"
    val GROUP_ID = "group_id"
    val MESSAGE = "message"
    val MESSAGE_TYPE = "message_type"
    val FILE_TYPE = "file_type"

    val JOIN_ROOM = "room join"
    val LISTEN_ROOM_JOIN = "room join"
    val LEAVE_ROOM = "room leave"
    val SEND_MESSAGE = "message"
    val LISTEN_SEND_MESSAGE = "message"

    val SEEN = "seen"
    val LISTEN_SEEN = "seen"

    val GROUP_SEEN_EMIT_LISTEN = "seen_chat"

    val EMIT_USER_STATUS = "sendUserOnlineStatusToServer"
    val USER_STATUS = "is_user_online"
    val STATUS_LISTENER = "serverOnlineStatusResponseToUser"
    val UPLOAD_MORE_DATA = "uploadFileMoreDataReq"
/*
  val VIEW_ALL_BLOGS_ACC_TO_CAT = "show_blog_categories"
    val VIEW_ALL_BLOGS_ACC_TO_CAT_CODE = 24
*/


    @JvmStatic
    val KEY_USERTYPE = "usertype"
    @JvmStatic
    val KEY_USERNAME = "" +
            ""
    @JvmStatic
    val KEY_USER_FIRSTNAME = "firstname"
    @JvmStatic
    val KEY_USER_LASTNAME = "lastname"
    @JvmStatic
    val KEY_USERID = "userid"
    @JvmStatic
    val KEY_EMAIL = "email"
    @JvmStatic
    val KEY_PHONE = "phone"
    @JvmStatic
    val KEY_ALTERNET_PHONE = "altrphone"
    @JvmStatic
    val KEY_PASSWORD = "password"
    @JvmStatic
    val KEY_ADDRESS = "address"
    @JvmStatic
    val KEY_PROFILE_IMAGE = "profile_image"
    @JvmStatic
    val KEY_PROFILE_STATUS = "profile_status"
    @JvmStatic
    val KEY_STATUS = "status"
    @JvmStatic
    val UNIVERSITY_SCHOOL_ID = "university_school_id"
    @JvmStatic
    val UNIVERSITY_NAME = "university_school_name"

    @JvmStatic
    val OTP_VERIFY = "no"


    var source = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var target = SimpleDateFormat("MMM dd, yyyy hh:mm a")

    val PLACE_API = "AIzaSyCDl6ZDBRDkG1KMCZkAFavadSBthuruHvE"


}
