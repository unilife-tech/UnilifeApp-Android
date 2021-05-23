package unilife.com.unilife.brands;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.brands.adapter.InstoreAdapter;
import unilife.com.unilife.brands.adapter.OnlineMainAdapter;
import unilife.com.unilife.brands.newbrandresponse.BrandResponse2;
import unilife.com.unilife.brands.response.BrandDetailsResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retrofit.WebUrls;
import unilife.com.unilife.updated.BaseActivity;

public class BrandDetailsNew extends BaseActivity {

    @BindView(R.id.releOnline)
    RelativeLayout releOnline;
    @BindView(R.id.txtOnline)
    TextView txtOnline;
    @BindView(R.id.releInStore)
    RelativeLayout releInStore;
    @BindView(R.id.txtInStore)
    TextView txtInStore;

    @BindView(R.id.imgBrand)
    ImageView imgBrand;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvDescHeader)
    TextView tvDescHeader;
    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.recyclerOnline)
    RecyclerView recyclerOnline;
    @BindView(R.id.recyclerInstore)
    RecyclerView recyclerInstore;
//    private BrandResponse2.CategoriesBrand categoriesBrand;
    private ArrayList<BrandDetailsResponse.Online> online = new ArrayList<>();

    private String brandId="";
    private String brandName="";
    private String brandImage="";


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
//        categoriesBrand = getIntent().getParcelableExtra("item");

        brandId=getIntent().getStringExtra("id");
        brandName=getIntent().getStringExtra("name");
        brandImage=getIntent().getStringExtra("image");

        Glide.with(mContext)
                .load(brandImage)
                .into(imgBrand);


//        tvDesc.setText(getHtmlConverted(categoriesBrand.getBrandOffer().get(0).getDescription()));
//        tvDescHeader.setText(Html.fromHtml(categoriesBrand.getBrandOffer().get(0).getDescription()));
        txtHeader.setText(Html.fromHtml(brandName));
//        tvTermsDesc.setText(Html.fromHtml(categoriesBrand.getBrandOffer().get(0).getTermCondition()));

        //=========================================================================
        releOnline.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        txtOnline.setTextColor(getResources().getColor(R.color.white));

        releInStore.setBackgroundResource(R.drawable.back_brand_details);
        txtInStore.setTextColor(getResources().getColor(R.color.colorPrimary));

        findViewById(R.id.recyclerOnline).setVisibility(View.VISIBLE);
        findViewById(R.id.recyclerInstore).setVisibility(View.GONE);

        findViewById(R.id.recyclerOnline).setVisibility(View.VISIBLE);
        findViewById(R.id.recyclerInstore).setVisibility(View.GONE);

        getBrandDetails();
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_brand_details_new;
    }

    public void goBack(View view) {
        finish();
    }

    public void showUnilifeID(View view) {
//        intent = new Intent(mContext, OnlineRedeemedCoupons.class);
//        intent.putExtra("offer_id", categoriesBrand.getId());
//        startActivity(intent);
    }

    public void showOnline(View view) {
        releOnline.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        txtOnline.setTextColor(getResources().getColor(R.color.white));

        releInStore.setBackgroundResource(R.drawable.back_brand_details);
        txtInStore.setTextColor(getResources().getColor(R.color.colorPrimary));

        findViewById(R.id.recyclerOnline).setVisibility(View.VISIBLE);
        findViewById(R.id.recyclerInstore).setVisibility(View.GONE);

        setTextViewDrawableColor(txtOnline, R.color.white);
        setTextViewDrawableColor(txtInStore, R.color.colorPrimary);

    }

    public void showInStore(View view) {
        releInStore.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        txtInStore.setTextColor(getResources().getColor(R.color.white));

        releOnline.setBackgroundResource(R.drawable.back_brand_details);
        txtOnline.setTextColor(getResources().getColor(R.color.colorPrimary));

        findViewById(R.id.recyclerOnline).setVisibility(View.GONE);
        findViewById(R.id.recyclerInstore).setVisibility(View.VISIBLE);

        setTextViewDrawableColor(txtOnline, R.color.colorPrimary);
        setTextViewDrawableColor(txtInStore, R.color.white);

//        recyclerInstore.setHasFixedSize(true);
//        recyclerInstore.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerInstore.setAdapter(new InstoreAdapter(mContext, new ArrayList<>()));
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private void getBrandDetails() {
        if (!isNetworkConnected())
            return;

        BrandDetailsRequest brandDetailsRequest = new BrandDetailsRequest();
        brandDetailsRequest.setBrand_id(brandId);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BrandDetailsResponse> call = apiInterface.getBrandsDetails(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), brandDetailsRequest);

        call.enqueue(new Callback<BrandDetailsResponse>() {
            @Override
            public void onResponse(Call<BrandDetailsResponse> call, Response<BrandDetailsResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                tvDesc.setText(getHtmlConverted(response.body().getData().get(0).getDescription()));

                if (response.body().getData() != null && response.body().getData().size() > 0) {
                    recyclerOnline.setHasFixedSize(true);
                    recyclerOnline.setLayoutManager(new LinearLayoutManager(mContext));
                    recyclerOnline.setAdapter(new OnlineMainAdapter(mContext, response.body().getData().get(0).getOnline()));
                    online = response.body().getData().get(0).getOnline();
                }

                if (response.body().getData() != null && response.body().getData().size() > 0) {
                    recyclerInstore.setHasFixedSize(true);
                    recyclerInstore.setLayoutManager(new LinearLayoutManager(mContext));
                    recyclerInstore.setAdapter(new InstoreAdapter(mContext, response.body().getData().get(0).getStore()));
                }
            }

            @Override
            public void onFailure(Call<BrandDetailsResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void openFacebook(View view) {
        if (online.size() > 0) {
            callLInk(online.get(0).getFacebook());
        }
    }

    public void openInstagram(View view) {
        if (online.size() > 0) {
            callLInk(online.get(0).getInstagram());
        }
    }

    public void openTwitter(View view) {
        if (online.size() > 0) {
            callLInk(online.get(0).getTwitter());
        }
    }

    private void callLInk(String link) {
        if (link.contains("http")) {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse(link));
            startActivity(viewIntent);
        } else {
            showToast("Invalid link");
        }
    }

    public class BrandDetailsRequest {
        String brand_id;

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }
    }
}