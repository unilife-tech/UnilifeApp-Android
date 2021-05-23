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
import unilife.com.unilife.profile.model.UserEducation;
import unilife.com.unilife.profile.model.requests.EducationRequest;
import unilife.com.unilife.profile.model.requests.ExperienceRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditEducationActivity extends BaseActivity implements TextWatcher {

    String strCollegeName;
    String strConcentration;
    String strDegree;
    String strClubandSocieties;
    String strGrade;
    String strStartDate;
    String strEndDate;

    @BindView(R.id.edtCollegeName)
    EditText edtCollegeName;
    @BindView(R.id.edtConcentration)
    EditText edtConcentration;
    @BindView(R.id.edtDegree)
    EditText edtDegree;
    @BindView(R.id.edtClubandSocieties)
    EditText edtClubandSocieties;
    @BindView(R.id.edtGrade)
    EditText edtGrade;
    @BindView(R.id.edtStartDate)
    EditText edtStartDate;
    @BindView(R.id.edtEndDate)
    EditText edtEndDate;
    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.txtinput1)
    TextInputLayout txtinput1;
    @BindView(R.id.txtinput2)
    TextInputLayout txtinput2;
    @BindView(R.id.txtinput3)
    TextInputLayout txtinput3;
    @BindView(R.id.txtinput4)
    TextInputLayout txtinput4;
    @BindView(R.id.txtinput5)
    TextInputLayout txtinput5;
    @BindView(R.id.txtinput6)
    TextInputLayout txtinput6;
    @BindView(R.id.txtinput7)
    TextInputLayout txtinput7;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    UserEducation userEducation;
    String type="save";
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        userEducation=getIntent().getParcelableExtra("item");

        if (userEducation!=null) {
            type="update";
            text.setText("Edit Education");
            btnUpdate.setText("UPDATE");

            edtCollegeName.setText(userEducation.getCollegeName());
            edtCollegeName.setSelection(userEducation.getCollegeName().length());
            edtConcentration.setText(userEducation.getConcentration());
            edtDegree.setText(userEducation.getDegree());
            edtClubandSocieties.setText(userEducation.getClubSociety());
            edtStartDate.setText(userEducation.getStartDate());
            edtEndDate.setText(userEducation.getEndDate());
            edtGrade.setText(userEducation.getGrade());
        }

        edtCollegeName.addTextChangedListener(this);
        edtConcentration.addTextChangedListener(this);
        edtDegree.addTextChangedListener(this);
        edtClubandSocieties.addTextChangedListener(this);
        edtStartDate.addTextChangedListener(this);
        edtEndDate.addTextChangedListener(this);
        edtGrade.addTextChangedListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_education;
    }

    public void callService(View view) {
        strCollegeName = edtCollegeName.getText().toString().trim();
        strConcentration=edtConcentration.getText().toString().trim();
        strDegree = edtDegree.getText().toString().trim();
        strClubandSocieties = edtClubandSocieties.getText().toString().trim();
        strGrade = edtGrade.getText().toString().trim();
        strStartDate = edtStartDate.getText().toString().trim();
        strEndDate = edtEndDate.getText().toString().trim();

        if (valid())
        doCall();
    }


    boolean valid() {
        boolean isValid = true;
        if (strCollegeName.equals("")) {
            isValid = false;
            txtinput1.setError("Please enter college name");
        } else if (strConcentration.equals("")) {
            isValid = false;
            txtinput2.setError("Please enter concentration");
        } else if (strDegree.equals("")) {
            isValid = false;
            txtinput3.setError("Please enter degree");
        } else if (strClubandSocieties.equals("")) {
            isValid = false;
            txtinput4.setError("Please enter club and societies");
        }else if (strGrade.equals("")) {
            isValid = false;
            txtinput5.setError("Please enter grade");
        }else if (strStartDate.equals("")) {
            isValid = false;
            txtinput6.setError("Please enter start date");
        }else if (strEndDate.equals("")) {
            isValid = false;
            txtinput7.setError("Please enter end date");
        }
        return isValid;
    }

    public void doCall() {
        if (!isNetworkConnected())
            return;

        EducationRequest educationRequest = new EducationRequest();
        educationRequest.setCollege_name(strCollegeName);
        educationRequest.setConcentration(strConcentration);
        educationRequest.setDegree(strDegree);
        educationRequest.setStart_date(strStartDate);
        educationRequest.setEnd_date(strEndDate);
        educationRequest.setGrade(strGrade);
        educationRequest.setClub_society(strClubandSocieties);
        educationRequest.setType(type);
        if (type.equals("update"))
        educationRequest.setId(userEducation.getId());

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

    public void setStartDate(View view) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mdiDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edtStartDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);
        mdiDialog.show();
    }

    public void setEndDate(View view) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mdiDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edtEndDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
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
        txtinput5.setErrorEnabled(false);
        txtinput6.setErrorEnabled(false);
        txtinput7.setErrorEnabled(false);
    }
}
