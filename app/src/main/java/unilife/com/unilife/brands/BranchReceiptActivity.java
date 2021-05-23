package unilife.com.unilife.brands;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.brands.request.RedeemRequest;
import unilife.com.unilife.brands.response.BrandDetailsResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class BranchReceiptActivity extends BaseActivity {

    BrandDetailsResponse.Store store;
    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.edtReceipt)
    TextView edtReceipt;
    @BindView(R.id.edtBranch)
    TextView edtBranch;

    String code = "";

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        store = getIntent().getParcelableExtra("item");
        code = getIntent().getStringExtra("code");
        txtHeader.setText(store.getHeading());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_branch_receipt;
    }

    public void goBack(View view) {
        finish();
    }

    public void callNext(View view) {
        if (edtReceipt.getText().toString().trim().equals("")) {
            edtReceipt.setHint("Please enter receipt number");
            edtReceipt.setHintTextColor(Color.RED);
        } else if (edtBranch.getText().toString().trim().equals("")) {
            edtBranch.setHint("Please enter branch name");
            edtBranch.setHintTextColor(Color.RED);
        } else {
//            sharePref.setReceiptNumber(edtReceipt.getText().toString().trim());
//            sharePref.setBranchName(edtBranch.getText().toString().trim());
//            start(RedeemSuccessActivity.class);
            redeemCode();
        }
    }

    private void redeemCode() {
        if (!isNetworkConnected())
            return;

        RedeemRequest redeemRequest = new RedeemRequest();
        redeemRequest.setBrand_id(store.getBrandId());
        redeemRequest.setCode(code);
        redeemRequest.setReceipt_number(edtReceipt.getText().toString().trim());
        redeemRequest.setBranch_name_location(edtBranch.getText().toString().trim());
        redeemRequest.setType("instore");

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.redeemVoucher(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), redeemRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful() && response.body().getStatus()) {
                    start(RedeemSuccessActivity.class);
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
}