package unilife.com.unilife.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.R;
import unilife.com.unilife.home.adapter.HomeAdapter2;
import unilife.com.unilife.home.requests.ReportPostRequest;
import unilife.com.unilife.home.requests.SelectPollRequest;
import unilife.com.unilife.home.responses.HomeResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.utils.EndlessRecyclerViewScrollListener;

public class HomeNew extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;
    private HomeAdapter2 homeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeadapter=new HomeAdapter2(this);
        recyclerView=findViewById(R.id.rcyMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(homeadapter);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        // Retain an instance so that you can call `resetState()` for fresh searches
//        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
//                getHomepageData(1);
//            }
//        };
//        // Adds the scroll listener to RecyclerView
//        recyclerView.addOnScrollListener(scrollListener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getHomepageData(1);
    }

    //    public class HomeRequest{
//        String pagination;
//        public void setPagination(String pagination) {
//            this.pagination = pagination;
//        }
//    }


    public void getHomepageData(int page) {
//        if (!isNetworkConnected()
//            return;
        Home.HomeRequest homeRequest=new Home.HomeRequest();
        homeRequest.setPagination(page+"");



//        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<HomeResponse> call = apiInterface.getHomeData("614", homeRequest);

        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
//                hideProgressDialog();
                homeadapter.updateData(response.body().getData());

            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
//                hideProgressDialog();
//                showToast(t.getMessage());
            }
        });
    }


//    public class LikeRequest{
//      String  user_id;
//      String  post_id;
//
//        public void setUser_id(String user_id) {
//            this.user_id = user_id;
//        }
//
//        public void setPost_id(String post_id) {
//            this.post_id = post_id;
//        }
//    }
//
//    public void reportUser(String id) {
////        if (!isNetworkConnected()
////            return;
//
//        ReportPostRequest postRequest=new ReportPostRequest();
//        postRequest.setReport_post_id("931");
//        postRequest.setReason("Invalid post are there");
//        postRequest.setType("Spam");
//
//
////        showProgressDialog();
//        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
//        Call<CommonResponse> call = apiInterface.reportPost("614", postRequest);
//
//        call.enqueue(new Callback<CommonResponse>() {
//            @Override
//            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
////                hideProgressDialog();
//                if (response.isSuccessful()) {
//                    Toast.makeText(HomeNew.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<CommonResponse> call, Throwable t) {
////                hideProgressDialog();
////                showToast(t.getMessage());
//            }
//        });
//    }
}
//}