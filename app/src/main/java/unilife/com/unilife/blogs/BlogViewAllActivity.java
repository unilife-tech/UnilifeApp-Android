package unilife.com.unilife.blogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import unilife.com.unilife.R;
import unilife.com.unilife.blogs.adapter.BlogSubAdapter;
import unilife.com.unilife.blogs.adapter.BlogViewAllAdapter;
import unilife.com.unilife.blogs.response.Blog;
import unilife.com.unilife.brands.adapter.BrandSubAdapter;
import unilife.com.unilife.updated.BaseActivity;

public class BlogViewAllActivity extends BaseActivity {

    @BindView(R.id.txtHeader)
    TextView txtHeader;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Blog blog;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        blog = getIntent().getParcelableExtra("item");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(new BlogViewAllAdapter(mContext, blog.getCategoriesBlog()));
        txtHeader.setText(blog.getCategoriesName());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_blog_view_all;
    }

    public void goBack(View view) {
        finish();
    }
}