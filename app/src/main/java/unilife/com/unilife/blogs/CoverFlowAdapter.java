package unilife.com.unilife.blogs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import unilife.com.unilife.R;
import unilife.com.unilife.retrofit.WebUrls;

import java.util.ArrayList;

public class CoverFlowAdapter /*extends BaseAdapter*/ {
//
//    private ArrayList<BlogItem> mData = new ArrayList<>(0);
//    private ArrayList<Blog.SliderData> mData2 = new ArrayList<>();
//    private Context mContext;
//
//    public CoverFlowAdapter(Context context) {
//        mContext = context;
//    }
//
//    public void setData(ArrayList<BlogItem> data, ArrayList<Blog.SliderData> arraySlider) {
//        mData = data;
//
//        if(arraySlider.size()>0)
//        {
//            mData2 = arraySlider;
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return mData.size();
//    }
//
//    @Override
//    public Object getItem(int pos) {
//        return mData.get(pos);
//    }
//
//    @Override
//    public long getItemId(int pos) {
//        return pos;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View rowView = convertView;
//
//        if (rowView == null) {
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            rowView = inflater.inflate(R.layout.item_flow_view, null);
//
//            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.text = (TextView) rowView.findViewById(R.id.name);
//            viewHolder.image = (ImageView) rowView.findViewById(R.id.image);
//            rowView.setTag(viewHolder);
//        }
//
//        ViewHolder holder = (ViewHolder) rowView.getTag();
//
//      //  holder.image.setImageResource(mData.get(position).imageResId);
//      //  holder.text.setText(mData.get(position).titleResId);
//
//        if(!mData2.isEmpty()) {
//            mData2.size();
//
//            Picasso.with(mContext).load(WebUrls.INSTANCE.getBlogImageUrl()+mData2.
//                    get(position).component5()).placeholder(R.drawable.no_image).into(holder.image);
//            // Log.e("adshl;kjaj;g", mData2.get(position).component1());
//        }
//        return rowView;
//    }
//
//
//    static class ViewHolder {
//        public TextView text;
//        public ImageView image;
//    }
}