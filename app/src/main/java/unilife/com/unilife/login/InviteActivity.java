package unilife.com.unilife.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.R;
import unilife.com.unilife.home.Home;
import unilife.com.unilife.login.adapter.FriendsAdapter;
import unilife.com.unilife.login.model.FriendRequest;
import unilife.com.unilife.login.model.FriendsResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class InviteActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.txtName)
    TextView txtName;
    private ArrayList<FriendsResponse.Datum> data = new ArrayList<>();

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        hideKeyboard();
        txtName.setText(sharePref.getUsername());
        getFriendList();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_invite;
    }

    public void callNext(View view) {
        intent = new Intent(mContext, Home.class);
        startActivity(intent);
    }

    public void inviteFriends(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Unilife");
            String shareMessage = "Hey, let’s connect on Unilife, Unilife allows you to communicate easily in Uni and gives you access to students discounts and contents\n\n";
            shareMessage = shareMessage + " https://play.google.com/store/apps/details?id=unilife.com.unilife";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFriendList() {
        if (!isNetworkConnected())
            return;

        FriendListRequest friendListRequest = new FriendListRequest();
        friendListRequest.setPagination("1");
//        friendListRequest.setUniversity_id(sharePref.getUniversityId());

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FriendsResponse> call = apiInterface.friendRequestListing(sharePref.getUserId(), friendListRequest);

        call.enqueue(new Callback<FriendsResponse>() {
            @Override

            public void onResponse(Call<FriendsResponse> call, Response<FriendsResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    recyclerView.setHasFixedSize(true);

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (response.body().getData().get(i).getEmail_domain().equalsIgnoreCase(sharePref.getUniversityEmail().split("@")[1])) {
                            data.add(response.body().getData().get(i));
                        }
                    }
                    recyclerView.setAdapter(new FriendsAdapter(mContext, data));
                }
            }

            @Override
            public void onFailure(Call<FriendsResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void sendRequest(String status, String id) {
        if (!isNetworkConnected())
            return;

        FriendRequest friendRequest = new FriendRequest();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<CommonResponse> call;
        if (status.equals("send")) {
            friendRequest.setUser_id(sharePref.getUserId());
            friendRequest.setRequest_id(id);
            call = apiInterface.friendRequest(sharePref.getUserId(), friendRequest);
        } else {
            friendRequest.setReject_id(sharePref.getUserId());
            friendRequest.setUser_id(id);
            call = apiInterface.cancelRequest(sharePref.getUserId(), friendRequest);
        }
        showProgressDialog();
        call.enqueue(new Callback<CommonResponse>() {
            @Override

            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

//                if (response.isSuccessful() && response.body().getStatus()) {
//                  showToast(response.body().getMessage());
//                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void openContact(View view) {
        start(AddNumberActivity.class);
    }


    public class FriendListRequest {
        String university_id;
        String pagination;

        public void setUniversity_id(String university_id) {
            this.university_id = university_id;
        }

        public void setPagination(String pagination) {
            this.pagination = pagination;
        }
    }
}