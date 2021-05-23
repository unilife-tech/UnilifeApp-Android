package unilife.com.unilife.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.R;
import unilife.com.unilife.login.response.UniversityListingResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class SearchUniversity extends BaseActivity {

    @BindView(R.id.searchView)
    android.support.v7.widget.SearchView searchView;
    @BindView(R.id.listView)
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private List<UniversityListingResponse.Datum> data = new ArrayList<>();
    private AlertDialog basic_reg = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        doCall();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search_university;
    }

    public void doCall() {
        if (!isNetworkConnected())
            return;

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UniversityListingResponse> call = apiInterface.getUniversityListing();

        call.enqueue(new Callback<UniversityListingResponse>() {
            @Override

            public void onResponse(Call<UniversityListingResponse> call, Response<UniversityListingResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    data.addAll(response.body().getData());

                    for (int i = 0; i < data.size(); i++) {
                        arrayList.add(data.get(i).getName());
                    }

                    arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.e("selected", String.valueOf(adapterView.getItemAtPosition(i)));
                            for (int j = 0; j < data.size(); j++) {
                                if (data.get(j).getName().equalsIgnoreCase(String.valueOf(adapterView.getItemAtPosition(i)))) {
                                    sharePref.setUniversityId(data.get(j).getId());
                                    sharePref.setUniversityName(data.get(j).getName());
                                    sharePref.setDomain(data.get(j).getDomains());
                                    break;
                                }
                            }
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UniversityListingResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void addUniversity(String name, String domain) {
        if (!isNetworkConnected())
            return;

        AddUniversityRequest addUniversityRequest = new AddUniversityRequest();
        addUniversityRequest.setDomain(name);
        addUniversityRequest.setName(domain);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.addUniversity(addUniversityRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override

            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    showToast(response.body().getMessage());
                    sharePref.setUniversityId(response.body().getUniversity_id());
                    sharePref.setUniversityName(name);
                    sharePref.setDomain(domain);
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

    public void callAddNew(View view) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_add_university, null);

        EditText edtName = v.findViewById(R.id.edtName);
        EditText edtDomain = v.findViewById(R.id.edtDomain);
        Button btnUpdate = v.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName = edtName.getText().toString().trim();
                String strDomain = edtDomain.getText().toString().trim();

                if (strName.equals("")) {
                    edtName.setHint("Please enter name");
                    edtName.setHintTextColor(Color.RED);
                } else if (strDomain.equals("")) {
                    edtDomain.setHint("Please enter domain");
                    edtDomain.setHintTextColor(Color.RED);
                } else {
                    basic_reg.dismiss();
                    addUniversity(strName, strDomain);
                }
            }
        });

        builder.setView(v);
        builder.setCancelable(true);
        builder.create();
        basic_reg = builder.show();
        basic_reg.setCancelable(true);
    }

    public class AddUniversityRequest {
        String name;
        String domain;

        public void setName(String name) {
            this.name = name;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }
}