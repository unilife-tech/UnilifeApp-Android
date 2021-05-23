package unilife.com.unilife.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import unilife.com.unilife.R;

public class HomeLatestBlogAdapter extends RecyclerView.Adapter<HomeLatestBlogAdapter.NewsViewHolder> {

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

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
