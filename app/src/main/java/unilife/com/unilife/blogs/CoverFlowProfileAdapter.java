package unilife.com.unilife.blogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import unilife.com.unilife.R;
import unilife.com.unilife.retrofit.WebUrls;

import java.util.ArrayList;

public class CoverFlowProfileAdapter  {
/*
    private ArrayList<BlogItem> mData = new ArrayList<>(0);
    private ArrayList<Blog.TeamData> mData2 = new ArrayList<>(0);
    private Context mContext;

    public CoverFlowProfileAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<BlogItem> data, ArrayList<Blog.TeamData> arrayTeam) {
        mData = data;
        if(arrayTeam.size()>0)
        {
            mData2 = arrayTeam;
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.blog_img_item_flow, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = rowView.findViewById(R.id.name);
            viewHolder.image = rowView.findViewById(R.id.ivUser);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        if(!mData2.isEmpty()) {

            mData2.size();

*//*
                        .resize(R.dimen.margin_90, R.dimen.margin_90)
*//*

                Picasso.with(mContext).load(WebUrls.INSTANCE.getPROFILE_IMAGE_URL() + mData2.
                        get(position).component3()).placeholder(R.drawable.no_image)
                        .into(holder.image);
                Log.e("COVERFLOW", WebUrls.INSTANCE.getPROFILE_IMAGE_URL()+mData2.
                        get(position).component3());


            holder.text.setText(mData2.get(position).getName());

        }


      //  holder.image.setImageResource(mData.get(position).imageResId);
      //  holder.text.setText(mData.get(position).titleResId);

        return rowView;
    }


    static class ViewHolder {
        public TextView text;
        public CircleImageView image;
    }*/
}