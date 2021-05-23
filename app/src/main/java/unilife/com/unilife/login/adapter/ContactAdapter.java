package unilife.com.unilife.login.adapter;

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
import unilife.com.unilife.R;
import unilife.com.unilife.login.AddFromContactActivity;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    public ArrayList<AddFromContactActivity.ItemContact> arrayList = new ArrayList<>();
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactAdapter(Context context) {
        this.context = context;
    }

    public void updateData(ArrayList<AddFromContactActivity.ItemContact> data) {
        this.arrayList = data;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_friends, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (arrayList.get(position).isInContact() && !arrayList.get(position).getUserId().equals("")) {
            holder.tvAction.setText(arrayList.get(position).getAction());
        }

        holder.tvName.setText(arrayList.get(position).getName());
//        Glide.with(context)
//                .load(arrayList.get(position).getUserProfileImage())
//                .apply(defultImg)
//                .into(holder.ivImg);

        holder.tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tvAction.getText().toString().equalsIgnoreCase("Invite")) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Unilife");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + " https://play.google.com/store/apps/details?id=unilife.com.unilife";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "Invite " + arrayList.get(position).getName()));
                } else if (holder.tvAction.getText().toString().equalsIgnoreCase("Add Friend")){
//                    if (!arrayList.get(position).isSelect()) {
                    ((AddFromContactActivity) context).sendRequest("send", arrayList.get(position).getUserId());
//                        arrayList.get(position).setSelect(true);
//                    } else {
//                        ((AddFromContactActivity)context).sendRequest("cancel",arrayList.get(position).getUserId());
//                        arrayList.get(position).setSelect(false);
//                    }

//                    notifyDataSetChanged();
                }
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

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAction)
        TextView tvAction;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            tvAction.setText("Invite");
        }
    }
}