//package unilife.com.unilife.home
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.brands.BrandDetailsActivity
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//
//class HomeAddAdapter(
//    internal var context: Context,
//    internal var catOffersList: ArrayList<HomeModel.CategoriesBrandModel>?,
//    internal var brandOffersList1:ArrayList<HomeModel.BrandOffers>?
//) : RecyclerView.Adapter<HomeAddAdapter.NewsViewHolder>() {
//    init {
//        this.catOffersList = catOffersList
//        this.context = context
//        this.brandOffersList1 = brandOffersList1
//
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.brands_offer_adapter, viewGroup, false)
//        return NewsViewHolder(view)
//    }
//
//    override fun onBindViewHolder(newsViewHolder: NewsViewHolder, i: Int) {
//
//        try {
//            Picasso.with(context!!).load(WebUrls.offerImageUrl + catOffersList!![0].brandsOffersList[i].image).placeholder(R.drawable.no_image)
//                .into(newsViewHolder.iv_image)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//
//        newsViewHolder.iv_image.setOnClickListener {
//            context.startActivity(Intent( context,BrandDetailsActivity::class.java)
//                .putExtra("offer_id", catOffersList!![0].brandsOffersList[i].id)
//                .putExtra("name", catOffersList!![0].brand_name)
//            )
//
//        }
//
//
////        Log.e("imagelink","13.59.5.253/unilife/public/offer_imgs/"+brandOffersList!![i].image)
//    }
//
//    override fun getItemCount(): Int {
////        Log.e("sizebrand", brandOffersList?.size.toString())
//        return if (catOffersList!![0].brandsOffersList.size <= 4) {
//            catOffersList!![0].brandsOffersList.size
//        } else {
//            4
//        }
//    }
//
//    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var iv_image: ImageView = itemView.findViewById(R.id.iv_image)
//
//    }
//}
