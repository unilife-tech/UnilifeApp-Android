//package unilife.com.unilife.brands
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.home.*
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//
//
//class TrendinBrandsAdapter(
//    internal var context: Context,
//    arrayOffer: ArrayList<TrendingOffers.OffersCat>
//) : RecyclerView.Adapter<TrendinBrandsAdapter.MyViewHolder>() {
//
//
//    val TAG = TrendinBrandsAdapter::class.java.simpleName
//    lateinit var onItemClick: onItemClickListener
//    var arrayOffer: ArrayList<TrendingOffers.OffersCat> = ArrayList()
//    init {
//       // this.homeList = homeList
//        this.arrayOffer=arrayOffer
//        this.context = context
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
//        val view =
//                LayoutInflater.from(viewGroup.context).inflate(R.layout.tranding_brand_offer, viewGroup, false)
//        return MyViewHolder(view)
//    }
//
//    @SuppressLint("SetTextI18n")
//    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
//
//
//        if(arrayOffer[i].image!=null) {
//            Picasso.with(context).load(WebUrls.offerImageUrl + arrayOffer[i].image)
//                .placeholder(R.drawable.no_image).into(myViewHolder.iv_image)
//        }
//        else
//        {
//            myViewHolder.iv_image.setImageResource(R.drawable.no_image)
//        }
//
//        myViewHolder.tvOff.text = arrayOffer[i].discount_percent + "%"
//
//        Log.e("sfsfsdfgxfgnjnjhj",""+WebUrls.offerImageUrl+arrayOffer[i].image)
//
//        myViewHolder.iv_image.setOnClickListener {
//            context.startActivity(Intent(context,BrandDetailsActivity::class.java)
//                .putExtra("offer_id",arrayOffer[i].brand_id)
//                .putExtra("name",arrayOffer[i].name)
//            )
//        }
//
//    }
//
//
//    override fun getItemCount(): Int {
//       // return homeList!!.size
//        return arrayOffer.size
//    }
//
//    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var tvOff: TextView
//        internal var iv_image: ImageView
//
//        init {
//
//            tvOff = itemView.findViewById(R.id.tvOff)
//            iv_image = itemView.findViewById(R.id.iv_image)
//
//            itemView.setOnClickListener {
//                //onItemClick.onItemClick(layoutPosition,homeList)
////                context.startActivity(Intent(context, BrandsRedeemedCoupons::class.java))
//            }
//        }
//    }
//
//    /* todo @24 OCT START */
//    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
//        this.onItemClick = onItemClick
//    }
//
//    public interface onItemClickListener {
//        public fun onItemClick(position: Int,  brandOffersList: ArrayList<HomeModel.BrandOffers>? )
//    }
//    /* todo @24 OCT END */
//
//    fun setAdapter(rycItem: RecyclerView, getdataAl: ArrayList<String>
//    ) {
//        val homePostAdapter = HomePostAdapter(context, getdataAl)
//        rycItem.adapter = homePostAdapter
//        rycItem.addItemDecoration(CirclePagerIndicatorDecoration())
//    }
//
//
//}