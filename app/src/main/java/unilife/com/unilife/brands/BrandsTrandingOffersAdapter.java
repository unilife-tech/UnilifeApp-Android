//package unilife.com.unilife.brands;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import com.makeramen.roundedimageview.RoundedImageView;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//import unilife.com.unilife.R;
//import unilife.com.unilife.retrofit.WebUrls;
//
//public class BrandsTrandingOffersAdapter extends RecyclerView.Adapter<BrandsTrandingOffersAdapter.NewsViewHolder> {
//
//    public BrandsTrandingOffersAdapter(Context context, ArrayList<BrandsHome.BrandOffersBrands> offerArraylist) {
//        this.context = context;
//        this.arrayList = offerArraylist;
//    }
//
//    private Context context;
//    private ArrayList<BrandsHome.BrandOffersBrands> arrayList ;
//
//    @NonNull
//    @Override
//    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tranding_offer_adapter,viewGroup,false);
//        return new NewsViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
//
//       /* Picasso.with(context).load(WebUrls.blogImageUrl+blogdatalist[i].categories_image)
//                .placeholder(R.drawable.no_image).into(newsViewHolder.iv_image); */
//        Picasso.with(context).load(WebUrls.INSTANCE.getOfferImageUrl()+arrayList.get(i).getImage())
//                .placeholder(R.drawable.no_image).into(newsViewHolder.iv_image);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//
//    }
//
//    public  class NewsViewHolder extends RecyclerView.ViewHolder{
//
//        RoundedImageView iv_image;
//
//        public NewsViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            iv_image=itemView.findViewById(R.id.iv_image);
//
//
//           // iv_image.setOnClickListener(v -> context.startActivity(new Intent(context,BrandsRedeemedCoupons.class)));
//        }
//    }
//}
