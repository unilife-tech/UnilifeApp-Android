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
import unilife.com.unilife.profile.adapters.LanguageAdapter;
import unilife.com.unilife.profile.adapters.ProfileAdapter_2;
import unilife.com.unilife.profile.model.UserLanguage;
import unilife.com.unilife.profile.model.requests.AddSkillRequest;
import unilife.com.unilife.profile.model.requests.SearchRequest;
import unilife.com.unilife.profile.model.responses.LanguageResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class EditLangActivity extends BaseActivity {

    public String strConcat;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.txtNewLang)
    TextView txtNewLang;
    @BindView(R.id.recycvlerView)
    RecyclerView recycvlerView;
    LanguageAdapter languageAdapter;
    private ArrayList<UserLanguage> data = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        languageAdapter = new LanguageAdapter(mContext);
        recycvlerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recycvlerView.setHasFixedSize(true);
        recycvlerView.setAdapter(languageAdapter);
        getLanguages("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<UserLanguage> newList = new ArrayList<UserLanguage>();
                for (UserLanguage channel : data) {
                    String channelName = channel.getLanguageName().toLowerCase();
                    if (channelName.contains(s.toLowerCase())) {
                        newList.add(channel);
                    }
                }

                if (s.length() > 0) {
                    txtNewLang.setVisibility(View.VISIBLE);
                    txtNewLang.setText("+ Add New Language " + s);
                } else {
                    txtNewLang.setVisibility(View.GONE);
                }

                languageAdapter.updateData(newList);
                return false;
            }
        });

        txtNewLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = txtNewLang.getText().toString().trim().replace("+ Add New Language ", "");

                if (s.length() > 0) {
                    searchView.setQuery("", false);
                    addNewSkill(strConcat + s);
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_lang;
    }

    public void callService(View view) {
        addNewSkill(strConcat);
    }

    public void getLanguages(String str) {
        if (!isNetworkConnected())
            return;

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearch(str);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LanguageResponse> call = apiInterface.user_languages(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), searchRequest);

        call.enqueue(new Callback<LanguageResponse>() {
            @Override
            public void onResponse(Call<LanguageResponse> call, Response<LanguageResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    languageAdapter.updateData(response.body().getData());
                    setUpSkills(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<LanguageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    void setUpSkills(ArrayList<UserLanguage> arrayList) {
        data = arrayList;
        strConcat = "";

        for (int i = 0; i < arrayList.size(); i++) {
            strConcat = strConcat + arrayList.get(i).getLanguageName() + ",";
        }

    }

    public void addNewSkill(String strADD) {
        if (!isNetworkConnected())
            return;

        AddSkillRequest addSkillRequest = new AddSkillRequest();
        addSkillRequest.setLanguage_name(strADD);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LanguageResponse> call = apiInterface.addNewLanguage(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), addSkillRequest);

        call.enqueue(new Callback<LanguageResponse>() {
            @Override
            public void onResponse(Call<LanguageResponse> call, Response<LanguageResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    languageAdapter.updateData(response.body().getData());
                    setUpSkills(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<LanguageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}
