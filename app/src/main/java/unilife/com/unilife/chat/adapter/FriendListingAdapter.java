package unilife.com.unilife.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import unilife.com.unilife.chat.model.FriendsResponse;

public class FriendListingAdapter extends RecyclerView.Adapter<FriendListingAdapter.MyViewHolder> {

    public ArrayList<FriendsResponse.Datum> arrayList = new ArrayList<>();
    private Context context;
    private RequestOptions defultImg = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH);

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendListingAdapter(Context context, ArrayList<FriendsResponse.Datum> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void updateData(ArrayList<FriendsResponse.Datum> arrayList) {
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FriendListingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_request_listing, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (!arrayList.get(position).isSelected()) {
            holder.tvAction.setText("Add");
            holder.tvAction.setBackgroundResource(R.drawable.radius_dark_grey);
        } else {
            holder.tvAction.setText("Selected");
            holder.tvAction.setBackgroundResource(R.drawable.radius_color_primary);
        }
        holder.tvName.setText(arrayList.get(position).getUsername());
        Glide.with(context)
                .load(arrayList.get(position).getProfileImage())
                .apply(defultImg)
                .into(holder.ivImg);

        holder.tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!arrayList.get(position).isSelected()) {
                    arrayList.get(position).setSelected(true);
                } else {
                    arrayList.get(position).setSelected(false);
                }

                notifyDataSetChanged();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return arrayList.size();
        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.ivImg)
        ImageView ivImg;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAction)
        TextView tvAction;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            tvAction.setText("Add");
            tvAction.setBackgroundResource(R.drawable.radius_dark_grey);
        }
    }

}