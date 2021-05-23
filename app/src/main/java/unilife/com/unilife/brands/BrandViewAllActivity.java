package unilife.com.unilife.brands;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.brands.adapter.BrandViewAllAdapter;
import unilife.com.unilife.brands.adapter.CateBrandViewAllAdapter;
import unilife.com.unilife.brands.adapter.InstoreAdapter;
import unilife.com.unilife.brands.adapter.OnlineMainAdapter;
import unilife.com.unilife.brands.newbrandresponse.BrandResponse2;
import unilife.com.unilife.brands.response.BrandDetailsResponse;
import unilife.com.unilife.brands.response.CategoryWiseBrandResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class BrandViewAllActivity extends BaseActivity {

    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BrandResponse2.Offer offer;

    String strCateId = "";

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        offer = getIntent().getParcelableExtra("item");

        if (offer!=null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            recyclerView.setAdapter(new BrandViewAllAdapter(mContext, offer.getCategoriesBrand()));
            txtHeader.setText(offer.getCategory());
        }else {
            strCateId=getIntent().getStringExtra("id");
            getBrands();
        }
//        getBrands();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_brand_view_all;
    }

    public void goBack(View view) {
        finish();
    }

    private void getBrands() {
        if (!isNetworkConnected())
            return;

        CategoryWiseBrandRequest wiseBrandRequest = new CategoryWiseBrandRequest();
        wiseBrandRequest.setCategory_id(strCateId);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CategoryWiseBrandResponse> call = apiInterface.categories_wise_offers_data(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), wiseBrandRequest);

        call.enqueue(new Callback<CategoryWiseBrandResponse>() {
            @Override
            public void onResponse(Call<CategoryWiseBrandResponse> call, Response<CategoryWiseBrandResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful() && response.body().getStatus()) {
                    txtHeader.setText(response.body().getCategories().get(0).getName());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recyclerView.setAdapter(new CateBrandViewAllAdapter(mContext, response.body().getCategories().get(0).getOffers()));
                }
            }

            @Override
            public void onFailure(Call<CategoryWiseBrandResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public class CategoryWiseBrandRequest {
        String category_id;

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }
    }

}