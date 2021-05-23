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
import unilife.com.unilife.profile.adapters.ProfileAdapter_2;
import unilife.com.unilife.profile.model.requests.AddSkillRequest;
import unilife.com.unilife.profile.model.requests.SearchRequest;
import unilife.com.unilife.profile.model.responses.SkillsResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditSkillActivity extends BaseActivity {

    public String strConcat;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.txtNewLang)
    TextView txtNewLang;
    @BindView(R.id.recycvlerView)
    RecyclerView recycvlerView;
    ProfileAdapter_2 profileAdapter_2;
    private ArrayList<SkillsResponse.Datum> data = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        profileAdapter_2 = new ProfileAdapter_2(mContext);
        recycvlerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycvlerView.setHasFixedSize(true);
        recycvlerView.setAdapter(profileAdapter_2);
        getSkills("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<SkillsResponse.Datum> newList = new ArrayList<SkillsResponse.Datum>();
                for (SkillsResponse.Datum channel : data) {
                    String channelName = channel.getSkillName().toLowerCase();
                    if (channelName.contains(s.toLowerCase())) {
                        newList.add(channel);
                    }
                }

                if (s.length() > 0) {
                    txtNewLang.setVisibility(View.VISIBLE);
                    txtNewLang.setText("+ Add New Skill " + s);
                } else {
                    txtNewLang.setVisibility(View.GONE);
                }

                profileAdapter_2.updateData(newList);
                return false;
            }
        });

        txtNewLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = txtNewLang.getText().toString().trim().replace("+ Add New Skill ", "");

                if (s.length() > 0) {
                    searchView.setQuery("", false);
                    addNewSkill(strConcat + s);
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_skill;
    }


    public void callService(View view) {
//        addNewSkill(strConcat);
        finish();
    }

    public void getSkills(String str) {
        if (!isNetworkConnected())
            return;

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearch(str);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SkillsResponse> call = apiInterface.user_skills(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), searchRequest);

        call.enqueue(new Callback<SkillsResponse>() {
            @Override
            public void onResponse(Call<SkillsResponse> call, Response<SkillsResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    profileAdapter_2.updateData(response.body().getData());
                    setUpSkills(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<SkillsResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    void setUpSkills(ArrayList<SkillsResponse.Datum> arrayList) {
        data = arrayList;
        strConcat = "";

        for (int i = 0; i < arrayList.size(); i++) {
            strConcat = strConcat + arrayList.get(i).getSkillName() + ",";
        }

    }

    public void addNewSkill(String strADD) {
        if (!isNetworkConnected())
            return;

        AddSkillRequest addSkillRequest = new AddSkillRequest();
        addSkillRequest.setSkill_name(strADD);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SkillsResponse> call = apiInterface.addNewSkills(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), addSkillRequest);

        call.enqueue(new Callback<SkillsResponse>() {
            @Override
            public void onResponse(Call<SkillsResponse> call, Response<SkillsResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    profileAdapter_2.updateData(response.body().getData());
                    setUpSkills(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<SkillsResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}
