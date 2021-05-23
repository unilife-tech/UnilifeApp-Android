package unilife.com.unilife.brands;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import unilife.com.unilife.R;

public class SavedCoupansAdapter extends RecyclerView.Adapter<SavedCoupansAdapter.NewsViewHolder> {

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_coupans_adapter,viewGroup,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public  class NewsViewHolder extends RecyclerView.ViewHolder{

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
