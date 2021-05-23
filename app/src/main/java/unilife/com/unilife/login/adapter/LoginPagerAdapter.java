package unilife.com.unilife.login.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import unilife.com.unilife.R;

public class LoginPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater mLayoutInflater;
    ArrayList<Integer> arrayList = new ArrayList<Integer>();

    public LoginPagerAdapter(Context context, ArrayList<Integer> arrayList) {
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
        View itemView = mLayoutInflater.inflate(R.layout.pager_item_login, container, false);

        TextView txtNext = itemView.findViewById(R.id.txtNext);
        TextView txtNote = itemView.findViewById(R.id.txtNote);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        ImageView imgTop = itemView.findViewById(R.id.imgTop);
        ImageView imgStable = itemView.findViewById(R.id.imgStable);


        if (position == 0) {
            txtNext.setVisibility(View.VISIBLE);
            txtNote.setVisibility(View.VISIBLE);
            imgTop.setVisibility(View.VISIBLE);
            imgStable.setVisibility(View.VISIBLE);

            imageView.setVisibility(View.GONE);
        }else {
            txtNext.setVisibility(View.GONE);
            txtNote.setVisibility(View.GONE);
            imgTop.setVisibility(View.GONE);
            imgStable.setVisibility(View.GONE);

            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(arrayList.get(position));
        }


//        RequestOptions defultImg = new RequestOptions()
//                .placeholder(R.drawable.default_image)
//                .error(R.drawable.default_image)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .priority(Priority.HIGH);
//
//        Log.e("url", arrayList.get(position).getImage());
//
//        Glide
//                .with(context)
//                .load(arrayList.get(position).getImage())
//                .apply(defultImg)
//                .into(imageView);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
