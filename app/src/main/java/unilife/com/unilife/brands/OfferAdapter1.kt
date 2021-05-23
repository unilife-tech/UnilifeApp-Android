//package unilife.com.unilife.brands
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//
//class OfferAdapter1(
//    var context: Context,
//    mainOfferArrayList: ArrayList<BrandsHome.BrandOffersBrands>
//) : RecyclerView.Adapter<OfferAdapter1.MyViewHolder>() {
//
//    var offerlist : ArrayList<BrandsHome.BrandOffersBrands> = ArrayList()
//
//    init {
//        this.offerlist = mainOfferArrayList
//    }
//
//    override fun onCreateViewHolder(viewGroup : ViewGroup, i: Int): MyViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.blog_sports_adapter, viewGroup, false)
//        return MyViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//
//        return if(offerlist.size <=6){
//            offerlist.size
//
//        } else{
//            6
//        }
//    }
//
//    override fun onBindViewHolder(myVIewHolder : MyViewHolder, i : Int) {
//
//        if(offerlist[i].image!=null)
//        {
//            Picasso.with(context).load(WebUrls.offerImageUrl+offerlist[i].image).placeholder(R.drawable.no_image)
//                .into(myVIewHolder.iv_image)
//        }
//        else
//        {
//            myVIewHolder.iv_image.setImageResource(R.drawable.no_image)
//        }
//
//        myVIewHolder.tvTitle.visibility = View.GONE
//
//        myVIewHolder.iv_image.setOnClickListener {
//            context.startActivity(Intent(context,BrandsRedeemedCoupons::class.java)
//                .putExtra("offer_id",offerlist[i].offer_id)
//                .putExtra("name",offerlist[i].brandnameList[0].name))
//        }
//
//    }
//
//
//    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var iv_image : ImageView = itemView.findViewById(R.id.iv_image)
//        var tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
//    }
//
//}