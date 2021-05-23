package unilife.com.unilife.retro;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

public class WebUrls {

    public static WebUrls ourInstance = new WebUrls();

    public static WebUrls getInstance() {
        return ourInstance;
    }
    
    @NotNull
    public static final String BASE_URL = "http://15.206.103.14:3006/";
    @NotNull
    public static final String MSG_BASE_URL = "http://api.msg91.com/api/";
    @NotNull
    public static final String IMAGE_BASE_URL = "http://15.206.103.14/public/post_imgs/";
    @NotNull
    public static final String PROFILE_IMAGE = "http://15.206.103.14/public/profile_imgs/";
    @NotNull
    public static final String MYPROFILE_ICON = "http://15.206.103.14/public/unilife-icons/";
    @NotNull
    public static final String SOCKET_BASE_URL = "http://15.206.103.14:3007";
    @NotNull
    public static final String LOGIN = "login";
    public static final int LOGIN_CODE = 1;
    @NotNull
    public static final String SIGN_UP = "signup";
    public static final int SIGN_UP_CODE = 2;
    @NotNull
    public static final String FORGET_PASSWORD = "forget_password/";
    public static final int FORGET_PASSWORD_CODE = 3;
    @NotNull
    public static final String SEND_UNIVERSITY = "send_university";
    public static final int SEND_UNIVERSITY_CODE = 4;
    @NotNull
    public static final String SEND_PROFILE_QUSTION = "send_profile_questions/";
    public static final int SEND_PROFILE_QUSTION_CODE = 5;
    @NotNull
    public static final String SEND_PROFILE_ANSWER = "edit_profile";
    public static final int SEND_PROFILE_ANSWER_CODE = 6;
    @NotNull
    public static final String SEND_INTREST = "send_interests";
    public static final int SEND_INTREST_CODE = 7;
    @NotNull
    public static final String SEND_HOBBY = "send_hobbies";
    public static final int SEND_HOBBY_CODE = 8;
    @NotNull
    public static final String SEND_CATEGORIES = "send_categories_icon_profile";
    public static final int SEND_CATEGORIES_CODE = 9;
    @NotNull
    public static final String VIEW_PROFILE = "view_profile_questions_answer";
    public static final int VIEW_PROFILE_CODE = 10;
    @NotNull
    public static final String VIEW_FRIEND_LIST = "send_user_friends";
    public static final int VIEW_FREIND_LIST_CODE = 11;
    @NotNull
    public static final String ADD_POST_ATT = "add_post_attachment";
    public static final int ADD_POST_ATT_CODE = 12;
    @NotNull
    public static final String ADD_POST = "add_post";
    public static final int ADD_POST_CODE = 13;
    @NotNull
    public static final String SHOW_POST = "show_post";
    public static final int SHOW_POST_CODE = 14;
    @NotNull
    public static final String SEND_GROUP_LIST = "send_group_list";
    public static final int SEND_GROUP_LIST_CODE = 15;
    @NotNull
    public static final String VIEW_PROFILE_QUES_ANSWER = "view_profile_questions_answer_limit";
    public static final int VIEW_PROFILE_QUES_ANSWER_CODE = 16;
    @NotNull
    public static final String BLOG_DATA = "show_blog/";
    public static final int BLOG_DATA_CODE = 17;
    @NotNull
    public static final String LIKE_UNLIKE_COMMENT = "like_unlike_comment";
    public static final int LIKE_UNLIKE_COMMENT_CODE = 18;
    @NotNull
    public static final String VIEW_ALL_POST_LIKE = "view_all_post_like/";
    public static final int VIEW_ALL_POST_LIKE_CODE = 19;
    @NotNull
    public static final String VIEW_SAVED_BLOG = "user_list_saved_blog/";
    public static final int VIEW_SAVED_BLOG_CODE = 20;
    @NotNull
    public static final String SAVED_READ_BLOG = "user_list_read_blog/";
    public static final int SAVED_READ_BLOG_CODE = 21;
    @NotNull
    public static final String VIEW_LIKE_BLOG = "user_list_like_blog/";
    public static final int VIEW_LIKE_BLOG_CODE = 22;
    @NotNull
    public static final String VIEW_SHARED_BLOG = "user_list_shared_blog/";
    public static final int VIEW_SHARED_BLOG_CODE = 23;
    @NotNull
    public static final String SHOW_SINGLE_BLOG_DETAIL = "show_single_blog_detail";
    public static final int SHOW_SINGLE_BLOG_DETAIL_CODE = 24;
    @NotNull
    public static final String LIKE_UNLIKE_BLOG = "like_unlike_blog";
    public static final int LIKE_UNLIKE_BLOG_CODE = 25;
    @NotNull
    public static final String SAVE_BLOG = "user_blog_saved";
    public static final int SAVE_BLOG_CODE = 26;
    @NotNull
    public static final String VIEW_ALL_BLOGS_ACC_TO_CAT = "get_show_blog_categories/";
    public static final int VIEW_ALL_BLOGS_ACC_TO_CAT_CODE = 27;
    @NotNull
    public static final String SHOW_ALL_BLOGS = "get_blog_categories/";
    public static final int SHOW_ALL_BLOGS_CODE = 28;
    @NotNull
    public static final String SEARCH_BLOG = "send_search_blog";
    public static final int SEARCH_BLOG_CODE = 29;
    @NotNull
    public static final String READ_BLOG = "user_blog_read";
    public static final int READ_BLOG_CODE = 30;
    @NotNull
    public static final String SHOW_OFFERS = "show_offers/";
    public static final int SHOW_OFFERS_CODE = 31;
    @NotNull
    public static final String SHOW_OFFERS_ACC_CAT = "get_show_offer_categories/";
    public static final int SHOW_OFFERS_ACC_CAT_CODE = 32;
    @NotNull
    public static final String GET_OFFER_CAT = "get_offer_categories";
    public static final int GET_OFFER_CAT_CODE = 33;
    @NotNull
    public static final String GET_SINGLE_OFFER = "show_single_offer_detail";
    public static final int GET_SINGLE_OFFER_CODE = 34;
    @NotNull
    public static final String VIEW_ALL_RELATED = "show_single_brand_offer_detail/";
    public static final int VIEW_ALL_RELATED_CODE = 35;
    @NotNull
    public static final String SAVE_OFFER = "user_offer_saved";
    public static final int SAVE_OFFER_CODE = 36;
    @NotNull
    public static final String SEARCH_BRAND = "send_search_brand";
    public static final int SEARCH_BRAND_CODE = 37;
    @NotNull
    public static final String LIST_VIEW_BRANDS = "user_list_view_offer/";
    public static final int LIST_VIEW_BRANDS_CODE = 38;
    @NotNull
    public static final String SHOW_ONLINE_FRIENDS = "send_friends_online/";
    public static final int SHOW_ONLINE_FRIENDS_CODE = 39;
    @NotNull
    public static final String SHOW_FRIENDS_LISTING = "send_user_friends/";
    public static final int SHOW_FRIENDS_LISTING_CODE = 40;
    @NotNull
    public static final String VIEW_MORE_SUGGESTIONS = "send_view_more_suggestions_user/";
    public static final int VIEW_MORE_SUGGESTIONS_CODE = 41;
    @NotNull
    public static final String SEND_FRIEND_REQ = "send_friend_request";
    public static final int SEND_FRIEND_REQ_CODE = 42;
    @NotNull
    public static final String REJECT_FRIEND_REQ = "reject_friend_request";
    public static final int REJECT_FRIEND_REQ_CODE = 43;
    @NotNull
    public static final String ACCEPT_FRIEND_REQ = "accept_friend_request";
    public static final int ACCEPT_FRIEND_REQ_CODE = 44;
    @NotNull
    public static final String RCVD_FRIENDS_REQ = "send_Requested_friends/";
    public static final int RCVD_FRIENDS_REQ_CODE = 45;
    @NotNull
    public static final String GET_ALL_CHAT = "chat_data";
    public static final int GET_ALL_CHAT_CODE = 46;
    @NotNull
    public static final String CREATE_ROOM = "chat-room";
    public static final int CREATE_ROOM_CODE = 47;
    @NotNull
    public static final String LAST_MSG_LIST = "send_user_group_with_last_message/";
    public static final int LAST_MSG_LIST_CODE = 48;
    @NotNull
    public static final String BLOCK_USER = "block_user";
    public static final int BLOCK_USER_CODE = 49;
    @NotNull
    public static final String LIST_SHARED_OFFER = "user_list_shared_offer/";
    public static final int LIST_SHARED_OFFER_CODE = 50;
    @NotNull
    public static final String LIST_SAVED_OFFER = "user_list_saved_offer/";
    public static final int LIST_SAVED_OFFER_CODE = 51;
    @NotNull
    public static final String SHARE_OFFER = "user_shared_offer";
    public static final int SHARE_OFFER_CODE = 52;
    @NotNull
    public static final String READ_OFFER = "user_view_offer";
    public static final int READ_OFFER_CODE = 53;
    @NotNull
    public static final String SHOW_SINGLE_POST_COMMENTS = "view_comments";
    public static final int SHOW_SINGLE_POST_COMMENTS_CODE = 54;
    @NotNull
    public static final String POST_COMMENT = "add_comment";
    public static final int POST_COMMENT_CODE = 55;
    @NotNull
    public static final String LIKE_UNLIKE_REPLY = "like_unlike_reply";
    public static final int LIKE_UNLIKE_REPLY_CODE = 56;
    @NotNull
    public static final String REPLY_COMMENT = "reply_comment";
    public static final int REPLY_COMMENT_CODE = 57;
    @NotNull
    public static final String POST_LIKE_UNLIKE = "like_unlike_post";
    public static final int POST_LIKE_UNLIKE_CODE = 58;
    @NotNull
    public static final String VIEW_COMMENT_LIKES = "view_all_post_comment_like/";
    public static final int VIEW_COMMENT_LIKES_CODE = 59;
    @NotNull
    public static final String VIEW_REPLY_LIKES = "view_all_post_reply_like/";
    public static final int VIEW_REPLY_LIKES_CODE = 60;
    @NotNull
    public static final String CANCEL_FRIEND_REQ = "cancel_friend_request";
    public static final int CANCEL_FRIEND_REQ_CODE = 61;
    @NotNull
    public static final String FAQ = "send_faq";
    public static final int FAQ_CODE = 62;
    @NotNull
    public static final String HELP = "help";
    public static final int HELP_CODE = 63;
    @NotNull
    public static final String FEEDBACK = "get_feedback";
    public static final int FEEDBACK_CODE = 64;
    @NotNull
    public static final String SHOW_BLOCKED_USERS = "send_block_list/";
    public static final int SHOW_BLOCKED_USERS_CODE = 65;
    @NotNull
    public static final String UNBLOCK_USER = "unblock_user";
    public static final int UNBLOCK_USER_CODE = 66;
    @NotNull
    public static final String VIEW_MULTIMEDIA = "view_multimedia_acc_type";
    public static final int VIEW_MULTIMEDIA_CODE = 67;
    @NotNull
    public static final String SHOW_OTHER_PROFILE = "show_user_detail";
    public static final int SHOW_OTHER_PROFILE_CODE = 68;
    @NotNull
    public static final String CREATE_GROUP = "create_group";
    public static final int CREATE_GROUP_REQ_CODE = 69;
    @NotNull
    public static final String UNJOIN_GROUP = "unjoin_the_group";
    public static final int UNJOIN_GROUP_REQ_CODE = 70;
    @NotNull
    public static final String CREATE_ROOM_FOR_GROUP = "chat-room-group";
    public static final int CREATE_ROOM_FOR_GROUP_REQ_CODE = 71;
    @NotNull
    public static final String GROUP_CHAT_DATA = "group_chat_data";
    public static final int GROUP_CHAT_DATA_REQ_CODE = 72;
    @NotNull
    public static final String GROUP_DETAILS = "group_image";
    public static final int GROUP_DETAILS_REQ_CODE = 73;
    @NotNull
    public static final String REDEEM_OFFER = "redeem_coupon";
    public static final int REDEEM_OFFER_CODE = 74;
    @NotNull
    public static final String SHOW_REDEEM_OFFER = "user_list_redeem_offer/";
    public static final int SHOW_REDEEM_OFFER_CODE = 75;
    @NotNull
    public static final String DELETE_ALL_CHAT = "delete_full_chat_data/";
    public static final int DELETE_ALL_CHAT_CODE = 76;
    @NotNull
    public static final String ARCHIVE_ALL_CHAT = "archieve_user_group/";
    public static final int ARCHIVE_ALL_CHAT_CODE = 77;
    @NotNull
    public static final String UNARCHIVE_ALL_CHAT = "unarchieve_user_group/";
    public static final int UNARCHIVE_ALL_CHAT_CODE = 78;
    @NotNull
    public static final String ARCHIVE_STATUS = "send_archieve_user/";
    public static final int ARCHIVE_STATUS_CODE = 79;
    @NotNull
    public static final String DELETE_MULTIMEDIA = "delete_multimedia_data";
    public static final int DELETE_MULTIMEDIA_CODE = 80;
    @NotNull
    public static final String HIDE_USER_STATUS = "hide_user_status";
    public static final int HIDE_USER_STATUS_STATUS_CODE = 81;
    @NotNull
    public static final String SEND_USER_CHAT_WALLPAPER = "send_user_chat_wallpaper/";
    public static final int SEND_USER_CHAT_WALLPAPER_CODE = 82;
    @NotNull
    public static final String UPLOAD_WALLPAPER = "user_chat_wallpaper";
    public static final int UPLOAD_WALLPAPER_CODE = 83;
    @NotNull
    public static final String CHAT_SEARCH = "search_user_group_with_last_message";
    public static final int CHAT_SEARCH_CODE = 84;
    @NotNull
    public static final String SEND_BACKUP_STATUS = "back_up";
    public static final int SEND_BACKUP_STATUS_CODE = 85;
    @NotNull
    public static final String GET_BACKUP = "get_back_up/";
    public static final int GET_BACKUP_CODE = 86;
    @NotNull
    public static final String OFFLINE_ONLINE = "make_user_online_offline";
    public static final int OFFLINE_ONLINE_CODE = 87;
    @NotNull
    public static final String USER_DEVICE = "User_device";
    public static final int USER_DEVICE_CODE = 88;
    @NotNull
    public static final String GET_NOTIFICATION = "get_notification/";
    public static final int GET_NOTIFICATION_CODE = 89;
    @NotNull
    public static final String DELETE_NOTIFICATION = "delete_notification/";
    public static final int DELETE_NOTIFICATION_CODE = 90;
    @NotNull
    public static final String DELETE_USER = "delete_User_device/";
    public static final int DELETE_USER_CODE = 91;
    @NotNull
    public static final String PAGI_POST = "new_show_post";
    public static final int PAGI_POST_CODE = 92;
    @NotNull
    public static final String CHANGE_PASSWORD = "change_user_password";
    public static final int CHANGE_PASSWORD_CODE = 93;
    @NotNull
    public static final String DELETE_POST = "delete-user-post";
    public static final int DELETE_POST_CODE = 94;
    @NotNull
    public static final String DELETE_ACCOUNT = "delete-user-account/";
    public static final int DELETE_ACCOUNT_CODE = 95;
    @NotNull
    public static final String SHARE_POST = "shareUserPost";
    public static final int SHARE_POST_CODE = 96;
    @NotNull
    public static final String SUBMIT_OTP = "user_otp_verify";
    public static final int SUBMIT_OTP_CODE = 97;
    @NotNull
    public static final String RESEND_OTP = "user_resend_otp/";
    public static final int RESEND_OTP_CODE = 98;
    @NotNull
    public static final String CHANGE_GROUP_ICON = "change_group_icon";
    public static final int CHANGE_GROUP_ICON_CODE = 99;
    @NotNull
    public static final String GROUP_DETAIL = "group_detail/";
    public static final int GROUP_DETAIL_CODE = 100;
    @NotNull
    public static final String ADD_PARTCIPANT = "add_participant";
    public static final int ADD_PARTCIPANT_CODE = 101;
    @NotNull
    public static final String REMOVE_PARTCIPANT = "remove_participant";
    public static final int REMOVE_PARTCIPANT_CODE = 102;
    @NotNull
    public static final String VIEW_MULTIMEDIA_CHAT = "view_multimedia";
    public static final int VIEW_MULTIMEDIA_CHAT_CODE = 103;
    @NotNull
    public static final String DELETE_CHAT = "delete_chat";
    public static final int DELETE_CHAT_CODE = 104;
    @NotNull
    public static final String ARCHIVE_CHAT = "archive_chat";
    public static final int ARCHIVE_CHAT_CODE = 105;
    @NotNull
    public static final String GROUP_BLOCK_LIST = "block_list";
    public static final int GROUP_BLOCK_LIST_CODE = 106;
    @NotNull
    public static final String SHOW_FRIEND_USER = "show_friend_user";
    public static final int SHOW_FRIEND_USER_CODE = 107;
    @NotNull
    public static final String SEND_MULTI_USER_FRIEND_REQUEST = "send_multiuser_friend_request";
    public static final int SEND_MULTI_USER_FRIEND_REQUEST_CODE = 108;
    @NotNull
    public static final String ROOM_ID = "room_id";
    @NotNull
    public static final String SENDER_ID = "sender_id";
    @NotNull
    public static final String RECEIVER_ID = "receiver_id";
    @NotNull
    public static final String GROUP_ID = "group_id";
    @NotNull
    public static final String MESSAGE = "message";
    @NotNull
    public static final String MESSAGE_TYPE = "message_type";
    @NotNull
    public static final String FILE_TYPE = "file_type";
    @NotNull
    public static final String JOIN_ROOM = "room join";
    @NotNull
    public static final String LISTEN_ROOM_JOIN = "room join";
    @NotNull
    public static final String LEAVE_ROOM = "room leave";
    @NotNull
    public static final String SEND_MESSAGE = "message";
    @NotNull
    public static final String LISTEN_SEND_MESSAGE = "message";
    @NotNull
    public static final String SEEN = "seen";
    @NotNull
    public static final String LISTEN_SEEN = "seen";
    @NotNull
    public static final String GROUP_SEEN_EMIT_LISTEN = "seen_chat";
    @NotNull
    public static final String EMIT_USER_STATUS = "sendUserOnlineStatusToServer";
    @NotNull
    public static final String USER_STATUS = "is_user_online";
    @NotNull
    public static final String STATUS_LISTENER = "serverOnlineStatusResponseToUser";
    @NotNull
    public static final String UPLOAD_MORE_DATA = "uploadFileMoreDataReq";
    @NotNull
    public static final String KEY_USERTYPE = "usertype";
    @NotNull
    public static final String KEY_USERNAME = "";
    @NotNull
    public static final String KEY_USER_FIRSTNAME = "firstname";
    @NotNull
    public static final String KEY_USER_LASTNAME = "lastname";
    @NotNull
    public static final String KEY_USERID = "userid";
    @NotNull
    public static final String KEY_EMAIL = "email";
    @NotNull
    public static final String KEY_PHONE = "phone";
    @NotNull
    public static final String KEY_ALTERNET_PHONE = "altrphone";
    @NotNull
    public static final String KEY_PASSWORD = "password";
    @NotNull
    public static final String KEY_ADDRESS = "address";
    @NotNull
    public static final String KEY_PROFILE_IMAGE = "profile_image";
    @NotNull
    public static final String KEY_PROFILE_STATUS = "profile_status";
    @NotNull
    public static final String KEY_STATUS = "status";
    @NotNull
    public static final String UNIVERSITY_SCHOOL_ID = "university_school_id";
    @NotNull
    public static final String UNIVERSITY_NAME = "university_school_name";
    @NotNull
    public static final String OTP_VERIFY = "no";
    @NotNull
    public static final String PLACE_API = "AIzaSyCDl6ZDBRDkG1KMCZkAFavadSBthuruHvE";
    @NotNull
    public static String PROFILE_IMAGE_URL="http://15.206.103.14/public/profile_imgs/";
    @NotNull
    public static String POST_IMAGE_URL;
    @NotNull
    public static String categoryImageUrl;
    @NotNull
    public static String offerImageUrl;
    @NotNull
    public static String brandImageUrl;
    @NotNull
    public static String blogImageUrl;
    @NotNull
    public static String CHAT_MEDIA_URL;
    @NotNull
    public static SimpleDateFormat source;
    @NotNull
    public static SimpleDateFormat target;

    public WebUrls() {
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_USERTYPE$annotations() {
    }

    @NotNull
    public static final String getKEY_USERTYPE() {
        return KEY_USERTYPE;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_USERNAME$annotations() {
    }

    @NotNull
    public static final String getKEY_USERNAME() {
        return KEY_USERNAME;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_USER_FIRSTNAME$annotations() {
    }

    @NotNull
    public static final String getKEY_USER_FIRSTNAME() {
        return KEY_USER_FIRSTNAME;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_USER_LASTNAME$annotations() {
    }

    @NotNull
    public static final String getKEY_USER_LASTNAME() {
        return KEY_USER_LASTNAME;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_USERID$annotations() {
    }

    @NotNull
    public static final String getKEY_USERID() {
        return KEY_USERID;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_EMAIL$annotations() {
    }

    @NotNull
    public static final String getKEY_EMAIL() {
        return KEY_EMAIL;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_PHONE$annotations() {
    }

    @NotNull
    public static final String getKEY_PHONE() {
        return KEY_PHONE;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_ALTERNET_PHONE$annotations() {
    }

    @NotNull
    public static final String getKEY_ALTERNET_PHONE() {
        return KEY_ALTERNET_PHONE;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_PASSWORD$annotations() {
    }

    @NotNull
    public static final String getKEY_PASSWORD() {
        return KEY_PASSWORD;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_ADDRESS$annotations() {
    }

    @NotNull
    public static final String getKEY_ADDRESS() {
        return KEY_ADDRESS;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_PROFILE_IMAGE$annotations() {
    }

    @NotNull
    public static final String getKEY_PROFILE_IMAGE() {
        return KEY_PROFILE_IMAGE;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_PROFILE_STATUS$annotations() {
    }

    @NotNull
    public static final String getKEY_PROFILE_STATUS() {
        return KEY_PROFILE_STATUS;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void KEY_STATUS$annotations() {
    }

    @NotNull
    public static final String getKEY_STATUS() {
        return KEY_STATUS;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void UNIVERSITY_SCHOOL_ID$annotations() {
    }

    @NotNull
    public static final String getUNIVERSITY_SCHOOL_ID() {
        return UNIVERSITY_SCHOOL_ID;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void UNIVERSITY_NAME$annotations() {
    }

    @NotNull
    public static final String getUNIVERSITY_NAME() {
        return UNIVERSITY_NAME;
    }

    /**
     * @deprecated
     */
    // $FF: synthetic method
    @JvmStatic
    public static void OTP_VERIFY$annotations() {
    }

    @NotNull
    public static final String getOTP_VERIFY() {
        return OTP_VERIFY;
    }

    @NotNull
    public final String getBASE_URL() {
        return BASE_URL;
    }

    @NotNull
    public final String getMSG_BASE_URL() {
        return MSG_BASE_URL;
    }

    @NotNull
    public final String getIMAGE_BASE_URL() {
        return IMAGE_BASE_URL;
    }

    @NotNull
    public final String getPROFILE_IMAGE() {
        return PROFILE_IMAGE;
    }

    @NotNull
    public final String getMYPROFILE_ICON() {
        return MYPROFILE_ICON;
    }

    @NotNull
    public final String getPROFILE_IMAGE_URL() {
        return PROFILE_IMAGE_URL;
    }

    public final void setPROFILE_IMAGE_URL(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        PROFILE_IMAGE_URL = var1;
    }

    @NotNull
    public final String getPOST_IMAGE_URL() {
        return POST_IMAGE_URL;
    }

    public final void setPOST_IMAGE_URL(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        POST_IMAGE_URL = var1;
    }

    @NotNull
    public final String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public final void setCategoryImageUrl(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        categoryImageUrl = var1;
    }

    @NotNull
    public final String getOfferImageUrl() {
        return offerImageUrl;
    }

    public final void setOfferImageUrl(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        offerImageUrl = var1;
    }

    @NotNull
    public final String getBrandImageUrl() {
        return brandImageUrl;
    }

    public final void setBrandImageUrl(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        brandImageUrl = var1;
    }

    @NotNull
    public final String getBlogImageUrl() {
        return blogImageUrl;
    }

    public final void setBlogImageUrl(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        blogImageUrl = var1;
    }

    @NotNull
    public final String getSOCKET_BASE_URL() {
        return SOCKET_BASE_URL;
    }

    @NotNull
    public final String getCHAT_MEDIA_URL() {
        return CHAT_MEDIA_URL;
    }

    public final void setCHAT_MEDIA_URL(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        CHAT_MEDIA_URL = var1;
    }

    @NotNull
    public final String getLOGIN() {
        return LOGIN;
    }

    public final int getLOGIN_CODE() {
        return LOGIN_CODE;
    }

    @NotNull
    public final String getSIGN_UP() {
        return SIGN_UP;
    }

    public final int getSIGN_UP_CODE() {
        return SIGN_UP_CODE;
    }

    @NotNull
    public final String getFORGET_PASSWORD() {
        return FORGET_PASSWORD;
    }

    public final int getFORGET_PASSWORD_CODE() {
        return FORGET_PASSWORD_CODE;
    }

    @NotNull
    public final String getSEND_UNIVERSITY() {
        return SEND_UNIVERSITY;
    }

    public final int getSEND_UNIVERSITY_CODE() {
        return SEND_UNIVERSITY_CODE;
    }

    @NotNull
    public final String getSEND_PROFILE_QUSTION() {
        return SEND_PROFILE_QUSTION;
    }

    public final int getSEND_PROFILE_QUSTION_CODE() {
        return SEND_PROFILE_QUSTION_CODE;
    }

    @NotNull
    public final String getSEND_PROFILE_ANSWER() {
        return SEND_PROFILE_ANSWER;
    }

    public final int getSEND_PROFILE_ANSWER_CODE() {
        return SEND_PROFILE_ANSWER_CODE;
    }

    @NotNull
    public final String getSEND_INTREST() {
        return SEND_INTREST;
    }

    public final int getSEND_INTREST_CODE() {
        return SEND_INTREST_CODE;
    }

    @NotNull
    public final String getSEND_HOBBY() {
        return SEND_HOBBY;
    }

    public final int getSEND_HOBBY_CODE() {
        return SEND_HOBBY_CODE;
    }

    @NotNull
    public final String getSEND_CATEGORIES() {
        return SEND_CATEGORIES;
    }

    public final int getSEND_CATEGORIES_CODE() {
        return SEND_CATEGORIES_CODE;
    }

    @NotNull
    public final String getVIEW_PROFILE() {
        return VIEW_PROFILE;
    }

    public final int getVIEW_PROFILE_CODE() {
        return VIEW_PROFILE_CODE;
    }

    @NotNull
    public final String getVIEW_FRIEND_LIST() {
        return VIEW_FRIEND_LIST;
    }

    public final int getVIEW_FREIND_LIST_CODE() {
        return VIEW_FREIND_LIST_CODE;
    }

    @NotNull
    public final String getADD_POST_ATT() {
        return ADD_POST_ATT;
    }

    public final int getADD_POST_ATT_CODE() {
        return ADD_POST_ATT_CODE;
    }

    @NotNull
    public final String getADD_POST() {
        return ADD_POST;
    }

    public final int getADD_POST_CODE() {
        return ADD_POST_CODE;
    }

    @NotNull
    public final String getSHOW_POST() {
        return SHOW_POST;
    }

    public final int getSHOW_POST_CODE() {
        return SHOW_POST_CODE;
    }

    @NotNull
    public final String getSEND_GROUP_LIST() {
        return SEND_GROUP_LIST;
    }

    public final int getSEND_GROUP_LIST_CODE() {
        return SEND_GROUP_LIST_CODE;
    }

    @NotNull
    public final String getVIEW_PROFILE_QUES_ANSWER() {
        return VIEW_PROFILE_QUES_ANSWER;
    }

    public final int getVIEW_PROFILE_QUES_ANSWER_CODE() {
        return VIEW_PROFILE_QUES_ANSWER_CODE;
    }

    @NotNull
    public final String getBLOG_DATA() {
        return BLOG_DATA;
    }

    public final int getBLOG_DATA_CODE() {
        return BLOG_DATA_CODE;
    }

    @NotNull
    public final String getLIKE_UNLIKE_COMMENT() {
        return LIKE_UNLIKE_COMMENT;
    }

    public final int getLIKE_UNLIKE_COMMENT_CODE() {
        return LIKE_UNLIKE_COMMENT_CODE;
    }

    @NotNull
    public final String getVIEW_ALL_POST_LIKE() {
        return VIEW_ALL_POST_LIKE;
    }

    public final int getVIEW_ALL_POST_LIKE_CODE() {
        return VIEW_ALL_POST_LIKE_CODE;
    }

    @NotNull
    public final String getVIEW_SAVED_BLOG() {
        return VIEW_SAVED_BLOG;
    }

    public final int getVIEW_SAVED_BLOG_CODE() {
        return VIEW_SAVED_BLOG_CODE;
    }

    @NotNull
    public final String getSAVED_READ_BLOG() {
        return SAVED_READ_BLOG;
    }

    public final int getSAVED_READ_BLOG_CODE() {
        return SAVED_READ_BLOG_CODE;
    }

    @NotNull
    public final String getVIEW_LIKE_BLOG() {
        return VIEW_LIKE_BLOG;
    }

    public final int getVIEW_LIKE_BLOG_CODE() {
        return VIEW_LIKE_BLOG_CODE;
    }

    @NotNull
    public final String getVIEW_SHARED_BLOG() {
        return VIEW_SHARED_BLOG;
    }

    public final int getVIEW_SHARED_BLOG_CODE() {
        return VIEW_SHARED_BLOG_CODE;
    }

    @NotNull
    public final String getSHOW_SINGLE_BLOG_DETAIL() {
        return SHOW_SINGLE_BLOG_DETAIL;
    }

    public final int getSHOW_SINGLE_BLOG_DETAIL_CODE() {
        return SHOW_SINGLE_BLOG_DETAIL_CODE;
    }

    @NotNull
    public final String getLIKE_UNLIKE_BLOG() {
        return LIKE_UNLIKE_BLOG;
    }

    public final int getLIKE_UNLIKE_BLOG_CODE() {
        return LIKE_UNLIKE_BLOG_CODE;
    }

    @NotNull
    public final String getSAVE_BLOG() {
        return SAVE_BLOG;
    }

    public final int getSAVE_BLOG_CODE() {
        return SAVE_BLOG_CODE;
    }

    @NotNull
    public final String getVIEW_ALL_BLOGS_ACC_TO_CAT() {
        return VIEW_ALL_BLOGS_ACC_TO_CAT;
    }

    public final int getVIEW_ALL_BLOGS_ACC_TO_CAT_CODE() {
        return VIEW_ALL_BLOGS_ACC_TO_CAT_CODE;
    }

    @NotNull
    public final String getSHOW_ALL_BLOGS() {
        return SHOW_ALL_BLOGS;
    }

    public final int getSHOW_ALL_BLOGS_CODE() {
        return SHOW_ALL_BLOGS_CODE;
    }

    @NotNull
    public final String getSEARCH_BLOG() {
        return SEARCH_BLOG;
    }

    public final int getSEARCH_BLOG_CODE() {
        return SEARCH_BLOG_CODE;
    }

    @NotNull
    public final String getREAD_BLOG() {
        return READ_BLOG;
    }

    public final int getREAD_BLOG_CODE() {
        return READ_BLOG_CODE;
    }

    @NotNull
    public final String getSHOW_OFFERS() {
        return SHOW_OFFERS;
    }

    public final int getSHOW_OFFERS_CODE() {
        return SHOW_OFFERS_CODE;
    }

    @NotNull
    public final String getSHOW_OFFERS_ACC_CAT() {
        return SHOW_OFFERS_ACC_CAT;
    }

    public final int getSHOW_OFFERS_ACC_CAT_CODE() {
        return SHOW_OFFERS_ACC_CAT_CODE;
    }

    @NotNull
    public final String getGET_OFFER_CAT() {
        return GET_OFFER_CAT;
    }

    public final int getGET_OFFER_CAT_CODE() {
        return GET_OFFER_CAT_CODE;
    }

    @NotNull
    public final String getGET_SINGLE_OFFER() {
        return GET_SINGLE_OFFER;
    }

    public final int getGET_SINGLE_OFFER_CODE() {
        return GET_SINGLE_OFFER_CODE;
    }

    @NotNull
    public final String getVIEW_ALL_RELATED() {
        return VIEW_ALL_RELATED;
    }

    public final int getVIEW_ALL_RELATED_CODE() {
        return VIEW_ALL_RELATED_CODE;
    }

    @NotNull
    public final String getSAVE_OFFER() {
        return SAVE_OFFER;
    }

    public final int getSAVE_OFFER_CODE() {
        return SAVE_OFFER_CODE;
    }

    @NotNull
    public final String getSEARCH_BRAND() {
        return SEARCH_BRAND;
    }

    public final int getSEARCH_BRAND_CODE() {
        return SEARCH_BRAND_CODE;
    }

    @NotNull
    public final String getLIST_VIEW_BRANDS() {
        return LIST_VIEW_BRANDS;
    }

    public final int getLIST_VIEW_BRANDS_CODE() {
        return LIST_VIEW_BRANDS_CODE;
    }

    @NotNull
    public final String getSHOW_ONLINE_FRIENDS() {
        return SHOW_ONLINE_FRIENDS;
    }

    public final int getSHOW_ONLINE_FRIENDS_CODE() {
        return SHOW_ONLINE_FRIENDS_CODE;
    }

    @NotNull
    public final String getSHOW_FRIENDS_LISTING() {
        return SHOW_FRIENDS_LISTING;
    }

    public final int getSHOW_FRIENDS_LISTING_CODE() {
        return SHOW_FRIENDS_LISTING_CODE;
    }

    @NotNull
    public final String getVIEW_MORE_SUGGESTIONS() {
        return VIEW_MORE_SUGGESTIONS;
    }

    public final int getVIEW_MORE_SUGGESTIONS_CODE() {
        return VIEW_MORE_SUGGESTIONS_CODE;
    }

    @NotNull
    public final String getSEND_FRIEND_REQ() {
        return SEND_FRIEND_REQ;
    }

    public final int getSEND_FRIEND_REQ_CODE() {
        return SEND_FRIEND_REQ_CODE;
    }

    @NotNull
    public final String getREJECT_FRIEND_REQ() {
        return REJECT_FRIEND_REQ;
    }

    public final int getREJECT_FRIEND_REQ_CODE() {
        return REJECT_FRIEND_REQ_CODE;
    }

    @NotNull
    public final String getACCEPT_FRIEND_REQ() {
        return ACCEPT_FRIEND_REQ;
    }

    public final int getACCEPT_FRIEND_REQ_CODE() {
        return ACCEPT_FRIEND_REQ_CODE;
    }

    @NotNull
    public final String getRCVD_FRIENDS_REQ() {
        return RCVD_FRIENDS_REQ;
    }

    public final int getRCVD_FRIENDS_REQ_CODE() {
        return RCVD_FRIENDS_REQ_CODE;
    }

    @NotNull
    public final String getGET_ALL_CHAT() {
        return GET_ALL_CHAT;
    }

    public final int getGET_ALL_CHAT_CODE() {
        return GET_ALL_CHAT_CODE;
    }

    @NotNull
    public final String getCREATE_ROOM() {
        return CREATE_ROOM;
    }

    public final int getCREATE_ROOM_CODE() {
        return CREATE_ROOM_CODE;
    }

    @NotNull
    public final String getLAST_MSG_LIST() {
        return LAST_MSG_LIST;
    }

    public final int getLAST_MSG_LIST_CODE() {
        return LAST_MSG_LIST_CODE;
    }

    @NotNull
    public final String getBLOCK_USER() {
        return BLOCK_USER;
    }

    public final int getBLOCK_USER_CODE() {
        return BLOCK_USER_CODE;
    }

    @NotNull
    public final String getLIST_SHARED_OFFER() {
        return LIST_SHARED_OFFER;
    }

    public final int getLIST_SHARED_OFFER_CODE() {
        return LIST_SHARED_OFFER_CODE;
    }

    @NotNull
    public final String getLIST_SAVED_OFFER() {
        return LIST_SAVED_OFFER;
    }

    public final int getLIST_SAVED_OFFER_CODE() {
        return LIST_SAVED_OFFER_CODE;
    }

    @NotNull
    public final String getSHARE_OFFER() {
        return SHARE_OFFER;
    }

    public final int getSHARE_OFFER_CODE() {
        return SHARE_OFFER_CODE;
    }

    @NotNull
    public final String getREAD_OFFER() {
        return READ_OFFER;
    }

    public final int getREAD_OFFER_CODE() {
        return READ_OFFER_CODE;
    }

    @NotNull
    public final String getSHOW_SINGLE_POST_COMMENTS() {
        return SHOW_SINGLE_POST_COMMENTS;
    }

    public final int getSHOW_SINGLE_POST_COMMENTS_CODE() {
        return SHOW_SINGLE_POST_COMMENTS_CODE;
    }

    @NotNull
    public final String getPOST_COMMENT() {
        return POST_COMMENT;
    }

    public final int getPOST_COMMENT_CODE() {
        return POST_COMMENT_CODE;
    }

    @NotNull
    public final String getLIKE_UNLIKE_REPLY() {
        return LIKE_UNLIKE_REPLY;
    }

    public final int getLIKE_UNLIKE_REPLY_CODE() {
        return LIKE_UNLIKE_REPLY_CODE;
    }

    @NotNull
    public final String getREPLY_COMMENT() {
        return REPLY_COMMENT;
    }

    public final int getREPLY_COMMENT_CODE() {
        return REPLY_COMMENT_CODE;
    }

    @NotNull
    public final String getPOST_LIKE_UNLIKE() {
        return POST_LIKE_UNLIKE;
    }

    public final int getPOST_LIKE_UNLIKE_CODE() {
        return POST_LIKE_UNLIKE_CODE;
    }

    @NotNull
    public final String getVIEW_COMMENT_LIKES() {
        return VIEW_COMMENT_LIKES;
    }

    public final int getVIEW_COMMENT_LIKES_CODE() {
        return VIEW_COMMENT_LIKES_CODE;
    }

    @NotNull
    public final String getVIEW_REPLY_LIKES() {
        return VIEW_REPLY_LIKES;
    }

    public final int getVIEW_REPLY_LIKES_CODE() {
        return VIEW_REPLY_LIKES_CODE;
    }

    @NotNull
    public final String getCANCEL_FRIEND_REQ() {
        return CANCEL_FRIEND_REQ;
    }

    public final int getCANCEL_FRIEND_REQ_CODE() {
        return CANCEL_FRIEND_REQ_CODE;
    }

    @NotNull
    public final String getFAQ() {
        return FAQ;
    }

    public final int getFAQ_CODE() {
        return FAQ_CODE;
    }

    @NotNull
    public final String getHELP() {
        return HELP;
    }

    public final int getHELP_CODE() {
        return HELP_CODE;
    }

    @NotNull
    public final String getFEEDBACK() {
        return FEEDBACK;
    }

    public final int getFEEDBACK_CODE() {
        return FEEDBACK_CODE;
    }

    @NotNull
    public final String getSHOW_BLOCKED_USERS() {
        return SHOW_BLOCKED_USERS;
    }

    public final int getSHOW_BLOCKED_USERS_CODE() {
        return SHOW_BLOCKED_USERS_CODE;
    }

    @NotNull
    public final String getUNBLOCK_USER() {
        return UNBLOCK_USER;
    }

    public final int getUNBLOCK_USER_CODE() {
        return UNBLOCK_USER_CODE;
    }

    @NotNull
    public final String getVIEW_MULTIMEDIA() {
        return VIEW_MULTIMEDIA;
    }

    public final int getVIEW_MULTIMEDIA_CODE() {
        return VIEW_MULTIMEDIA_CODE;
    }

    @NotNull
    public final String getSHOW_OTHER_PROFILE() {
        return SHOW_OTHER_PROFILE;
    }

    public final int getSHOW_OTHER_PROFILE_CODE() {
        return SHOW_OTHER_PROFILE_CODE;
    }

    @NotNull
    public final String getCREATE_GROUP() {
        return CREATE_GROUP;
    }

    public final int getCREATE_GROUP_REQ_CODE() {
        return CREATE_GROUP_REQ_CODE;
    }

    @NotNull
    public final String getUNJOIN_GROUP() {
        return UNJOIN_GROUP;
    }

    public final int getUNJOIN_GROUP_REQ_CODE() {
        return UNJOIN_GROUP_REQ_CODE;
    }

    @NotNull
    public final String getCREATE_ROOM_FOR_GROUP() {
        return CREATE_ROOM_FOR_GROUP;
    }

    public final int getCREATE_ROOM_FOR_GROUP_REQ_CODE() {
        return CREATE_ROOM_FOR_GROUP_REQ_CODE;
    }

    @NotNull
    public final String getGROUP_CHAT_DATA() {
        return GROUP_CHAT_DATA;
    }

    public final int getGROUP_CHAT_DATA_REQ_CODE() {
        return GROUP_CHAT_DATA_REQ_CODE;
    }

    @NotNull
    public final String getGROUP_DETAILS() {
        return GROUP_DETAILS;
    }

    public final int getGROUP_DETAILS_REQ_CODE() {
        return GROUP_DETAILS_REQ_CODE;
    }

    @NotNull
    public final String getREDEEM_OFFER() {
        return REDEEM_OFFER;
    }

    public final int getREDEEM_OFFER_CODE() {
        return REDEEM_OFFER_CODE;
    }

    @NotNull
    public final String getSHOW_REDEEM_OFFER() {
        return SHOW_REDEEM_OFFER;
    }

    public final int getSHOW_REDEEM_OFFER_CODE() {
        return SHOW_REDEEM_OFFER_CODE;
    }

    @NotNull
    public final String getDELETE_ALL_CHAT() {
        return DELETE_ALL_CHAT;
    }

    public final int getDELETE_ALL_CHAT_CODE() {
        return DELETE_ALL_CHAT_CODE;
    }

    @NotNull
    public final String getARCHIVE_ALL_CHAT() {
        return ARCHIVE_ALL_CHAT;
    }

    public final int getARCHIVE_ALL_CHAT_CODE() {
        return ARCHIVE_ALL_CHAT_CODE;
    }

    @NotNull
    public final String getUNARCHIVE_ALL_CHAT() {
        return UNARCHIVE_ALL_CHAT;
    }

    public final int getUNARCHIVE_ALL_CHAT_CODE() {
        return UNARCHIVE_ALL_CHAT_CODE;
    }

    @NotNull
    public final String getARCHIVE_STATUS() {
        return ARCHIVE_STATUS;
    }

    public final int getARCHIVE_STATUS_CODE() {
        return ARCHIVE_STATUS_CODE;
    }

    @NotNull
    public final String getDELETE_MULTIMEDIA() {
        return DELETE_MULTIMEDIA;
    }

    public final int getDELETE_MULTIMEDIA_CODE() {
        return DELETE_MULTIMEDIA_CODE;
    }

    @NotNull
    public final String getHIDE_USER_STATUS() {
        return HIDE_USER_STATUS;
    }

    public final int getHIDE_USER_STATUS_STATUS_CODE() {
        return HIDE_USER_STATUS_STATUS_CODE;
    }

    @NotNull
    public final String getSEND_USER_CHAT_WALLPAPER() {
        return SEND_USER_CHAT_WALLPAPER;
    }

    public final int getSEND_USER_CHAT_WALLPAPER_CODE() {
        return SEND_USER_CHAT_WALLPAPER_CODE;
    }

    @NotNull
    public final String getUPLOAD_WALLPAPER() {
        return UPLOAD_WALLPAPER;
    }

    public final int getUPLOAD_WALLPAPER_CODE() {
        return UPLOAD_WALLPAPER_CODE;
    }

    @NotNull
    public final String getCHAT_SEARCH() {
        return CHAT_SEARCH;
    }

    public final int getCHAT_SEARCH_CODE() {
        return CHAT_SEARCH_CODE;
    }

    @NotNull
    public final String getSEND_BACKUP_STATUS() {
        return SEND_BACKUP_STATUS;
    }

    public final int getSEND_BACKUP_STATUS_CODE() {
        return SEND_BACKUP_STATUS_CODE;
    }

    @NotNull
    public final String getGET_BACKUP() {
        return GET_BACKUP;
    }

    public final int getGET_BACKUP_CODE() {
        return GET_BACKUP_CODE;
    }

    @NotNull
    public final String getOFFLINE_ONLINE() {
        return OFFLINE_ONLINE;
    }

    public final int getOFFLINE_ONLINE_CODE() {
        return OFFLINE_ONLINE_CODE;
    }

    @NotNull
    public final String getUSER_DEVICE() {
        return USER_DEVICE;
    }

    public final int getUSER_DEVICE_CODE() {
        return USER_DEVICE_CODE;
    }

    @NotNull
    public final String getGET_NOTIFICATION() {
        return GET_NOTIFICATION;
    }

    public final int getGET_NOTIFICATION_CODE() {
        return GET_NOTIFICATION_CODE;
    }

    @NotNull
    public final String getDELETE_NOTIFICATION() {
        return DELETE_NOTIFICATION;
    }

    public final int getDELETE_NOTIFICATION_CODE() {
        return DELETE_NOTIFICATION_CODE;
    }

    @NotNull
    public final String getDELETE_USER() {
        return DELETE_USER;
    }

    public final int getDELETE_USER_CODE() {
        return DELETE_USER_CODE;
    }

    @NotNull
    public final String getPAGI_POST() {
        return PAGI_POST;
    }

    public final int getPAGI_POST_CODE() {
        return PAGI_POST_CODE;
    }

    @NotNull
    public final String getCHANGE_PASSWORD() {
        return CHANGE_PASSWORD;
    }

    public final int getCHANGE_PASSWORD_CODE() {
        return CHANGE_PASSWORD_CODE;
    }

    @NotNull
    public final String getDELETE_POST() {
        return DELETE_POST;
    }

    public final int getDELETE_POST_CODE() {
        return DELETE_POST_CODE;
    }

    @NotNull
    public final String getDELETE_ACCOUNT() {
        return DELETE_ACCOUNT;
    }

    public final int getDELETE_ACCOUNT_CODE() {
        return DELETE_ACCOUNT_CODE;
    }

    @NotNull
    public final String getSHARE_POST() {
        return SHARE_POST;
    }

    public final int getSHARE_POST_CODE() {
        return SHARE_POST_CODE;
    }

    @NotNull
    public final String getSUBMIT_OTP() {
        return SUBMIT_OTP;
    }

    public final int getSUBMIT_OTP_CODE() {
        return SUBMIT_OTP_CODE;
    }

    @NotNull
    public final String getRESEND_OTP() {
        return RESEND_OTP;
    }

    public final int getRESEND_OTP_CODE() {
        return RESEND_OTP_CODE;
    }

    @NotNull
    public final String getCHANGE_GROUP_ICON() {
        return CHANGE_GROUP_ICON;
    }

    public final int getCHANGE_GROUP_ICON_CODE() {
        return CHANGE_GROUP_ICON_CODE;
    }

    @NotNull
    public final String getGROUP_DETAIL() {
        return GROUP_DETAIL;
    }

    public final int getGROUP_DETAIL_CODE() {
        return GROUP_DETAIL_CODE;
    }

    @NotNull
    public final String getADD_PARTCIPANT() {
        return ADD_PARTCIPANT;
    }

    public final int getADD_PARTCIPANT_CODE() {
        return ADD_PARTCIPANT_CODE;
    }

    @NotNull
    public final String getREMOVE_PARTCIPANT() {
        return REMOVE_PARTCIPANT;
    }

    public final int getREMOVE_PARTCIPANT_CODE() {
        return REMOVE_PARTCIPANT_CODE;
    }

    @NotNull
    public final String getVIEW_MULTIMEDIA_CHAT() {
        return VIEW_MULTIMEDIA_CHAT;
    }

    public final int getVIEW_MULTIMEDIA_CHAT_CODE() {
        return VIEW_MULTIMEDIA_CHAT_CODE;
    }

    @NotNull
    public final String getDELETE_CHAT() {
        return DELETE_CHAT;
    }

    public final int getDELETE_CHAT_CODE() {
        return DELETE_CHAT_CODE;
    }

    @NotNull
    public final String getARCHIVE_CHAT() {
        return ARCHIVE_CHAT;
    }

    public final int getARCHIVE_CHAT_CODE() {
        return ARCHIVE_CHAT_CODE;
    }

    @NotNull
    public final String getGROUP_BLOCK_LIST() {
        return GROUP_BLOCK_LIST;
    }

    public final int getGROUP_BLOCK_LIST_CODE() {
        return GROUP_BLOCK_LIST_CODE;
    }

    @NotNull
    public final String getSHOW_FRIEND_USER() {
        return SHOW_FRIEND_USER;
    }

    public final int getSHOW_FRIEND_USER_CODE() {
        return SHOW_FRIEND_USER_CODE;
    }

    @NotNull
    public final String getSEND_MULTI_USER_FRIEND_REQUEST() {
        return SEND_MULTI_USER_FRIEND_REQUEST;
    }

    public final int getSEND_MULTI_USER_FRIEND_REQUEST_CODE() {
        return SEND_MULTI_USER_FRIEND_REQUEST_CODE;
    }

    @NotNull
    public final String getROOM_ID() {
        return ROOM_ID;
    }

    @NotNull
    public final String getSENDER_ID() {
        return SENDER_ID;
    }

    @NotNull
    public final String getRECEIVER_ID() {
        return RECEIVER_ID;
    }

    @NotNull
    public final String getGROUP_ID() {
        return GROUP_ID;
    }

    @NotNull
    public final String getMESSAGE() {
        return MESSAGE;
    }

    @NotNull
    public final String getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }

    @NotNull
    public final String getFILE_TYPE() {
        return FILE_TYPE;
    }

    @NotNull
    public final String getJOIN_ROOM() {
        return JOIN_ROOM;
    }

    @NotNull
    public final String getLISTEN_ROOM_JOIN() {
        return LISTEN_ROOM_JOIN;
    }

    @NotNull
    public final String getLEAVE_ROOM() {
        return LEAVE_ROOM;
    }

    @NotNull
    public final String getSEND_MESSAGE() {
        return SEND_MESSAGE;
    }

    @NotNull
    public final String getLISTEN_SEND_MESSAGE() {
        return LISTEN_SEND_MESSAGE;
    }

    @NotNull
    public final String getSEEN() {
        return SEEN;
    }

    @NotNull
    public final String getLISTEN_SEEN() {
        return LISTEN_SEEN;
    }

    @NotNull
    public final String getGROUP_SEEN_EMIT_LISTEN() {
        return GROUP_SEEN_EMIT_LISTEN;
    }

    @NotNull
    public final String getEMIT_USER_STATUS() {
        return EMIT_USER_STATUS;
    }

    @NotNull
    public final String getUSER_STATUS() {
        return USER_STATUS;
    }

    @NotNull
    public final String getSTATUS_LISTENER() {
        return STATUS_LISTENER;
    }

    @NotNull
    public final String getUPLOAD_MORE_DATA() {
        return UPLOAD_MORE_DATA;
    }

    @NotNull
    public final SimpleDateFormat getSource() {
        return source;
    }

    public final void setSource(@NotNull SimpleDateFormat var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        source = var1;
    }

    @NotNull
    public final SimpleDateFormat getTarget() {
        return target;
    }

    public final void setTarget(@NotNull SimpleDateFormat var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        target = var1;
    }

    @NotNull
    public final String getPLACE_API() {
        return PLACE_API;
    }
}
