package unilife.com.unilife.profile.editprofiel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.model.SelfIntroduction;
import unilife.com.unilife.profile.model.requests.UpdateProfileRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditPersonalStatementActivity extends BaseActivity {

    @BindView(R.id.txtinput1)
    TextInputLayout txtinput1;
    @BindView(R.id.edtStatement)
    EditText edtStatement;
    String strStatement = "";
    SelfIntroduction selfIntroduction;
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        selfIntroduction = getIntent().getParcelableExtra("item");
        edtStatement.setText(selfIntroduction.getPersonalDescription());
        edtStatement.setSelection(selfIntroduction.getPersonalDescription().length());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_personal_statement;
    }

    public void goBack(View view) {
        finish();
    }

    public void callService(View view) {
        strStatement = edtStatement.getText().toString().trim();

        if (strStatement.equals("")) {
            txtinput1.setError("Enter personal mission statement");
        }else {
            doCall();
        }
    }

    public void doCall() {
        if (!isNetworkConnected())
            return;

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setPersonal_mission("Personal Mission Statement");
        updateProfileRequest.setPersonal_description(strStatement);
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
}