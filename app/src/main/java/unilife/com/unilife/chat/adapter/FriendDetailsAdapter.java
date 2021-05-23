package unilife.com.unilife.chat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.model.ItemFriendDetails;

public class FriendDetailsAdapter extends RecyclerView.Adapter<FriendDetailsAdapter.MyViewHolder> {

    ArrayList<ItemFriendDetails> arrayList = new ArrayList<>();
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendDetailsAdapter(Context context, ArrayList<ItemFriendDetails> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FriendDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_friend_detials, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (position==3)
        {
            holder.line1.setVisibility(View.GONE);
        }

        if (position == 4) {
            holder.line1.setVisibility(View.VISIBLE);
            holder.imgProfile.setVisibility(View.GONE);
            holder.txtName.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.line1.getLayoutParams();
            params.height = context.getResources().getDimensionPixelSize(R.dimen.text_view_height);
            params.setMargins(0,0,0,0);
            holder.line1.setLayoutParams(params);
        }else {
//            holder.line1.setVisibility(View.GONE);
            holder.imgProfile.setVisibility(View.VISIBLE);
            holder.txtName.setVisibility(View.VISIBLE);
            holder.txtName.setText(arrayList.get(position).getTitle());
            holder.imgProfile.setImageResource(arrayList.get(position).getImage());
            holder.txtName.setTextColor(Color.parseColor(arrayList.get(position).getColor()));
//            holder.imgProfile.getBackground().setColorFilter(Color.parseColor(arrayList.get(position).getColor()), PorterDuff.Mode.SRC_ATOP);
        }
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

        @BindView(R.id.imgProfile)
        ImageView imgProfile;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.line1)
        TextView line1;
//        @BindView(R.id.constraintMain)
//        ConstraintLayout constraintMain;
//        @BindView(R.id.group)
//        Group constraintMain;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}