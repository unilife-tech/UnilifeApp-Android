package unilife.com.unilife.retro;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import unilife.com.unilife.blogs.BlogDetailsActivity;
import unilife.com.unilife.blogs.request.GetBannerRequest;
import unilife.com.unilife.blogs.response.BannerResponse;
import unilife.com.unilife.blogs.response.BlogResponse;
import unilife.com.unilife.blogs.response.InstagramResponse;
import unilife.com.unilife.brands.BrandDetailsNew;
import unilife.com.unilife.brands.BrandViewAllActivity;
import unilife.com.unilife.brands.newbrandresponse.BrandResponse2;
import unilife.com.unilife.brands.request.RedeemRequest;
import unilife.com.unilife.brands.response.BrandDetailsResponse;
import unilife.com.unilife.brands.response.CategoryWiseBrandResponse;
import unilife.com.unilife.chat.CreateGroupActivity;
import unilife.com.unilife.chat.ReceivedRequestActivity;
import unilife.com.unilife.chat.model.ChatDetailRequest;
import unilife.com.unilife.chat.model.ExitGroupRequest;
import unilife.com.unilife.chat.model.FriendsResponse;
import unilife.com.unilife.chat.model.ReceivedRequestResponse;
import unilife.com.unilife.chat.update.FriendDetailActivity;
import unilife.com.unilife.chat.update.chatresponse.ChatResponse;
import unilife.com.unilife.chat.update.groupresponse.GroupChatResponse;
import unilife.com.unilife.chat.update.groupresponse.GroupRoomResponse;
import unilife.com.unilife.home.Home;
import unilife.com.unilife.home.requests.DeletePostRequest;
import unilife.com.unilife.home.requests.ReportPostRequest;
import unilife.com.unilife.home.requests.SelectPollRequest;
import unilife.com.unilife.home.responses.HomeResponse;
import unilife.com.unilife.home.responses.LikeUnlikeResponse;
import unilife.com.unilife.home.responses.comment.CommentResponse;
import unilife.com.unilife.login.AddPhotoActivity;
import unilife.com.unilife.login.InviteActivity;
import unilife.com.unilife.login.SchoolNameActivity;
import unilife.com.unilife.login.SearchUniversity;
import unilife.com.unilife.login.VerifyEmailActivity;
import unilife.com.unilife.login.model.FriendRequest;
import unilife.com.unilife.login.model.VerifyEmail;
import unilife.com.unilife.login.response.UniversityIdResponse;
import unilife.com.unilife.login.response.UniversityListingResponse;
import unilife.com.unilife.login.response.UserPhonesResponse;
import unilife.com.unilife.profile.model.ProfileResponse;
import unilife.com.unilife.profile.model.requests.AchievementRequest;
import unilife.com.unilife.profile.model.requests.AddSkillRequest;
import unilife.com.unilife.profile.model.requests.EducationRequest;
import unilife.com.unilife.profile.model.requests.ExperienceRequest;
import unilife.com.unilife.profile.model.requests.HighlightsRequest;
import unilife.com.unilife.profile.model.requests.IntroRequest;
import unilife.com.unilife.profile.model.requests.SearchRequest;
import unilife.com.unilife.profile.model.requests.SocialRequest;
import unilife.com.unilife.profile.model.requests.UpdateProfileRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse2;
import unilife.com.unilife.profile.model.responses.CourseResponse;
import unilife.com.unilife.profile.model.responses.DeletePostResponse;
import unilife.com.unilife.profile.model.responses.HighlightResponse;
import unilife.com.unilife.profile.model.responses.InterestResponse;
import unilife.com.unilife.profile.model.responses.LanguageResponse;
import unilife.com.unilife.profile.model.responses.SkillsResponse;
import unilife.com.unilife.profile.model.responses.UploadImageResponse;
import unilife.com.unilife.updated.CommentsActivity;
import unilife.com.unilife.updated.model.requests.CreatePostRequest;
import unilife.com.unilife.updated.model.requests.EventRequest;
import unilife.com.unilife.updated.model.requests.PollRequest;

public interface ApiInterface {

    //=====================================Profile========================================================
    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.GETPROFILE)
    Call<ProfileResponse> getProfile(@Header("Token") String token);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_HIGHLIGHTS)
    Call<HighlightResponse> user_highlights(@Header("Token") String token, @Body HighlightsRequest highlightsRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.PROFILE_UPDATE)
    Call<CommonResponse> profile_update(@Header("Token") String token, @Body IntroRequest introRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.PHONE_NUMBER_GET_UNIV_WISE)
    Call<UserPhonesResponse> phone_number_get_univ_wise(@Header("Token") String token);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.PROFILE_UPDATE)
    Call<CommonResponse> profileUpdate2(@Header("Token") String token, @Body UpdateProfileRequest profileRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.PERSONAL_MISSION_UPDATE)
    Call<CommonResponse> personalMissionUpdate(@Header("Token") String token, @Body UpdateProfileRequest profileRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_SOCIAL_PROFILE)
    Call<CommonResponse> user_social_profile(@Header("Token") String token, @Body SocialRequest socialRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_EXPERIENCE)
    Call<CommonResponse> user_experience(@Header("Token") String token, @Body ExperienceRequest experienceRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_ACHIEVEMENTS)
    Call<CommonResponse> user_achievements(@Header("Token") String token, @Body AchievementRequest achievementRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_EDUCATION)
    Call<CommonResponse> user_education(@Header("Token") String token, @Body EducationRequest educationRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_SKILLS)
    Call<SkillsResponse> user_skills(@Header("Token") String token, @Body SearchRequest searchRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_SKILLS)
    Call<SkillsResponse> addNewSkills(@Header("Token") String token, @Body AddSkillRequest skillRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_COURSE)
    Call<CourseResponse> user_course(@Header("Token") String token, @Body SearchRequest searchRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_COURSE)
    Call<CourseResponse> addNewCourse(@Header("Token") String token, @Body AddSkillRequest searchRequest);


    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_LANGUAGES)
    Call<LanguageResponse> user_languages(@Header("Token") String token, @Body SearchRequest searchRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_LANGUAGES)
    Call<LanguageResponse> addNewLanguage(@Header("Token") String token, @Body AddSkillRequest skillRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_INTEREST)
    Call<InterestResponse> user_interest(@Header("Token") String token, @Body SearchRequest searchRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_INTEREST)
    Call<InterestResponse> addNewInterest(@Header("Token") String token, @Body AddSkillRequest skillRequest);

    @Multipart
    @POST(ApiClient.SubUrls.UPLOAD_IMAGE)
    Call<UploadImageResponse> uploadImage(@Header("Token") String token, @Part MultipartBody.Part image);

    @Multipart
    @POST(ApiClient.SubUrls.UPLOAD_POST_IMAGES)
    Call<UploadImageResponse> upload_post_images(@Header("Token") String token,
                                                 @Part("type") RequestBody description,
                                                 @Part MultipartBody.Part image);

    @Multipart
    @POST(ApiClient.SubUrls.UPLOAD_POST_IMAGES)
    Call<UploadImageResponse> uploadVideo(@Header("Token") String token,
                                          @Part("type") RequestBody description,
                                          @Part MultipartBody.Part image);

    //=====================================Home========================================================
    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CREATE_EVENT)
    Call<CommonResponse> createEvent(@Header("Token") String token, @Body EventRequest eventRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CREATE_OPINION)
    Call<CommonResponse> addOpinion(@Header("Token") String token, @Body CreatePostRequest opinionRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CREATE_POST)
    Call<CommonResponse> createPost(@Header("Token") String token, @Body CreatePostRequest createPostRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CREATE_POLL)
    Call<CommonResponse> createPoll(@Header("Token") String token, @Body PollRequest pollRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.HOMEPAGE_DATA)
    Call<HomeResponse> getHomeData(@Header("Token") String token, @Body Home.HomeRequest homeRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.GET_POST_COMMENT)
    Call<CommentResponse> getPostComment(@Header("Token") String token, @Body CommentsActivity.GetCommentRequest commentRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.ADD_COMMENT)
    Call<CommentResponse> addComment(@Header("Token") String token, @Body CommentsActivity.CommentRequest commentRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.LIKE_UNLIKE_POST)
    Call<LikeUnlikeResponse> likePost(@Header("Token") String token, @Body Home.LikeRequest likeRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.EVENT_LINK_COUNTER_HIT)
    Call<CommonResponse> registerEvent(@Header("Token") String token, @Body Home.EventCountRegister eventCountRegister);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.REPORT_POST)
    Call<CommonResponse> reportPost(@Header("Token") String token, @Body ReportPostRequest reportPostRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.REPORT_USER)
    Call<CommonResponse> reportUser(@Header("Token") String token, @Body ReportPostRequest reportPostRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.DELETEUSERPOST)
    Call<DeletePostResponse> deletePost(@Header("Token") String token, @Body DeletePostRequest deletePostRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.SELECT_POLL_OPTION)
    Call<CommonResponse> selectPollOption(@Header("Token") String token, @Body SelectPollRequest selectPollRequest);

    //=================================== Brand ======================================================
    @Headers("Content-Type: application/json")
    @GET(ApiClient.SubUrls.BRAND_DATA)
    Call<BrandResponse2> getBrands(@Header("Token") String token);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.BRAND_DETAIL)
    Call<BrandDetailsResponse> getBrandsDetails(@Header("Token") String token, @Body BrandDetailsNew.BrandDetailsRequest brandDetailsRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.REDEEM_VOUCHER)
    Call<CommonResponse> redeemVoucher(@Header("Token") String token, @Body RedeemRequest redeemRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CATEGORIES_WISE_OFFERS_DATA)
    Call<CategoryWiseBrandResponse> categories_wise_offers_data(@Header("Token") String token, @Body BrandViewAllActivity.CategoryWiseBrandRequest redeemRequest);

    //=================================== Blog ======================================================
    @Headers("Content-Type: application/json")
    @GET(ApiClient.SubUrls.BLOGLISTING)
    Call<BlogResponse> getBlogs(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.GET_BANNER)
    Call<BannerResponse> getBanner(@Header("Token") String token, @Body GetBannerRequest bannerRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.USER_BLOG_SAVED)
    Call<CommonResponse> userBlogSaved(@Header("Token") String token, @Body BlogDetailsActivity.SaveBlog saveBlog);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.INSTAPOST)
    Call<InstagramResponse> getInstaPost(@Header("Token") String token, @Body BlogDetailsActivity.InstaPostRequest instaPostRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.LIKE_UNLIKE_BLOG)
    Call<CommonResponse> likeUnlikeBlog(@Header("Token") String token, @Body BlogDetailsActivity.SaveBlog saveBlog);

    //=================================== chat ======================================================
    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CHATROOM)
    Call<GroupRoomResponse> createRoom(@Header("Token") String token, @Body ChatDetailRequest chatDetailRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.BLOCK_USER)
    Call<CommonResponse2> blockUser(@Header("Token") String token, @Body FriendDetailActivity.BlockRequest blockRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.DELETE_CHAT)
    Call<CommonResponse2> deleteChat(@Header("Token") String token, @Body FriendDetailActivity.BlockRequest blockRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CHATDATA)
    Call<ChatResponse> getChatDetails(@Header("Token") String token, @Body ChatDetailRequest chatDetailRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CHAT_ROOM_GROUP)
    Call<GroupRoomResponse> createGroupRoom(@Header("Token") String token, @Body ChatDetailRequest chatDetailRequest);


    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.FRIEND_REQ_LISTING)
    Call<ReceivedRequestResponse> getRequest(@Header("Token") String token);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.FRIEND_REQ_ACCEPT_REJECT)
    Call<CommonResponse> reqAcceptReject(@Header("Token") String token, @Body ReceivedRequestActivity.AcceptRejectRequest acceptRejectRequest);

    //============================================================================================

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.GROUP_CHAT_DATA)
    Call<GroupChatResponse> getGroupAllData(@Header("Token") String token, @Body ChatDetailRequest chatDetailRequest);

//    @Headers("Content-Type: application/json")
//    @POST(ApiClient.SubUrls.MESSAGE)
//    Call<ChatResponse> sendTextMessage(@Header("Token") String token, @Body SendMsgRequest sendMsgRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.REMOVE_MEMBER_FROM_GROUP)
    Call<CommonResponse> exitGroup(@Header("Token") String token, @Body ExitGroupRequest exitGroupRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.SHOW_FRIEND_USER)
    Call<FriendsResponse> showFriendList(@Header("Token") String token, @Body CreateGroupActivity.FriendRequest friendRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CREATE_GROUP)
    Call<CommonResponse> createGroup(@Header("Token") String token, @Body CreateGroupActivity.CreateGroupRequest createGroupRequest);

    //========================================================= Login =======================================
    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.GET_UNI_ID_USING_DOMAIN)
    Call<UniversityIdResponse> get_uni_id_using_domain(@Body SchoolNameActivity.UniversityRequest universityRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.EMAIL_VERIFY)
    Call<CommonResponse> emailVerify(@Header("Token") String token, @Body VerifyEmail verifyEmail);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.OTP_VERIFY)
    Call<CommonResponse> otpVerify(@Header("Token") String token, @Body VerifyEmail verifyEmail);

    @Headers("Content-Type: application/json")
    @GET(ApiClient.SubUrls.UNIVERSITY_SCHOOLS_LIST)
    Call<UniversityListingResponse> getUniversityListing();

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.REGISTER)
    Call<CommonResponse> registerUser(@Body AddPhotoActivity.RegisterRequest registerRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.ADD_UNIVERSITY)
    Call<CommonResponse> addUniversity(@Body SearchUniversity.AddUniversityRequest addUniversityRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.FRIEND_REQUEST_SEND_LISTING)
    Call<unilife.com.unilife.login.model.FriendsResponse> friendRequestListing(@Header("Token") String token, @Body InviteActivity.FriendListRequest friendListRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.FRIENDREQUEST)
    Call<CommonResponse> friendRequest(@Header("Token") String token, @Body FriendRequest friendRequest);

    @Headers("Content-Type: application/json")
    @POST(ApiClient.SubUrls.CANCEL_FRIEND_REQUEST)
    Call<CommonResponse> cancelRequest(@Header("Token") String token, @Body FriendRequest friendRequest);

}


