package unilife.com.unilife.blogs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.blogs.BlogDetailsActivity;
import unilife.com.unilife.R;
import unilife.com.unilife.blogs.response.CategoriesBlog;
import unilife.com.unilife.retrofit.WebUrls;

public class BlogViewAllAdapter extends RecyclerView.Adapter<BlogViewAllAdapter.MyViewHolder> {
    ArrayList<CategoriesBlog> categoriesBlogs;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BlogViewAllAdapter(Context context, ArrayList<CategoriesBlog> categoriesBlogs) {
        this.context = context;
        this.categoriesBlogs = categoriesBlogs;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BlogViewAllAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_view_all, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.textView.setText(arrayList.get(position).toString());

        try {
//            RequestOptions defultImg = new RequestOptions()
//                    .placeholder(R.drawable.default_image)
//                    .error(R.drawable.default_image)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .priority(Priority.HIGH);

            Glide
                    .with(context)
                    .load(WebUrls.INSTANCE.getBlogImageUrl() + categoriesBlogs.get(position).getImage())
//                    .apply(defultImg)
                    .into(holder.imgOffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Spanned title=Html.fromHtml(categoriesBlogs.get(position).getTitle());
        Spanned desc=Html.fromHtml(categoriesBlogs.get(position).getDescription());

        holder.txtTitle.setText(Html.fromHtml(String.valueOf(title)));
        holder.txtDesc.setText(Html.fromHtml(String.valueOf(desc)));

        Log.e("title", categoriesBlogs.get(position).getTitle());
        Log.e("desc", categoriesBlogs.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BlogDetailsActivity.class);
                intent.putExtra("item",categoriesBlogs.get(position));
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categoriesBlogs.size();
//        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.imgOffer)
        public ImageView imgOffer;
        @BindView(R.id.txtTitle)
        public TextView txtTitle;
        @BindView(R.id.txtDesc)
        public TextView txtDesc;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}