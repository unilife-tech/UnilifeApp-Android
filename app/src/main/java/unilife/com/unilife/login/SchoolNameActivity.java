package unilife.com.unilife.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.login.model.VerifyEmail;
import unilife.com.unilife.login.response.UniversityIdResponse;
import unilife.com.unilife.login.response.UniversityListingResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class SchoolNameActivity extends BaseActivity {

    @BindView(R.id.edtSchool)
    EditText edtSchool;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtConfirmEmail)
    EditText edtConfirmEmail;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        sharePref.setUniversityName("");
        sharePref.setDomain("");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_school_name;
    }

    public void callNext(View view) {
        if (edtSchool.getText().toString().trim().equals("")) {
            edtSchool.setHint("Please enter school name");
            edtSchool.setHintTextColor(Color.RED);
        }  else if (edtEmail.getText().toString().trim().equals("")) {
            edtEmail.setHint("Please enter email");
            edtEmail.setHintTextColor(Color.RED);
        } else if (!isValidEmail(edtEmail.getText().toString().trim())) {
            showToast("Please enter valid email");
        } else if (!edtEmail.getText().toString().trim().equals(edtConfirmEmail.getText().toString().trim())) {
            showToast("Email and confirm email must be same");
        } else {
            sharePref.setUniversityEmail(edtEmail.getText().toString().trim());
            doCall();
        }
    }

    public class UniversityRequest{
        String domain;

        public void setEmail(String email) {
            this.email = email;
        }

        String email;

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }


    public void doCall() {
        if (!isNetworkConnected())
            return;

        UniversityRequest universityRequest=new UniversityRequest();
        universityRequest.setDomain(edtEmail.getText().toString().trim().split("@")[1]);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UniversityIdResponse> call = apiInterface.get_uni_id_using_domain(universityRequest);

        call.enqueue(new Callback<UniversityIdResponse>() {
            @Override

            public void onResponse(Call<UniversityIdResponse> call, Response<UniversityIdResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful() && response.body().getStatus()) {
                    sharePref.setUniversityId(response.body().getUniversityId());
                    emailVerify();
                }
            }

            @Override
            public void onFailure(Call<UniversityIdResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }


    public void emailVerify() {
        if (!isNetworkConnected())
            return;

        VerifyEmail verifyEmail = new VerifyEmail();
        verifyEmail.setEmail(sharePref.getUniversityEmail());

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.emailVerify(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), verifyEmail);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()&&response.body().getStatus()) {
//                    OTP = response.body().getOtp();
//                } else
                    start(VerifyEmailActivity.class);
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

//    @Override
//    protected void onResume() {
//        edtSchool.setText(sharePref.getUniversityName());
//        edtEmail.setText(sharePref.getDomain());
//        edtConfirmEmail.setText(sharePref.getDomain());
//        super.onResume();
//    }

//    public void openSearch(View view) {
//        start(SearchUniversity.class);
//    }
}