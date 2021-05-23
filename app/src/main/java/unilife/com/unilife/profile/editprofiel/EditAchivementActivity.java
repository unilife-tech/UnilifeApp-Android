package unilife.com.unilife.profile.editprofiel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.model.UserAchievement;
import unilife.com.unilife.profile.model.requests.AchievementRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditAchivementActivity extends BaseActivity implements TextWatcher {

    String strNameCertf;
    String strInst;
    String strDateOffered;
    String strDuration;

    @BindView(R.id.txtinput1)
    TextInputLayout txtinput1;
    @BindView(R.id.txtinput2)
    TextInputLayout txtinput2;
    @BindView(R.id.txtinput3)
    TextInputLayout txtinput3;
    @BindView(R.id.txtinput4)
    TextInputLayout txtinput4;

    @BindView(R.id.edtNameCertf)
    EditText edtNameCertf;
    @BindView(R.id.edtInst)
    EditText edtInst;
    @BindView(R.id.edtDateOffered)
    EditText edtDateOffered;
    @BindView(R.id.edtDuration)
    EditText edtDuration;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    String type="save";
    UserAchievement userAchievement;
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        userAchievement=getIntent().getParcelableExtra("item");

        if (userAchievement!=null) {
            type="update";
            text.setText("Edit Achievement");
            btnUpdate.setText("UPDATE");

            edtNameCertf.setText(userAchievement.getCertificateName());
            edtNameCertf.setSelection(userAchievement.getCertificateName().length());
            edtInst.setText(userAchievement.getOfferedBy());
            edtDateOffered.setText(userAchievement.getOfferedDate());
            edtDuration.setText(userAchievement.getDuration());
        }

        edtNameCertf.addTextChangedListener(this);
        edtInst.addTextChangedListener(this);
        edtDateOffered.addTextChangedListener(this);
        edtDuration.addTextChangedListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_achivement;
    }

    public void callService(View view) {
        strNameCertf = edtNameCertf.getText().toString().trim();
        strInst = edtInst.getText().toString().trim();
        strDateOffered = edtDateOffered.getText().toString().trim();
        strDuration = edtDuration.getText().toString().trim();

        if (valid())
        doCall();
    }

    boolean valid() {
        boolean isValid = true;
        if (strNameCertf.equals("")) {
            isValid = false;
            txtinput1.setError("Please enter certification name");
        } else if (strInst.equals("")) {
            isValid = false;
            txtinput2.setError("Please enter institute name");
        } else if (strDateOffered.equals("")) {
            isValid = false;
            txtinput3.setError("Please enter date of offered");
        } else if (strDuration.equals("")) {
            isValid = false;
            txtinput4.setError("Please enter duration");
        }
        return isValid;
    }


    public void doCall() {
        if (!isNetworkConnected())
            return;

        AchievementRequest achievementRequest = new AchievementRequest();
        achievementRequest.setCertificate_name(strNameCertf);
        achievementRequest.setOffered_by(strInst);
        achievementRequest.setOffered_date(strDateOffered);
        achievementRequest.setDuration(strDuration);
        achievementRequest.setType(type);
        if (type.equals("update"))
        achievementRequest.setId(userAchievement.getId());

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

    public void setDateOffered(View view) {

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mdiDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edtDateOffered.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);
        mdiDialog.show();
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
