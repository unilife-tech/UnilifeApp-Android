package unilife.com.unilife.profile.editprofiel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.adapters.InterestAdapter;
import unilife.com.unilife.profile.model.UserInterest;
import unilife.com.unilife.profile.model.requests.AddSkillRequest;
import unilife.com.unilife.profile.model.requests.SearchRequest;
import unilife.com.unilife.profile.model.responses.InterestResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditInterestActivity extends BaseActivity {

    public String strConcat;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.txtNewLang)
    TextView txtNewLang;
    @BindView(R.id.recycvlerView)
    RecyclerView recycvlerView;
    InterestAdapter interestAdapter;
    private ArrayList<UserInterest> data = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        interestAdapter = new InterestAdapter(mContext);
        recycvlerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycvlerView.setHasFixedSize(true);
        recycvlerView.setAdapter(interestAdapter);
        getInterest("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<UserInterest> newList = new ArrayList<UserInterest>();
                for (UserInterest channel : data) {
                    String channelName = channel.getInterestName().toLowerCase();
                    if (channelName.contains(s.toLowerCase())) {
                        newList.add(channel);
                    }
                }

                if (s.length() > 0) {
                    txtNewLang.setVisibility(View.VISIBLE);
                    txtNewLang.setText("+ Add New Interest " + s);
                } else {
                    txtNewLang.setVisibility(View.GONE);
                }

                interestAdapter.updateData(newList);
                return false;
            }
        });

        txtNewLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = txtNewLang.getText().toString().trim().replace("+ Add New Interest ", "");

                if (s.length() > 0) {
                    searchView.setQuery("", false);
                    addNewInterest(strConcat + s);
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_interest;
    }

    public void callService(View view) {
        addNewInterest(strConcat);
    }

    public void getInterest(String str) {
        if (!isNetworkConnected())
            return;

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearch(str);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<InterestResponse> call = apiInterface.user_interest(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), searchRequest);

        call.enqueue(new Callback<InterestResponse>() {
            @Override
            public void onResponse(Call<InterestResponse> call, Response<InterestResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    interestAdapter.updateData(response.body().getData());
                    setUpSkills(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<InterestResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    void setUpSkills(ArrayList<UserInterest> arrayList) {
        data = arrayList;
        strConcat = "";

        for (int i = 0; i < arrayList.size(); i++) {
            strConcat = strConcat + arrayList.get(i).getInterestName() + ",";
        }

    }

    public void addNewInterest(String strADD) {
        if (!isNetworkConnected())
            return;

        AddSkillRequest addSkillRequest = new AddSkillRequest();
        addSkillRequest.setUser_interest(strADD);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<InterestResponse> call = apiInterface.addNewInterest(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), addSkillRequest);

        call.enqueue(new Callback<InterestResponse>() {
            @Override
            public void onResponse(Call<InterestResponse> call, Response<InterestResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    interestAdapter.updateData(response.body().getData());
                    setUpSkills(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<InterestResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}
