package unilife.com.unilife.brands.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import unilife.com.unilife.R;
import unilife.com.unilife.brands.newbrandresponse.BrandResponse2;

public class CustomPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater mLayoutInflater;
    ArrayList<BrandResponse2.Banner> arrayList = new ArrayList<>();

    public CustomPagerAdapter(Context context, ArrayList<BrandResponse2.Banner> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        RequestOptions defultImg = new RequestOptions()
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Log.e("url", arrayList.get(position).getImage());

        Glide
                .with(context)
                .load(arrayList.get(position).getImage())
                .apply(defultImg)
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
