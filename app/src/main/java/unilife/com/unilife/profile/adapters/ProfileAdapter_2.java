package unilife.com.unilife.profile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.editprofiel.EditSkillActivity;
import unilife.com.unilife.profile.model.responses.SkillsResponse;

public class ProfileAdapter_2 extends RecyclerView.Adapter<ProfileAdapter_2.MyViewHolder> {

    ArrayList<SkillsResponse.Datum> arrayList = new ArrayList<>();
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProfileAdapter_2(Context context) {
        this.context = context;
    }

    public void updateData(ArrayList<SkillsResponse.Datum> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProfileAdapter_2.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_profile_2, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(arrayList.get(position).getSkillName());

        if (arrayList.get(position).isSelect()) {
            holder.constraint.setBackgroundResource(R.drawable.back_profile_skill_fill);
        } else {
            holder.constraint.setBackgroundResource(R.drawable.back_profile_skill);
        }

        holder.txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.get(position).setSelect(false);
                ((EditSkillActivity) context).strConcat = getUpSkills(position);
                notifyDataSetChanged();
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.get(position).setSelect(true);
                notifyDataSetChanged();
            }
        });

    }

    private String getUpSkills(int pos) {
        ArrayList<SkillsResponse.Datum> arrayList1 = arrayList;
//        arrayList1.remove(pos);
        String strConcat = "";

        for (int i = 0; i < arrayList1.size(); i++) {
            if (arrayList1.get(pos).isSelect())
            strConcat = strConcat + arrayList1.get(i).getSkillName() + ",";
        }

        return strConcat;
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

        @BindView(R.id.text)
        TextView textView;
        @BindView(R.id.txtClose)
        TextView txtClose;
        @BindView(R.id.constraint)
        ConstraintLayout constraint;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}