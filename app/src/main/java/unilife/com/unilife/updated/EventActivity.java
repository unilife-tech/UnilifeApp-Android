package unilife.com.unilife.updated;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.profile.model.responses.SkillsResponse;
import unilife.com.unilife.profile.model.responses.UploadImageResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retrofit.WebUrls;
import unilife.com.unilife.updated.model.requests.EventRequest;

public class EventActivity extends BaseActivity {

    @BindView(R.id.edtEventTitle)
    EditText edtEventTitle;
    @BindView(R.id.edtEventLink)
    EditText edtEventLink;
    @BindView(R.id.edtEventDesc)
    EditText edtEventDesc;
    @BindView(R.id.imgEvent)
    ImageView imgEvent;
    String strImage = "";
    Uri mImageUri;
    private String strTitle;
    private String strLink;
    private String strDesc;
    private byte[] imageBytes = null;

    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtName)
    TextView txtName;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        txtName.setText(   PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERNAME));
        try {
            Glide
                    .with(mContext)
                    .load(WebUrls.INSTANCE.getPROFILE_IMAGE_URL() + PreferenceFile.getInstance().getPreferenceData(this, KEY_PROFILE_IMAGE))
                    .apply(options)
                    .into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_event;
    }

    public void closeView(View view) {
        finish();
    }

    public void addEvent() {
        if (!isNetworkConnected())
            return;

        EventRequest eventRequest = new EventRequest();
        eventRequest.setEvent_title(strTitle);
        eventRequest.setEvent_link(strLink);
        eventRequest.setEvent_description(strDesc);
        eventRequest.setEvent_images(strImage);


        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.createEvent(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), eventRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    finish();
                }
                showToast(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void postCall(View view) {
        strTitle = edtEventTitle.getText().toString().trim();
        strLink = edtEventLink.getText().toString().trim();
        strDesc = edtEventDesc.getText().toString().trim();

        if (valid())
        addEvent();
    }

    boolean valid() {
        boolean isValid = true;
        if (strTitle.equals("")) {
            isValid = false;
            edtEventTitle.setHint("Enter event title");
            edtEventTitle.setHintTextColor(getResources().getColor(R.color.medium_red));
        }/* else if (strLink.equals("")) {
            isValid = false;
            edtEventLink.setHint("Enter event registration link");
            edtEventLink.setHintTextColor(getResources().getColor(R.color.medium_red));
        } */else if (strDesc.equals("")){
            isValid = false;
            edtEventDesc.setHint("Enter event description");
            edtEventDesc.setHintTextColor(getResources().getColor(R.color.medium_red));
        }
        return isValid;
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
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"),"image" );

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UploadImageResponse> call = apiInterface.upload_post_images(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERID),type, image);


        showProgressDialog();
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.body());
                strImage = response.body().getData();

                findViewById(R.id.img3).setVisibility(View.GONE);
                findViewById(R.id.text4).setVisibility(View.GONE);

                imgEvent.setImageURI(mImageUri);
//                Glide.with(mContext)
//                        .load(response.body().getUrl())
//                        .apply(options)
//                        .into(imgEvent);

            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void onChooseFile(View view) {
        CropImage.activity().start(EventActivity.this);
    }
}
