package unilife.com.unilife.brands.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.brands.RedeemActivity;
import unilife.com.unilife.brands.response.BrandDetailsResponse;
import unilife.com.unilife.updated.BaseActivity;

public class OnlineMainAdapter extends RecyclerView.Adapter<OnlineMainAdapter.MyViewHolder> {
    ArrayList<BrandDetailsResponse.Online> arrayList;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OnlineMainAdapter(Context context, ArrayList<BrandDetailsResponse.Online> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OnlineMainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_online_main, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.txtHeader.setText(arrayList.get(position).getName());

        holder.txtHeader.setText(arrayList.get(position).getHeading());
        holder.txtDiscountMsg.setText(arrayList.get(position).getDiscountMessage());
        holder.tvDesc.setText(((BaseActivity) context).getHtmlConverted(arrayList.get(position).getDescription()
        ));
        holder.tvTermsDetails.setText(((BaseActivity) context).getHtmlConverted(arrayList.get(position).getTermsCondition()));

        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.constraintDetails.getVisibility() == View.VISIBLE) {
                    holder.constraintDetails.setVisibility(View.GONE);
                    holder.txtHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_outline_keyboard_arrow_down_24, 0);
                } else {
                    holder.constraintDetails.setVisibility(View.VISIBLE);
                    holder.txtHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                }
            }
        });

        holder.btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RedeemActivity.class);
                intent.putExtra("item",arrayList.get(position));
                context.startActivity(intent);
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

        @BindView(R.id.txtHeader)
        public TextView txtHeader;
        @BindView(R.id.txtDiscountMsg)
        public TextView txtDiscountMsg;
 //      @BindView(R.id.tvViewAll)
//        public TextView tvViewAll;
        @BindView(R.id.constraintDetails)
        public ConstraintLayout constraintDetails;
        @BindView(R.id.tvDesc)
        public TextView tvDesc;
        @BindView(R.id.tvTermsDetails)
        public TextView tvTermsDetails;
        @BindView(R.id.btnRedeem)
        public Button btnRedeem;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}