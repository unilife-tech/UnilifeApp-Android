package unilife.com.unilife.brands;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
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
import unilife.com.unilife.brands.request.RedeemRequest;
import unilife.com.unilife.brands.response.BrandDetailsResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class RedeemActivity extends BaseActivity {

    BrandDetailsResponse.Online online;
    @BindView(R.id.editCode)
    TextView editCode;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        online = getIntent().getParcelableExtra("item");
        editCode.setText(online.getCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_redeem;
    }

    public void goBack(View view) {
        finish();
    }

    public void copyCode(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", online.getCode());
        clipboard.setPrimaryClip(clip);
        showToast("Code copied!");
    }

    private void redeemCode() {
        if (!isNetworkConnected())
            return;

        RedeemRequest redeemRequest = new RedeemRequest();
        redeemRequest.setBrand_id(online.getBrandId());
        redeemRequest.setCode(online.getCode());
        redeemRequest.setType("online");

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.redeemVoucher(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), redeemRequest);

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

    public void callRedeem(View view) {
        if (online.getOnlineRedeemLink().contains("http")) {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse(online.getOnlineRedeemLink()));
            startActivity(viewIntent);
        }else {
            showToast("Invalid link");
        }
//        redeemCode();
    }
}