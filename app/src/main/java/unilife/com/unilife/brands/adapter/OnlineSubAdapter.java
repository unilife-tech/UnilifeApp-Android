package unilife.com.unilife.brands.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.brands.RedeemActivity;
import unilife.com.unilife.brands.response.BrandDetailsResponse;
import unilife.com.unilife.updated.BaseActivity;

public class OnlineSubAdapter extends RecyclerView.Adapter<OnlineSubAdapter.MyViewHolder> {
    ArrayList<BrandDetailsResponse.Online> arrayList;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OnlineSubAdapter(Context context, ArrayList<BrandDetailsResponse.Online> categoriesBrand) {
        this.context = context;
        this.arrayList = categoriesBrand;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OnlineSubAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvDesc.setText(((BaseActivity) context).getHtmlConverted(arrayList.get(position).getDescription()));
        holder.tvTermsDetails.setText(((BaseActivity) context).getHtmlConverted(arrayList.get(position).getTermsCondition()));
        try {
//            RequestOptions defultImg = new RequestOptions()
//                    .placeholder(R.drawable.default_image)
//                    .error(R.drawable.default_image)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .priority(Priority.HIGH);

//            Glide
//                    .with(context)
//                    .load(WebUrls.INSTANCE.getBrandImageUrl() + categoriesBrand.get(position).getImage())
//                    .apply(defultImg)
//                    .into(holder.imgOffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Spanned title=Html.fromHtml(categoriesBrand.get(position).getBrandOffer().get(0).getDiscountPercent()+"% OFF");
//        Spanned desc=Html.fromHtml(categoriesBrand.get(position).getDescription());
//
//        holder.txtTitle.setText(Html.fromHtml(String.valueOf(title)));
//        holder.txtDesc.setText(Html.fromHtml(String.valueOf(desc)));
//
//        Log.e("title", categoriesBrand.get(position).getBrandOffer().get(0).getDiscountPercent()+" %");
//        Log.e("desc", categoriesBrand.get(position).getDescription());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context, BrandDetailsNew.class);
//                intent.putExtra("item",categoriesBrand.get(position));
//                context.startActivity(intent);
//
//                Intent intent=new Intent(context, BrandsRedeemedCoupons.class);
//                intent.putExtra("offer_id",categoriesBrand.get(position).getId().toString());
//                intent.putExtra("name",categoriesBrand.get(position).getBrandName());
//                context.startActivity(intent);
//            }
//        });

        holder.btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RedeemActivity.class);
//                intent.putExtra("item",categoriesBrand.get(position));
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return categoriesBrand.size();
        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

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