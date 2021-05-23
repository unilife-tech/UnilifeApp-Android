package unilife.com.unilife.retro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static final String BASE_URL = "http://15.206.103.14/api/wsapp/home/"; //live
    private static final String BASE_URL_OLD = "http://15.206.103.14:3006/"; //live
    //============== Retrofit Initiation ================================
    public static Retrofit getClient() {
        Retrofit retrofit = null;
        HttpLoggingInterceptor logLevel = new HttpLoggingInterceptor();
        logLevel.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logLevel);  // <-- this is the important line

        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.writeTimeout(120, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()  // added recently ,can be removed
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getClientOld() {
        Retrofit retrofit = null;
        HttpLoggingInterceptor logLevel = new HttpLoggingInterceptor();
        logLevel.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logLevel);  // <-- this is the important line

        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.writeTimeout(120, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()  // added recently ,can be removed
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_OLD)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }

    final class SubUrls {
        //==========================Profile===============================
        static final String GETPROFILE = "get_all_profile_data";
        static final String USER_HIGHLIGHTS = "user_highlights";
        static final String PROFILE_UPDATE = "profile_update";
        static final String PERSONAL_MISSION_UPDATE = "personal_mission_update";
        static final String USER_SOCIAL_PROFILE = "user_social_profile";
        static final String USER_EXPERIENCE = "user_experience";
        static final String USER_ACHIEVEMENTS = "user_achievements";
        static final String USER_EDUCATION = "user_education";
        static final String USER_SKILLS = "user_skills";
        static final String USER_COURSE = "user_course";
        static final String USER_LANGUAGES = "user_languages";
        static final String USER_INTEREST = "user_interest";
        static final String UPLOAD_IMAGE = "upload_image";
        static final String UPLOAD_POST_IMAGES = "upload_post_images";
        static final String PHONE_NUMBER_GET_UNIV_WISE = "phone_number_get_univ_wise";

        //===========================Home==============================
        static final String CREATE_EVENT = "create_event";
        static final String CREATE_OPINION  = "create_opinion";
        static final String CREATE_POST  = "create_post";
        static final String CREATE_POLL  = "create_poll";
        static final String HOMEPAGE_DATA  = "homepage_data";
        static final String GET_POST_COMMENT  = "get_post_comment";
        static final String ADD_COMMENT  = "add_comment";
        static final String LIKE_UNLIKE_POST  = "like_unlike_post";
        static final String EVENT_LINK_COUNTER_HIT  = "event_link_counter_hit";
        static final String SELECT_POLL_OPTION  = "select_poll_option";
        static final String REPORT_POST  = "report_post";
        static final String REPORT_USER  = "report_user";
        static final String DELETEUSERPOST  = "delete-user-post";

        //===========================Brand==============================
        static final String BRANDLISTING = "show_offers/{id}";
        static final String BRAND_DATA = "brand_data";
        static final String BLOGLISTING = "show_blog/{id}";
        static final String GET_BANNER = "get_banner";
        static final String USER_BLOG_SAVED = "user_blog_saved";
        static final String INSTAPOST = "get_social_media_post";
        static final String LIKE_UNLIKE_BLOG = "like_unlike_blog";
        static final String BRAND_DETAIL = "brand_detail";
        static final String REDEEM_VOUCHER = "redeem_voucher";
        static final String CATEGORIES_WISE_OFFERS_DATA = "categories_wise_offers_data";
        //======================== chat ====================================
        static final String CHATROOM  = "chat-room";
        static final String BLOCK_USER  = "block_user";
        static final String DELETE_CHAT  = "delete_chat";
        static final String CHATDATA  = "chat_data";
        static final String CHAT_ROOM_GROUP  = "chat-room-group";
        static final String GROUP_CHAT_DATA  = "group_chat_data";
        static final String MESSAGE  = "message";
        static final String REMOVE_MEMBER_FROM_GROUP   = "remove_member_from_group";
        static final String SHOW_FRIEND_USER   = "show_friend_user";
        static final String CREATE_GROUP   = "create_group";
        static final String FRIEND_REQ_LISTING  = "friend_req_listing";
        static final String FRIEND_REQ_ACCEPT_REJECT  = "friend_req_accept_reject";

        static final String EMAIL_VERIFY  = "email_verify";
        static final String OTP_VERIFY  = "otp_verify";
        static final String GET_UNI_ID_USING_DOMAIN  = "get_uni_id_using_domain";
        static final String UNIVERSITY_SCHOOLS_LIST  = "university_schools_list";
        static final String REGISTER  = "register";
        static final String ADD_UNIVERSITY = "add_university";
        static final String FRIEND_REQUEST_SEND_LISTING = "friend_request_send_listing";
        static final String FRIENDREQUEST = "send_friend_request";
        static final String CANCEL_FRIEND_REQUEST = "cancel_friend_request";
    }
}




