package unilife.com.unilife.profile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.profile.model.UserCourse;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {
    ArrayList<UserCourse> arrayList=new ArrayList<>();
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CoursesAdapter(Context context) {
         this.context = context;
    }

    public void updateData(ArrayList<UserCourse> arrayList) {
         this.arrayList = arrayList;
         notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CoursesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_courses, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txt1.setText(arrayList.get(position).getName());

        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+arrayList.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });

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

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }
    }
}