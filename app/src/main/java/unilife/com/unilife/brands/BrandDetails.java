//package unilife.com.unilife.brands;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Html;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.google.gson.JsonObject;
//
//import org.json.JSONObject;
//
//import butterknife.BindView;
//import unilife.com.unilife.PreferenceFile;
//import unilife.com.unilife.R;
//import unilife.com.unilife.brands.response.CategoriesBrand;
//import unilife.com.unilife.retrofit.RetrofitService;
//import unilife.com.unilife.retrofit.WebUrls;
//import unilife.com.unilife.updated.BaseActivity;
//import unilife.com.unilife.utils.Alerts;
//
//public class BrandDetails extends BaseActivity {
//
//    @BindView(R.id.imgBrand)
//    ImageView imgBrand;
//    @BindView(R.id.tvDesc)
//    TextView tvDesc;
//    @BindView(R.id.tvTermsDesc)
//    TextView tvTermsDesc;
//    @BindView(R.id.txtHeader)
//    TextView txtHeader;
//
//    private CategoriesBrand categoriesBrand;
//
//    @Override
//    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
//        super.onViewReady(savedInstanceState, intent);
//        categoriesBrand = getIntent().getParcelableExtra("item");
//
//        Glide
//                .with(mContext)
//                .load(WebUrls.INSTANCE.getBrandImageUrl() + categoriesBrand.getImage())
//                .into(imgBrand);
//
//        tvDesc.setText(Html.fromHtml(categoriesBrand.getBrandOffer().get(0).getDescription()));
//        tvTermsDesc.setText(Html.fromHtml(categoriesBrand.getBrandOffer().get(0).getTermCondition()));
//        txtHeader.setText(Html.fromHtml(categoriesBrand.getBrandName()));
//    }
//
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_brand_details;
//    }
//
//    public void goBack(View view) {
//        finish();
//    }
//
//    public void showUnilifeID(View view) {
//        intent = new Intent(mContext, OnlineRedeemedCoupons.class);
//        intent.putExtra("offer_id", categoriesBrand.getId());
//        startActivity(intent);
//    }
//}