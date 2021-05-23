package unilife.com.unilife.brands.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.brands.BrandViewAllActivity;
import unilife.com.unilife.brands.newbrandresponse.BrandResponse2;

public class BrandMainAdapter extends RecyclerView.Adapter<BrandMainAdapter.MyViewHolder> {
    ArrayList<BrandResponse2.Offer> arrayList;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BrandMainAdapter(Context context, ArrayList<BrandResponse2.Offer> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BrandMainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_brand_main, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtHeader.setText(arrayList.get(position).getCategory());
        holder.recycvlerView.setHasFixedSize(true);
        holder.recycvlerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recycvlerView.setAdapter(new BrandSubAdapter(context, arrayList.get(position).getCategoriesBrand()));

        holder.tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, BrandViewAllActivity.class);
                intent.putExtra("item",arrayList.get(position));
                context.startActivity(intent);
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

        @BindView(R.id.txtHeader)
        public TextView txtHeader;
        @BindView(R.id.tvViewAll)
        public TextView tvViewAll;
        @BindView(R.id.recycvlerView)
        public RecyclerView recycvlerView;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}