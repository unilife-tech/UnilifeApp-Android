package unilife.com.unilife.blogs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import unilife.com.unilife.blogs.response.CategoriesBlog;
import unilife.com.unilife.blogs.response.Team;
import unilife.com.unilife.retrofit.WebUrls;

public class BlogTeamAdapter extends RecyclerView.Adapter<BlogTeamAdapter.MyViewHolder> {
    ArrayList<Team> categoriesBrand;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BlogTeamAdapter(Context context, ArrayList<Team> categoriesBrand) {
        this.context = context;
        this.categoriesBrand = categoriesBrand;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BlogTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_team, parent, false);

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
            RequestOptions defultImg = new RequestOptions()
                    .placeholder(R.drawable.default_profile)
                    .error(R.drawable.default_profile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide
                    .with(context)
                    .load(WebUrls.INSTANCE.getPROFILE_IMAGE_URL() + categoriesBrand.get(position).getImage())
                    .apply(defultImg)
                    .into(holder.imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.txtName.setText(Html.fromHtml(categoriesBrand.get(position).getName()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categoriesBrand.size();
//        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.imgProfile)
        public ImageView imgProfile;
        @BindView(R.id.txtName)
        public TextView txtName;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}