package unilife.com.unilife.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.ChatUsersList;
import unilife.com.unilife.profile.adapters.AchievementAdapter;
import unilife.com.unilife.profile.adapters.CoursesAdapter;
import unilife.com.unilife.profile.adapters.EducationAdapter;
import unilife.com.unilife.profile.adapters.ExperienceAdapter;
import unilife.com.unilife.profile.adapters.InterestAdapter;
import unilife.com.unilife.profile.adapters.LanguageAdapter;
import unilife.com.unilife.profile.adapters.SkillsAdapter;
import unilife.com.unilife.profile.editprofiel.EditAchivementActivity;
import unilife.com.unilife.profile.editprofiel.EditCoursesActivity;
import unilife.com.unilife.profile.editprofiel.EditEducationActivity;
import unilife.com.unilife.profile.editprofiel.EditExperienceActivity;
import unilife.com.unilife.profile.editprofiel.EditHighlightActivity;
import unilife.com.unilife.profile.editprofiel.EditInterestActivity;
import unilife.com.unilife.profile.editprofiel.EditIntroActivity;
import unilife.com.unilife.profile.editprofiel.EditLangActivity;
import unilife.com.unilife.profile.editprofiel.EditPersonalStatementActivity;
import unilife.com.unilife.profile.editprofiel.EditSkillActivity;
import unilife.com.unilife.profile.editprofiel.EditSocialActivity;
import unilife.com.unilife.profile.model.ProfileResponse;
import unilife.com.unilife.profile.model.requests.AchievementRequest;
import unilife.com.unilife.profile.model.requests.EducationRequest;
import unilife.com.unilife.profile.model.requests.ExperienceRequest;
import unilife.com.unilife.profile.model.requests.UpdateProfileRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.profile.model.responses.UploadImageResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class MyAccountActivity extends BaseActivity {

    @BindView(R.id.recycvlerView4)
    RecyclerView recycvlerView4;
    @BindView(R.id.recycvlerView5)
    RecyclerView recycvlerView5;
    @BindView(R.id.recycvlerView6)
    RecyclerView recycvlerView6;
    @BindView(R.id.recycvlerView7)
    RecyclerView recycvlerView7;
    @BindView(R.id.recycvlerView8)
    RecyclerView recycvlerView8;
    @BindView(R.id.recycvlerView9)
    RecyclerView recycvlerView9;
    @BindView(R.id.recyclerCourse)
    RecyclerView recyclerCourse;

    @BindView(R.id.txtCurrentWorking)
    TextView txtCurrentWorking;
    @BindView(R.id.txtcurrentStudying)
    TextView txtcurrentStudying;
    @BindView(R.id.txtLivesIn)
    TextView txtLivesIn;
    @BindView(R.id.txtFrom)
    TextView txtFrom;
    @BindView(R.id.txtPersonalMission)
    TextView txtPersonalMission;
    @BindView(R.id.btnEditStatement)
    TextView btnEditStatement;

    @BindView(R.id.txtProfileLink)
    TextView txtProfileLink;
    @BindView(R.id.txtUserName)
    TextView txtUserName;

    ExperienceAdapter experienceAdapter;
    AchievementAdapter achievementAdapter;
    EducationAdapter educationAdapter;

    SkillsAdapter skillsAdapter;
    LanguageAdapter languageAdapter;
    InterestAdapter interestAdapter;
    CoursesAdapter coursesAdapter;

    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtAbout)
    TextView txtAbout;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    ProfileResponse profileResponse;

    private String strImage = "";
    private byte[] imageBytes = null;
    private Uri mImageUri;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        recycvlerView4.setNestedScrollingEnabled(false);
        recycvlerView5.setNestedScrollingEnabled(false);
        recycvlerView6.setNestedScrollingEnabled(false);
        recycvlerView7.setNestedScrollingEnabled(false);
        recycvlerView8.setNestedScrollingEnabled(false);
        recycvlerView9.setNestedScrollingEnabled(false);
        recyclerCourse.setNestedScrollingEnabled(false);

        experienceAdapter = new ExperienceAdapter(mContext, 1);
        achievementAdapter = new AchievementAdapter(mContext, 1);
        educationAdapter = new EducationAdapter(mContext, 1);

        skillsAdapter = new SkillsAdapter(mContext);
        languageAdapter = new LanguageAdapter(mContext);
        interestAdapter = new InterestAdapter(mContext);
        coursesAdapter = new CoursesAdapter(mContext);

        recycvlerView4.setHasFixedSize(true);
        recycvlerView5.setHasFixedSize(true);
        recycvlerView6.setHasFixedSize(true);
        recycvlerView7.setHasFixedSize(true);
        recycvlerView8.setHasFixedSize(true);
        recycvlerView9.setHasFixedSize(true);

        recycvlerView4.setLayoutManager(new LinearLayoutManager(mContext));
        recycvlerView5.setLayoutManager(new LinearLayoutManager(mContext));
        recycvlerView6.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycvlerView7.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycvlerView8.setLayoutManager(new LinearLayoutManager(mContext));
        recycvlerView9.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerCourse.setLayoutManager(new GridLayoutManager(mContext, 3));

        recycvlerView4.setAdapter(experienceAdapter);
        recycvlerView5.setAdapter(educationAdapter);
        recycvlerView8.setAdapter(achievementAdapter);

        recycvlerView6.setAdapter(skillsAdapter);
        recycvlerView7.setAdapter(languageAdapter);
        recycvlerView9.setAdapter(interestAdapter);
        recyclerCourse.setAdapter(coursesAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_account;
    }

    @Override
    protected void onResume() {
        getProfile();
        super.onResume();
    }

    public void getProfile() {
        if (!isNetworkConnected())
            return;

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileResponse> call = apiInterface.getProfile(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID));

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    experienceAdapter.updateData(response.body().getRespoonse().getUserExperience());
                    educationAdapter.updateData(response.body().getRespoonse().getUserEducation());
                    achievementAdapter.updateData(response.body().getRespoonse().getUserAchievements());

                    skillsAdapter.updateData(response.body().getRespoonse().getUserSkills());
                    languageAdapter.updateData(response.body().getRespoonse().getUserLanguages());
                    interestAdapter.updateData(response.body().getRespoonse().getUserInterest());

                    coursesAdapter.updateData(response.body().getRespoonse().getUserCourses());
                    setUpData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void goBack(View view) {
        finish();
    }

    private void setUpData(ProfileResponse profileResponse1) {
        profileResponse = profileResponse1;
        txtName.setText(profileResponse.getSelfIntroduction().getUsername());
        txtAbout.setText(profileResponse.getSelfIntroduction().getDesignation() + " at " + profileResponse.getSelfIntroduction().getOrganisation());
        sharePref.setAbout(profileResponse.getSelfIntroduction().getDesignation() + " at " + profileResponse.getSelfIntroduction().getOrganisation());
        txtPersonalMission.setText(profileResponse.getSelfIntroduction().getPersonalDescription());
//        Glide.with(this).load(selfIntoduction.getProfileLogo()).into(imgBanner);
//        Glide.with(this).load(selfIntoduction.getProfileImage()).into(imgProfile);

        txtUserName.setText(profileResponse.getSelfIntroduction().getUsername() + "'s profile");
        txtProfileLink.setText(profileResponse.getSelfIntroduction().getUnilifeUserName());

        if (profileResponse1.getRespoonse().getUserHighlights().size() > 0) {
            txtCurrentWorking.setText(profileResponse1.getRespoonse().getUserHighlights().get(0).getCurrentlyWorking());
            txtcurrentStudying.setText(profileResponse1.getRespoonse().getUserHighlights().get(0).getCurrentlyStudying());
            txtLivesIn.setText(profileResponse1.getRespoonse().getUserHighlights().get(0).getLivesIn());
            txtFrom.setText(profileResponse1.getRespoonse().getUserHighlights().get(0).getFrom());
        }

        RequestOptions defultban = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_back)
                .error(R.drawable.ic_profile_back)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide
                .with(mContext)
                .load("http://15.206.103.14/public/profile_imgs/" + profileResponse1.getSelfIntroduction().getProfileBannerImage())
                .apply(defultban)
                .into(imgBanner);

        Glide
                .with(mContext)
                .load(profileResponse1.getSelfIntroduction().getProfileImage())
                .apply(options)
                .into(imgProfile);

    }

    public void openEditIntro(View view) {
        startActivity(new Intent(mContext, EditIntroActivity.class).putExtra("item", profileResponse.getSelfIntroduction()));
    }

    public void openEditStatement(View view) {
        startActivity(new Intent(mContext, EditPersonalStatementActivity.class).putExtra("item", profileResponse.getSelfIntroduction()));
    }

    public void openEditHighlights(View view) {
        if (profileResponse.getRespoonse().getUserHighlights().size() > 0)
            startActivity(new Intent(this, EditHighlightActivity.class).putExtra("item", profileResponse.getRespoonse().getUserHighlights().get(0)));
        else
            startActivity(new Intent(this, EditHighlightActivity.class));
    }

    public void openEditExperience(View view) {
        startActivity(new Intent(this, EditExperienceActivity.class));
    }

    public void openEditEducation(View view) {
        startActivity(new Intent(this, EditEducationActivity.class));
    }

    public void openEditSkills(View view) {
        startActivity(new Intent(this, EditSkillActivity.class));
    }

    public void openEditLang(View view) {
        startActivity(new Intent(this, EditLangActivity.class));
    }

    public void openEditAchivement(View view) {
        startActivity(new Intent(this, EditAchivementActivity.class));
    }

    public void openEditInterest(View view) {
        startActivity(new Intent(this, EditInterestActivity.class));
    }

    public void openEditSocial(View view) {
        startActivity(new Intent(this, EditSocialActivity.class).putExtra("item", profileResponse.getRespoonse().getUserSocialProfile().get(0)));
    }

    public void openFacebook(View view) {
        if (profileResponse.getRespoonse().getUserSocialProfile() == null || profileResponse.getRespoonse().getUserSocialProfile().size() < 1) {
            startActivity(new Intent(this, EditSocialActivity.class));
        } else if (profileResponse.getRespoonse().getUserSocialProfile().get(0).getFacebook().contains("http")) {
            Uri uri = Uri.parse(profileResponse.getRespoonse().getUserSocialProfile().get(0).getFacebook()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            gotoSocial();
        }
    }

    public void openInsta(View view) {
        if (profileResponse.getRespoonse().getUserSocialProfile() != null && profileResponse.getRespoonse().getUserSocialProfile().size() < 1) {
            startActivity(new Intent(this, EditSocialActivity.class));
        } else if (profileResponse.getRespoonse().getUserSocialProfile().get(0).getInstagram().contains("http")) {
            Uri uri = Uri.parse(profileResponse.getRespoonse().getUserSocialProfile().get(0).getInstagram()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            gotoSocial();
        }
    }

    public void openSharechat(View view) {
        if (profileResponse.getRespoonse().getUserSocialProfile() != null && profileResponse.getRespoonse().getUserSocialProfile().size() < 1) {
            startActivity(new Intent(this, EditSocialActivity.class));
        } else if (profileResponse.getRespoonse().getUserSocialProfile().get(0).getSnapchat().contains("http")) {
            Uri uri = Uri.parse(profileResponse.getRespoonse().getUserSocialProfile().get(0).getSnapchat()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            gotoSocial();
        }
    }

    public void openTwitter(View view) {
        if (profileResponse.getRespoonse().getUserSocialProfile() != null && profileResponse.getRespoonse().getUserSocialProfile().size() < 1) {
            startActivity(new Intent(this, EditSocialActivity.class));
        } else if (profileResponse.getRespoonse().getUserSocialProfile().get(0).getTwitter().contains("http")) {
            Uri uri = Uri.parse(profileResponse.getRespoonse().getUserSocialProfile().get(0).getTwitter()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            gotoSocial();
        }
    }

    //==============================================================================================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == Activity.RESULT_OK) {
                mImageUri = result.getUri();
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(mImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    imageBytes = getBytes(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (imageBytes != null) {
                    uploadImage();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception e = result.getError();
                Toast.makeText(mContext, "Possible error is: " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }

    public void uploadImage() {
        if (!isNetworkConnected())
            return;

        MultipartBody.Part image = null;
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        image = MultipartBody.Part.createFormData("club_image", "image.jpg", requestFile);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UploadImageResponse> call = apiInterface.uploadImage(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERID), image);

        showProgressDialog();
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.body());
                strImage = response.body().getData();

//                Glide.with(mContext)
//                        .load(response.body().getUrl())
//                        .into(imgBanner)
//                  ;

                imgBanner.setImageURI(mImageUri);
                doCall();

            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void doCall() {
        if (!isNetworkConnected())
            return;

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setProfile_banner_image(strImage);
//        updateProfileRequest.setPersonal_mission("Personal Mission Statement");
//        updateProfileRequest.setPersonal_description(strStatement);
//        introRequest.setFull_name(strFName + " " + strLName);
//        introRequest.setDesignation(strHeadline);
//        introRequest.setOrganisation(strStatus);
//        introRequest.setProfile_image(strImage);
//
        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.personalMissionUpdate(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), updateProfileRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }


    public void onChooseFile(View view) {
        CropImage.activity().start(MyAccountActivity.this);
    }

    public void openEditCourses(View view) {
        startActivity(new Intent(this, EditCoursesActivity.class));
    }

    public void openLinkedIn(View view) {
        if (profileResponse.getRespoonse().getUserSocialProfile() != null && profileResponse.getRespoonse().getUserSocialProfile().size() < 1) {
            startActivity(new Intent(this, EditSocialActivity.class));
        } else if (profileResponse.getRespoonse().getUserSocialProfile().get(0).getLinkedin().contains("http")) {
            Uri uri = Uri.parse(profileResponse.getRespoonse().getUserSocialProfile().get(0).getLinkedin()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            gotoSocial();
        }
    }

    private void gotoSocial() {
        startActivity(new Intent(this, EditSocialActivity.class).putExtra("item", profileResponse.getRespoonse().getUserSocialProfile().get(0)));
    }

    public void deleteAchievement(String id) {
        if (!isNetworkConnected())
            return;

        AchievementRequest achievementRequest = new AchievementRequest();
        achievementRequest.setType("delete");
        achievementRequest.setId(id);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.user_achievements(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), achievementRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    showToast(response.body().getMessage());
                    getProfile();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void deleteEducation(String id) {
        if (!isNetworkConnected())
            return;

        EducationRequest educationRequest = new EducationRequest();
        educationRequest.setType("delete");
        educationRequest.setId(id);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.user_education(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), educationRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    showToast(response.body().getMessage());
                    getProfile();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void deleteExperience(String id) {
        if (!isNetworkConnected())
            return;

        ExperienceRequest experienceRequest = new ExperienceRequest();
        experienceRequest.setType("delete");
        experienceRequest.setId(id);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.user_experience(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), experienceRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    showToast(response.body().getMessage());
                    getProfile();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void openFriendList(View view) {
        intent = new Intent(mContext, ChatUsersList.class);
        intent.putExtra("key", "view_friends");
        startActivity(intent);
    }
}
