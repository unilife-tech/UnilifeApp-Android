package unilife.com.unilife.profile.adapters;

import android.content.Context;
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

public class ProfileAdapter_1 extends RecyclerView.Adapter<ProfileAdapter_1.MyViewHolder> {

    ArrayList<String> arrayList;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProfileAdapter_1(Context context) {
         this.context = context;
    }

    public void updateData(ArrayList<String> arrayList) {
         this.arrayList = arrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProfileAdapter_1.MyViewHolder onCreateViewHolder(ViewGroup parent,
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


//        holder.textView.setText(arrayList.get(position).toString());

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


        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }
    }
}