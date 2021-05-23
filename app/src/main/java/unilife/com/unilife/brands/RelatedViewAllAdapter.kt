//package unilife.com.unilife.brands
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.CardView
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
//class RelatedViewAllAdapter(var context: Context, relatedOfferList: ArrayList<RelatedOffersViewAll.RelatedOffersData>)
//    : RecyclerView.Adapter<RelatedViewAllAdapter.MyViewHolder>() {
//
//    var relatedOfferList: ArrayList<RelatedOffersViewAll.RelatedOffersData> = ArrayList()
//
//    init {
//        this.relatedOfferList = relatedOfferList
//
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
//
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.blog_sports_adapter, viewGroup, false)
//        return MyViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//       return relatedOfferList.size
//    }
//
//    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
//        if(relatedOfferList[i].image!=null)
//        {
//            Picasso.with(context).load(WebUrls.offerImageUrl+relatedOfferList[i].image).placeholder(R.drawable.no_image)
//                .into(myViewHolder.iv_image)
//        }
//        else
//        {
//            myViewHolder.iv_image.setImageResource(R.drawable.no_image)
//        }
//        myViewHolder.tvTitle.visibility = View.VISIBLE
//        myViewHolder.tvDesc.visibility = View.VISIBLE
//        myViewHolder.tvTitle.text = relatedOfferList[i].brand_name
//
//        myViewHolder.tvDesc.text = "upto "+ relatedOfferList[i].discount_percent + " % off"
//
//        myViewHolder.cardview.setBackgroundResource(R.drawable.black_border_outline)
//
//
//        myViewHolder.iv_image.setOnClickListener {
//            context.startActivity(
////                Intent(context,BrandDetailsActivity::class.java)
////                    .putExtra("offer_id",relatedOfferList[i].offer_id)
////                    .putExtra("name",relatedOfferList[i].brand_name))
//
//
////        }
//    }
//
//
//    class MyViewHolder (itemView : View) :  RecyclerView.ViewHolder(itemView){
//
//        var iv_image : ImageView = itemView.findViewById(R.id.iv_image)
//        var tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
//        var tvDesc : TextView = itemView.findViewById(R.id.tvDesc)
//        var cardview : CardView = itemView.findViewById(R.id.cardview)
//
//    }
//
//}
