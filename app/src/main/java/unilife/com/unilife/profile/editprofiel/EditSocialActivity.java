package unilife.com.unilife.profile.editprofiel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.model.UserSocialProfile;
import unilife.com.unilife.profile.model.requests.IntroRequest;
import unilife.com.unilife.profile.model.requests.SocialRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditSocialActivity extends BaseActivity {

    String strFb;
    String strInsta;
    String strSnap;
    String strTwitter;
    String strLinkedIn;

    @BindView(R.id.edtFacebook)
    EditText edtFacebook;
    @BindView(R.id.edtInsta)
    EditText edtInsta;
    @BindView(R.id.edtSnap)
    EditText edtSnap;
    @BindView(R.id.edtTwitter)
    EditText edtTwitter;
    @BindView(R.id.edtLinkedIn)
    EditText edtLinkedIn;

    UserSocialProfile socialProfile;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        socialProfile = getIntent().getParcelableExtra("item");
        if (socialProfile != null) {
            edtFacebook.setText(socialProfile.getFacebook());
            edtInsta.setText(socialProfile.getInstagram());
            edtSnap.setText(socialProfile.getSnapchat());
            edtTwitter.setText(socialProfile.getTwitter());
            edtLinkedIn.setText(socialProfile.getLinkedin());
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_social;
    }

    public void callService(View view) {
        strFb = edtFacebook.getText().toString().trim();
        strInsta = edtInsta.getText().toString().trim();
        strSnap = edtSnap.getText().toString().trim();
        strTwitter = edtTwitter.getText().toString().trim();
        strLinkedIn = edtLinkedIn.getText().toString().trim();

        doCall();
    }

    public void doCall() {
        if (!isNetworkConnected())
            return;

        SocialRequest socialRequest = new SocialRequest();
        socialRequest.setFacebook(strFb);
        socialRequest.setInstagram(strInsta);
        socialRequest.setSnapchat(strSnap);
        socialRequest.setTwitter(strTwitter);
        socialRequest.setLinkedIn(strLinkedIn);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.user_social_profile(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), socialRequest);

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
}
