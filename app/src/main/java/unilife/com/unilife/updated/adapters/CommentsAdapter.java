package unilife.com.unilife.updated.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import de.hdodenhof.circleimageview.CircleImageView;
import unilife.com.unilife.R;
import unilife.com.unilife.home.responses.comment.Datum;
import unilife.com.unilife.retrofit.WebUrls;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {
    ArrayList<Datum> arrayList=new ArrayList<>();
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommentsAdapter(Context context) {
         this.context = context;
    }

    public void updateData(ArrayList<Datum> arrayList) {
         this.arrayList = arrayList;
         notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CommentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_comment, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtNameVd.setText(arrayList.get(position).getUserData().get(0).getUsername());
        holder.txtTimeVd.setText(Html.fromHtml(arrayList.get(position).getComment()));
        RequestOptions defultImg = new RequestOptions()
                    .placeholder(R.drawable.default_profile)
                    .error(R.drawable.default_profile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

        Glide
                .with(context)
                .load(arrayList.get(position).getUserData().get(0).getProfileImage())
                    .apply(defultImg)
                .into(holder.imgProfileVd);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return arrayList.size();
        return arrayList.size();
//        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.txtNameVd)
        public TextView txtNameVd;
        @BindView(R.id.txtTimeVd)
        public TextView txtTimeVd;
        @BindView(R.id.imgProfileVd)
        public CircleImageView imgProfileVd;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }
    }
}