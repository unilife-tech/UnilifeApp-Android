package unilife.com.unilife.blogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.blogs.adapter.InstaPostdapter;
import unilife.com.unilife.blogs.response.CategoriesBlog;
import unilife.com.unilife.blogs.response.InstagramResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retrofit.WebUrls;
import unilife.com.unilife.updated.BaseActivity;

public class BlogDetailsActivity extends BaseActivity {

    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.txtDetails)
    WebView txtDetails;
    @BindView(R.id.imageTop)
    ImageView imageTop;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtUrl)
    TextView txtUrl;
    @BindView(R.id.recyclerInstaPost)
    RecyclerView recyclerInstaPost;

    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnLike)
    Button btnLike;

    CategoriesBlog categoriesBlog;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        categoriesBlog = getIntent().getParcelableExtra("item");
        init();
        getInstaPost();
    }

    private void init() {
        txtHeader.setText(categoriesBlog.getTitle());
        txtTitle.setText(categoriesBlog.getTitle());
        txtDetails.loadData(categoriesBlog.getDescription(), "text/html", "UTF-8");
        Glide.with(mContext)
                .load(WebUrls.INSTANCE.getBlogImageUrl() + categoriesBlog.getImage())
                .into(imageTop);
        Glide.with(mContext)
                .load(WebUrls.INSTANCE.getBlogImageUrl() + categoriesBlog.getImage())
                .into(imgProfile);
        txtName.setText(categoriesBlog.getSharedBy());
        txtUrl.setText(categoriesBlog.getVideoLink());
        if (categoriesBlog.getUserBlogLike() != null && categoriesBlog.getUserBlogLike().size() > 0) {
            btnLike.setText("Unlike");
        }
        if (categoriesBlog.getUserBlogSaved() != null && categoriesBlog.getUserBlogSaved().size() > 0) {
            btnSave.setText("Unsave");
        }

        recyclerInstaPost.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerInstaPost.setHasFixedSize(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_blog_detials;
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void callSave(View view) {
        saveBlog();
    }

    private void saveBlog() {
        if (!isNetworkConnected())
            return;

        SaveBlog saveBlog = new SaveBlog();
        saveBlog.setBlog_id(categoriesBlog.getId().toString());
        saveBlog.setUser_id(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID));

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.userBlogSaved(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), saveBlog);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    showToast(response.body().getMessage());

                    if (btnSave.getText().toString().toLowerCase().equals("save")) {
                        btnSave.setText("Unsave");
                    } else {
                        btnSave.setText("Save");
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    private void getInstaPost() {
        if (!isNetworkConnected())
            return;

        InstaPostRequest instaPostRequest = new InstaPostRequest();
        instaPostRequest.setCategory_id(categoriesBlog.getCategoriesId().toString());

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<InstagramResponse> call = apiInterface.getInstaPost(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), instaPostRequest);

        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful()) {
                    recyclerInstaPost.setAdapter(new InstaPostdapter(mContext,response.body().getData()));
                }
            }

            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    private void likeBlog() {
        if (!isNetworkConnected())
            return;

        SaveBlog saveBlog = new SaveBlog();
        saveBlog.setBlog_id(categoriesBlog.getId().toString());
        saveBlog.setUser_id(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID));

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.likeUnlikeBlog(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), saveBlog);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    showToast(response.body().getMessage());
                    if (btnLike.getText().toString().toLowerCase().equals("like")) {
                        btnLike.setText("Unlike");
                    } else {
                        btnLike.setText("Like");
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void callLike(View view) {
        likeBlog();
    }

    public class SaveBlog {
        String user_id;
        String blog_id;

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setBlog_id(String blog_id) {
            this.blog_id = blog_id;
        }
    }

    public class InstaPostRequest {
        String category_id;

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }
    }
}