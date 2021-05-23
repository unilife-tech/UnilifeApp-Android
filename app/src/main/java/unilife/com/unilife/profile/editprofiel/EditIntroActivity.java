package unilife.com.unilife.profile.editprofiel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import unilife.com.unilife.profile.model.SelfIntroduction;
import unilife.com.unilife.profile.model.requests.IntroRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.profile.model.responses.UploadImageResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditIntroActivity extends BaseActivity implements TextWatcher {

    String strFName;
    String strLName;
    String strStatus;
    String strHeadline;

    @BindView(R.id.txtinput1)
    TextInputLayout txtinput1;
    @BindView(R.id.txtinput2)
    TextInputLayout txtinput2;
    @BindView(R.id.txtinput3)
    TextInputLayout txtinput3;
    @BindView(R.id.txtinput4)
    TextInputLayout txtinput4;

    @BindView(R.id.edtFName)
    EditText edtFName;
    @BindView(R.id.edtLName)
    EditText edtLName;
    @BindView(R.id.edtStatus)
    EditText edtStatus;
    @BindView(R.id.edtHeadline)
    EditText edtHeadline;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    SelfIntroduction selfIntroduction;
    String strImage = "";
    Uri mImageUri;
    private byte[] imageBytes = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        selfIntroduction = getIntent().getParcelableExtra("item");
        init();
    }

    private void init() {

        edtFName.addTextChangedListener(this);
        edtLName.addTextChangedListener(this);
        edtStatus.addTextChangedListener(this);
        edtHeadline.addTextChangedListener(this);

        if (selfIntroduction.getUsername().contains(" ")) {
            edtFName.setText(selfIntroduction.getUsername().split(" ")[0]);
            if (selfIntroduction.getUsername().split(" ").length > 1)
                edtLName.setText(selfIntroduction.getUsername().split(" ")[1]);
        } else {
            edtFName.setText(selfIntroduction.getUsername());
        }

        edtFName.setSelection(edtFName.getText().toString().trim().length());

        edtHeadline.setText(selfIntroduction.getOrganisation());
        edtStatus.setText(selfIntroduction.getDesignation());

        Glide.with(mContext)
                .load(selfIntroduction.getProfileImage())
                .apply(options)
                .into(imgProfile);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_intro;
    }

    public void callService(View view) {
        strFName = edtFName.getText().toString().trim();
        strLName = edtLName.getText().toString().trim();
        strStatus = edtStatus.getText().toString().trim();
        strHeadline = edtHeadline.getText().toString().trim();

        if (valid())
            doCall();
    }

    boolean valid() {
        boolean isValid = true;
        if (strFName.equals("")) {
            isValid = false;
            txtinput1.setError("Please enter first name");
        } else if (strLName.equals("")) {
            isValid = false;
            txtinput2.setError("Please enter last name");
        } else if (strStatus.equals("")) {
            isValid = false;
            txtinput3.setError("Please enter status");
        } else if (strHeadline.equals("")) {
            isValid = false;
            txtinput4.setError("Please enter headline");
        }
        return isValid;
    }


    public void doCall() {
        if (!isNetworkConnected())
            return;

        IntroRequest introRequest = new IntroRequest();
        introRequest.setFull_name(strFName + " " + strLName);
        introRequest.setDesignation(strStatus);
        introRequest.setOrganisation(strHeadline);
        introRequest.setProfile_image(strImage);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.profile_update(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), introRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override

            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    showToast(response.body().getMessage());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void goBack(View view) {
        finish();
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

                Glide.with(mContext)
                        .load(response.body().getUrl())
                        .apply(options)
                        .into(imgProfile);
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void onChooseFile(View view) {
        CropImage.activity().start(EditIntroActivity.this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        txtinput1.setErrorEnabled(false);
        txtinput2.setErrorEnabled(false);
        txtinput3.setErrorEnabled(false);
        txtinput4.setErrorEnabled(false);
    }
}
