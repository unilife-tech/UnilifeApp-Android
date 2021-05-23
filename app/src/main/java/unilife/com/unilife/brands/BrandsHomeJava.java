package unilife.com.unilife.brands;

public class BrandsHomeJava {
//
//    RecyclerView recyclerView;
//
//    @Override
//    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
//        super.onViewReady(savedInstanceState, intent);
//        doCall();
//    }
//
//    void setupPager(ArrayList<BannerResponse.Datum> slider) {
//        ViewPager viewPager = findViewById(R.id.brandPager);
//        CustomPagerAdapter adapter = new CustomPagerAdapter(mContext, slider);
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(0);
//
//        CircleIndicator pageIndicatorView = findViewById(R.id.indicatorbrand);
//        pageIndicatorView.setViewPager(viewPager); // specify total count of indicators
//    }
//
//    void setUpData(ArrayList<Offer> arrayList) {
//        RecyclerView recyclerView = findViewById(R.id.recycvlerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.setAdapter(new BrandMainAdapter(mContext,arrayList));
//    }
//
//    @Override
//    protected int getContentView() {
//        return R.layout.activity_brands_home2;
//    }
//
//
//    public void doCall() {
//        if (!isNetworkConnected())
//            return;
//
//        showProgressDialog();
//        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
//        Call<BrandResponse> call = apiInterface.getBrands(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID));
//
//        call.enqueue(new Callback<BrandResponse>() {
//            @Override
//            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
//                hideProgressDialog();
//                Log.e("response code -->", "" + response.code());
//
//                if (response.isSuccessful()) {
//                    setupPager(response.body().getSlider());
//                    setUpData(response.body().getOffer());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BrandResponse> call, Throwable t) {
//                hideProgressDialog();
//                showToast(t.getMessage());
//            }
//        });
//    }
}
