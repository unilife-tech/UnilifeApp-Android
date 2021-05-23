package unilife.com.unilife.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.login.model.VerifyEmail;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class VerifyEmailActivity extends BaseActivity {
    @BindView(R.id.edtOTP)
    EditText edtOTP;
    String OTP = "";
    private AlertDialog basic_reg = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
//        doCall();
//        edtEmail.setText(sharePref.getUniversityEmail());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_verify_email;
    }

    public void callSubmit(View view) {
//        if (edtEmail.getText().toString().trim().equals("")) {
//            edtEmail.setHint("Please enter email");
//            edtEmail.setHintTextColor(Color.RED);
//        } else {
//            doCall();
//            start(FirstLastNameActivity.class);
//        }

        OTP = edtOTP.getText().toString().trim();

        if (OTP.equals("")) {
            showToast("Please enter otp");
        } else {
            verifyOTP();
//            if (strOtp.equals(OTP)) {
//                start(FirstLastNameActivity.class);
//            } else {
//                showToast("Invalid OTP");
//            }
        }

    }


    public void verifyOTP() {
        if (!isNetworkConnected())
            return;

        VerifyEmail verifyEmail = new VerifyEmail();
        verifyEmail.setEmail(sharePref.getUniversityEmail());
        verifyEmail.setOtp(OTP);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.otpVerify(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), verifyEmail);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
//                    OTP = response.body().getOtp();
//                } else
                    start(FirstLastNameActivity.class);
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

//    public void otpVerify(String otp) {
//        AlertDialog.Builder builder;
//        builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
//        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//        View v = inflater.inflate(R.layout.layout_otp_dialog, null);
//
//        OtpView otpView = v.findViewById(R.id.otpView);
//        TextView tvResend = v.findViewById(R.id.tvResend);
//        Button otpSubmit = v.findViewById(R.id.otpSubmit);
//
//        otpSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String strOtp = otpView.getText().toString().trim();
//
//                if (strOtp.equals("")) {
//                    showToast("Please enter otp");
//                } else {
//                    if (strOtp.equals(otp)) {
//                        basic_reg.dismiss();
//                        sharePref.setUniversityEmail(edtEmail.getText().toString().trim());
//                        start(FirstLastNameActivity.class);
//                    } else {
//                        showToast("Invalid OTP");
//                    }
//                }
//            }
//        });
//
//        tvResend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                doCall();
//            }
//        });
//
//        builder.setView(v);
//        builder.setCancelable(true);
//        builder.create();
//        basic_reg = builder.show();
//        basic_reg.setCancelable(true);
//    }



}