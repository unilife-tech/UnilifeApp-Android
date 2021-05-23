package unilife.com.unilife.brands;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import unilife.com.unilife.R;

public class BrandsNewlyOffersAdapter extends RecyclerView.Adapter<BrandsNewlyOffersAdapter.NewsViewHolder> {
    public BrandsNewlyOffersAdapter(Context context) {
        this.context = context;
    }

    Context context;


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brands_newly_offer_adapter,viewGroup,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public  class NewsViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView iv_image;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image=itemView.findViewById(R.id.iv_image);

            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    context.startActivity(new Intent(context, BrandDetailsActivity.class));

                }
            });


        }
    }
}
