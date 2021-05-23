package unilife.com.unilife.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.update.ChatDetailsActivity;
import unilife.com.unilife.profile.model.UserSkill;

public class ChatListingAdapter extends RecyclerView.Adapter<ChatListingAdapter.MyViewHolder> {

    ArrayList<UserSkill> arrayList = new ArrayList<>();
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatListingAdapter(Context context) {
        this.context = context;
    }

    public void updateData(ArrayList<UserSkill> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatListingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_chat_listing, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.textView.setText(arrayList.get(position).getSkillName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChatDetailsActivity.class);
//                intent.putExtra("receiver_id", arrayList.get(position).receiverId)
//                intent.putExtra("receiver_name", arrayList.get(position).receiverName)
//                intent.putExtra("receiver_profile_image", arrayList.get(position).receiverProfileImage);
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return arrayList.size();
        return 20;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.imgProfile)
        CircleImageView imgProfile;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtLastMsg)
        TextView txtLastMsg;
        @BindView(R.id.txtTime)
        TextView txtTime;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}