package unilife.com.unilife.chat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.adapter.RequestListingAdapter;
import unilife.com.unilife.chat.model.ReceivedRequestResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class ReceivedRequestActivity extends BaseActivity {

    @BindView(R.id.recyclerListing)
    RecyclerView recyclerListing;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        recyclerListing.setHasFixedSize(true);
        recyclerListing.setLayoutManager(new LinearLayoutManager(mContext));
        getRequest();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_received_request;
    }

    public void getRequest() {
        if (!isNetworkConnected())
            return;

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ReceivedRequestResponse> call = apiInterface.getRequest(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID));
        call.enqueue(new Callback<ReceivedRequestResponse>() {
            @Override
            public void onResponse(Call<ReceivedRequestResponse> call, Response<ReceivedRequestResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful()) {
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        recyclerListing.setAdapter(new RequestListingAdapter(mContext, response.body().getData()));
                    } else {
                        showToast(response.body().getMessage());
                        recyclerListing.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ReceivedRequestResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void callAcceptReject(String requestId, String type) {
        if (!isNetworkConnected())
            return;

        AcceptRejectRequest acceptRejectRequest = new AcceptRejectRequest();
        acceptRejectRequest.setRequest_id(requestId);
        acceptRejectRequest.setType(type);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.reqAcceptReject(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), acceptRejectRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful()) {
                    showToast(response.body().getMessage());
                    getRequest();
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

    public void acceptRejectRequest(@NotNull String requestId) {
        AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((Context) this, R.style.custom_alert_dialog);
        View view = LayoutInflater.from((Context) this).inflate(R.layout.perform_action_options, (ViewGroup) null);

        TextView tvAccept = view.findViewById(R.id.tvAccept);
        TextView tvReject = view.findViewById(R.id.tvReject);
        TextView tvCancel = view.findViewById(R.id.tvCancel);

        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(true);

        tvAccept.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                alertDialog.dismiss();
                callAcceptReject(requestId, "accept");
            }
        }));
        tvReject.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                alertDialog.dismiss();
                callAcceptReject(requestId, "reject");
            }
        }));
        tvCancel.setOnClickListener((View.OnClickListener) (new View.OnClickListener() {
            public final void onClick(View it) {
                alertDialog.dismiss();
            }
        }));

        alertDialog.getWindow().setBackgroundDrawable((Drawable) (new ColorDrawable(0)));
        Resources var11 = this.getResources();
        double width = (double) var11.getDisplayMetrics().widthPixels * 0.85D;
        int height = -2;

        alertDialog.getWindow().setLayout((int) width, height);
        alertDialog.getWindow().setGravity(80);
        alertDialog.show();
    }

    public class AcceptRejectRequest {
        String type;
        String request_id;

        public void setType(String type) {
            this.type = type;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }
    }
}