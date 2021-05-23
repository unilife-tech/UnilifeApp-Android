package unilife.com.unilife.blogs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.blogs.response.InstagramResponse;
import unilife.com.unilife.blogs.response.Team;
import unilife.com.unilife.retrofit.WebUrls;

public class InstaPostdapter extends RecyclerView.Adapter<InstaPostdapter.MyViewHolder> {
    ArrayList<InstagramResponse.Datum> arrayList;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public InstaPostdapter(Context context, ArrayList<InstagramResponse.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public InstaPostdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_insta_post, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.textView.setText(arrayList.get(position).toString());
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.loadDataWithBaseURL("https://instagram.com", arrayList.get(position).getPost(), "text/html", "UTF-8", "");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
//        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.webView)
        public WebView webView;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}