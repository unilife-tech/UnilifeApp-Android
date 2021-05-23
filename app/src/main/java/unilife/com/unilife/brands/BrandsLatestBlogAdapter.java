package unilife.com.unilife.brands;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import unilife.com.unilife.R;

public class BrandsLatestBlogAdapter extends RecyclerView.Adapter<BrandsLatestBlogAdapter.NewsViewHolder> {

    Context context;

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.blog_news_adapter,viewGroup,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public  class NewsViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_image;
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

