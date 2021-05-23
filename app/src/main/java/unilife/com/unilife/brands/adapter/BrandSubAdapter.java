package unilife.com.unilife.brands.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.brands.BrandDetailsNew;
import unilife.com.unilife.brands.newbrandresponse.BrandResponse2;
import unilife.com.unilife.retrofit.WebUrls;

public class BrandSubAdapter extends RecyclerView.Adapter<BrandSubAdapter.MyViewHolder> {
    ArrayList<BrandResponse2.CategoriesBrand> categoriesBrand;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BrandSubAdapter(Context context, ArrayList<BrandResponse2.CategoriesBrand> categoriesBrand) {
        this.context = context;
        this.categoriesBrand = categoriesBrand;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BrandSubAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_sub_brand, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.textView.setText(arrayList.get(position).toString());

        try {
            RequestOptions defultImg = new RequestOptions()
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.IMMEDIATE);

            Glide
                    .with(context)
                    .load(categoriesBrand.get(position).getImage())
                    .apply(defultImg)
                    .into(holder.imgOffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Spanned title = Html.fromHtml(categoriesBrand.get(position).getBrandName());
        Spanned desc = Html.fromHtml(categoriesBrand.get(position).getDescription());

        holder.txtTitle.setText(Html.fromHtml(String.valueOf(title)));
        holder.txtDesc.setText(Html.fromHtml(String.valueOf(desc)));

//        Log.e("title", categoriesBrand.get(position).getBrandOffer().get(0).getDiscountPercent()+" %");
//        Log.e("desc", categoriesBrand.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrandDetailsNew.class);
                intent.putExtra("id", categoriesBrand.get(position).getId());
                intent.putExtra("name", categoriesBrand.get(position).getBrandName());
                intent.putExtra("image", categoriesBrand.get(position).getImage());
                context.startActivity(intent);

//                Intent intent=new Intent(context, BrandsRedeemedCoupons.class);
//                intent.putExtra("offer_id",categoriesBrand.get(position).getId().toString());
//                intent.putExtra("name",categoriesBrand.get(position).getBrandName());
//                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categoriesBrand.size();
//        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.imgOffer)
        public ImageView imgOffer;
        @BindView(R.id.txtTitle)
        public TextView txtTitle;
        @BindView(R.id.txtDesc)
        public TextView txtDesc;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}