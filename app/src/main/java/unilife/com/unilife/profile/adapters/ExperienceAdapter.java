package unilife.com.unilife.profile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.MyAccountActivity;
import unilife.com.unilife.profile.editprofiel.EditExperienceActivity;
import unilife.com.unilife.profile.model.UserExperience;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.MyViewHolder> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    ArrayList<UserExperience> arrayList = new ArrayList<>();
    Context context;
    int which = 0;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExperienceAdapter(Context context, int which) {
        this.context = context;
        this.which = which;
    }

    public void updateData(ArrayList<UserExperience> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExperienceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_profile_1, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txt1.setText(arrayList.get(position).getDesignation());
        holder.txt2.setText(arrayList.get(position).getCompanyName());
        holder.txt3.setText(arrayList.get(position).getStartDate() + " - " + arrayList.get(position).getEndDate());
        viewBinderHelper.bind(holder.swipelayout, arrayList.get(position).getId());

        if (which == 2) {
            holder.txtMore.setVisibility(View.GONE);
            holder.swipelayout.setLockDrag(true);
        }

        holder.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipelayout.open(true);
            }
        });

        holder.txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipelayout.close(true);
            }
        });

        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditExperienceActivity.class);
                intent.putExtra("item", arrayList.get(position));
                context.startActivity(intent);
            }
        });

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyAccountActivity) context).deleteExperience(arrayList.get(position).getId());
            }
        });
        holder.imgLogo.setImageResource(R.drawable.image_experience);
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

        @BindView(R.id.txt1)
        public TextView txt1;
        @BindView(R.id.txt2)
        public TextView txt2;
        @BindView(R.id.txt3)
        public TextView txt3;
        @BindView(R.id.imgLogo)
        public ImageView imgLogo;

        @BindView(R.id.swipelayout)
        SwipeRevealLayout swipelayout;
        @BindView(R.id.txtMore)
        TextView txtMore;

        @BindView(R.id.txtCancel)
        TextView txtCancel;
        @BindView(R.id.txtEdit)
        TextView txtEdit;
        @BindView(R.id.txtDelete)
        TextView txtDelete;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}