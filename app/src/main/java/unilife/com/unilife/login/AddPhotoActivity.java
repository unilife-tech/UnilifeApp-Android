package unilife.com.unilife.login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.profile.model.responses.UploadImageResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retro.WebUrls;
import unilife.com.unilife.updated.BaseActivity;

public class AddPhotoActivity extends BaseActivity {

    String strImage = "";
    Uri mImageUri;
    @BindView(R.id.imgAddProfile)
    CircleImageView imgAddProfile;
    private byte[] imageBytes = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_photo;
    }

    public void callNext(View view) {
        doRegister();
//        start(InviteActivity.class);
    }

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
                imgAddProfile.setImageURI(mImageUri);
                sharePref.setProfileImage(strImage);
//                Glide.with(mContext)
//                        .load(response.body().getUrl())
//                        .apply(options)
//                        .into(imgProfile);
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void onChooseFile(View view) {
        CropImage.activity().start(AddPhotoActivity.this);
    }

    public void doRegister() {
        if (!isNetworkConnected())
            return;

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(sharePref.getUniversityEmail());
        registerRequest.setGender(sharePref.getGender());
        registerRequest.setDate_of_birth(sharePref.getBirthday());
        registerRequest.setProfile_image(sharePref.getProfileImage());
        registerRequest.setPassword(sharePref.getPassword());
        registerRequest.setUniversity_id(sharePref.getUniversityId());
        registerRequest.setUsername(sharePref.getUsername());

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.registerUser(registerRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful() && response.body().getStatus()) {
                    sharePref.setUserId(response.body().getId());

                    PreferenceFile.getInstance().saveData(mContext, WebUrls.KEY_USERNAME, sharePref.getUsername());
                    PreferenceFile.getInstance().saveData(mContext, WebUrls.KEY_EMAIL, sharePref.getUniversityEmail());
                    PreferenceFile.getInstance().saveData(mContext, WebUrls.KEY_PROFILE_IMAGE, sharePref.getProfileImage());
                    PreferenceFile.getInstance().saveData(mContext, WebUrls.UNIVERSITY_NAME, sharePref.getUniversityName());
                    PreferenceFile.getInstance().saveData(mContext, WebUrls.KEY_PROFILE_IMAGE, sharePref.getProfileImage());
                    PreferenceFile.getInstance().saveData(mContext, WebUrls.KEY_USERID, sharePref.getUserId());
                    PreferenceFile.getInstance().saveData(mContext, WebUrls.OTP_VERIFY, "yes");

                    showToast(response.body().getMessage());
                    start(InviteActivity.class);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public class RegisterRequest {
        String university_id;
        String email;
        String username;
        String gender;
        String date_of_birth;
        String password;
        String profile_image;

        public void setUniversity_id(String university_id) {
            this.university_id = university_id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setDate_of_birth(String date_of_birth) {
            this.date_of_birth = date_of_birth;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }
    }
}