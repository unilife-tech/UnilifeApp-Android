package unilife.com.unilife.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.model.requests.IntroRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class AddNumberActivity extends BaseActivity {

    @BindView(R.id.txtCountryName)
    TextView txtCountryName;
    @BindView(R.id.edtCountryCode)
    EditText edtCountryCode;
    @BindView(R.id.edtMobile)
    EditText edtMobile;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtCountryName.setText(sharePref.getCountryName().trim());
        edtCountryCode.setText(sharePref.getCountryCode().trim());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_number;
    }

    public void addMobileNumber() {
        if (!isNetworkConnected())
            return;

        IntroRequest introRequest = new IntroRequest();
        introRequest.setPhone(edtMobile.getText().toString().trim().replace(" ", "").replace("+91", "").replace("+971", ""));

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.profile_update(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), introRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override

            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    start(AddFromContactActivity.class);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void goNext(View view) {
        if (edtMobile.getText().toString().trim().equals("")) {
            edtMobile.setHintTextColor(Color.RED);
            edtMobile.setHint("Please enter mobile");
        } else {
            addMobileNumber();
        }
    }

    public void openCountrySelection(View view) {
        start(SelectCountryActivity.class);
    }
}