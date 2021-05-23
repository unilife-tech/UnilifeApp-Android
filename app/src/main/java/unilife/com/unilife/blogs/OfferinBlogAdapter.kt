//package unilife.com.unilife.blogs
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import unilife.com.unilife.brands.TrendingOffers
//import unilife.com.unilife.R
//
//class OfferinBlogAdapter(
//    internal var context: Context,
//    arrayOffer: ArrayList<Blog.OfferData>
//) :
//    RecyclerView.Adapter<OfferinBlogAdapter.OfferViewHolder>() {
//
//    var arrayOffer : ArrayList<Blog.OfferData> = ArrayList()
//    var value = ""
//
//    init {
//        this.arrayOffer=arrayOffer
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup , i: Int): OfferViewHolder {
//        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.offerin_blog_adapter, viewGroup, false)
//        return OfferViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        /*var size=0
//        size = if(arrayOffer.size<=6) {
//            arrayOffer.size
//        } else {
//            6
//        }
//        return size*/
////        Log.e("arrayoffersizefbhfg",""+arrayOffer.size)
//
//
//        if(arrayOffer.size < 6){
////            Log.e("arrayoffersize",""+arrayOffer.size)
//            return arrayOffer.size
//        }
//        else{
//          return  6
//        }
//    }
//
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//
//    override fun onBindViewHolder(offerViewHolder: OfferViewHolder, i : Int) {
//
//        offerViewHolder.tv_ViewAll.setOnClickListener {
//            var ii = Intent(context,TrendingOffers::class.java)
//            ii.putExtra("offer_id",arrayOffer[0].id)
//            ii.putExtra("position", i)
//            ii.putExtra("value",value)
//            ii.putExtra("name",arrayOffer[0].name)
//            context.startActivity(ii)
//
//        }
//
//        offerViewHolder.tvOffers.text=arrayOffer[i].name
//
//        val offersChildAdapter = BlogElectronicsAdapter(context,arrayOffer,arrayOffer[i].brandOfferList)
//        offerViewHolder.ryc_childOffer.adapter = offersChildAdapter
//    }
//
//    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        var tvOffers : TextView = itemView.findViewById(R.id.tvOffers)
//        var tv_ViewAll : TextView = itemView.findViewById(R.id.tv_ViewAll)
//        var ryc_childOffer : RecyclerView = itemView.findViewById(R.id.ryc_childOffer)
//
//
//
//    }
//}