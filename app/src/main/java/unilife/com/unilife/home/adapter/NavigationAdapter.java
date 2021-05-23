package unilife.com.unilife.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.model.ItemSearch;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.MyViewHolder> {

    ArrayList<ItemSearch> arrayList = new ArrayList<>();
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NavigationAdapter(Context context, ArrayList<ItemSearch> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

//    public void updateData(ArrayList<ItemSearch> arrayList) {
//        this.arrayList = arrayList;
//        notifyDataSetChanged();
//    }

    // Create new views (invoked by the layout manager)
    @Override
    public NavigationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_navigation, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imgNav.setImageResource(arrayList.get(position).getImage());
        holder.txtNav.setText(arrayList.get(position).getName());

        if (arrayList.get(position).getName().equals("")) {
            holder.txtNav.setPadding(0, 0, 0, 0);
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

        @BindView(R.id.imgNav)
        ImageView imgNav;
        @BindView(R.id.txtNav)
        TextView txtNav;
        @BindView(R.id.constraintMain)
        ConstraintLayout constraintMain;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}