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
import unilife.com.unilife.profile.model.UserExperience;
import unilife.com.unilife.profile.model.requests.ExperienceRequest;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditExperienceActivity extends BaseActivity implements TextWatcher {

    String strCompName;
    String strRole;
    String strEmpType;
    String strStartDate;
    String strEndDate;
    String strIndustry;
    String strLocation;


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

    @BindView(R.id.edtCompName)
    EditText edtCompName;
    @BindView(R.id.edtRole)
    EditText edtRole;
    @BindView(R.id.edtEmpType)
    EditText edtEmpType;
    @BindView(R.id.edtStartDate)
    EditText edtStartDate;
    @BindView(R.id.edtEndDate)
    EditText edtEndDate;
    @BindView(R.id.edtIndustry)
    EditText edtIndustry;
    @BindView(R.id.edtLocation)
    EditText edtLocation;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.btnUpdate)
    Button  btnUpdate;
    UserExperience userExperience;
    String type="save";
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        userExperience = getIntent().getParcelableExtra("item");

        if (userExperience!=null) {
            type="update";
            text.setText("Edit Experience");
            btnUpdate.setText("UPDATE");


             edtCompName.setText(userExperience.getCompanyName());
             edtCompName.setSelection(userExperience.getCompanyName().length());
             edtRole.setText(userExperience.getDesignation());
             edtEmpType.setText(userExperience.getEmpType());
             edtStartDate.setText(userExperience.getStartDate());
             edtEndDate.setText(userExperience.getEndDate());
             edtIndustry.setText(userExperience.getIndustry());
             edtLocation.setText(userExperience.getLocation());
        }

        edtCompName.addTextChangedListener(this);
        edtRole.addTextChangedListener(this);
        edtEmpType.addTextChangedListener(this);
        edtIndustry.addTextChangedListener(this);
        edtStartDate.addTextChangedListener(this);
        edtEndDate.addTextChangedListener(this);
        edtLocation.addTextChangedListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_expirience;
    }

    public void callService(View view) {
        strCompName =   edtCompName.getText().toString().trim();
        strRole =       edtRole.getText().toString().trim();
        strEmpType =    edtEmpType.getText().toString().trim();
        strStartDate =  edtStartDate.getText().toString().trim();
        strEndDate =    edtEndDate.getText().toString().trim();
        strIndustry =   edtIndustry.getText().toString().trim();
        strLocation =   edtLocation.getText().toString().trim();

        if (valid())
        doCall();
    }

    boolean valid() {
        boolean isValid = true;
        if (strCompName.equals("")) {
            isValid = false;
            txtinput1.setError("Please enter company name");
        } else if (strRole.equals("")) {
            isValid = false;
            txtinput2.setError("Please enter role");
        } else if (strEmpType.equals("")) {
            isValid = false;
            txtinput3.setError("Please enter employee type");
        } else if (strStartDate.equals("")) {
            isValid = false;
            txtinput4.setError("Please select start date");
        }else if (strEndDate.equals("")) {
            isValid = false;
            txtinput5.setError("Please select end date");
        }else if (strIndustry.equals("")) {
            isValid = false;
            txtinput6.setError("Please enter industry");
        }else if (strLocation.equals("")) {
            isValid = false;
            txtinput7.setError("Please enter location");
        }
        return isValid;
    }


    public void doCall() {
        if (!isNetworkConnected())
            return;

        ExperienceRequest experienceRequest = new ExperienceRequest();
        experienceRequest.setCompany_name(strCompName);
        experienceRequest.setDesignation(strRole);
        experienceRequest.setEmp_type(strEmpType);
        experienceRequest.setStart_date(strStartDate);
        experienceRequest.setEnd_date(strEndDate);
        experienceRequest.setIndustry(strIndustry);
        experienceRequest.setLocation(strLocation);
        experienceRequest.setType(type);

        if (type.equals("update"))
        experienceRequest.setId(userExperience.getId());

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
