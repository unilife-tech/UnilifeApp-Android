//package unilife.com.unilife.brands
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.CardView
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//
//class BrandCategoriesAdapter(
//    var context: Context,
//    mainOfferArrayList: ArrayList<BrandsHome.OfferData>
//) : RecyclerView.Adapter<BrandCategoriesAdapter.CategoriesViewHolder>() {
//
//    var arrayList : ArrayList<BrandsHome.OfferData> = ArrayList()
//    init{
//
//        this.arrayList = mainOfferArrayList
//    }
//
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CategoriesViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.blog_category_adapter, viewGroup, false)
//        return CategoriesViewHolder(view)
//    }
//
//    override fun onBindViewHolder(categoriesViewHolder: CategoriesViewHolder, i: Int) {
//        if(i>2)
//        {
//            categoriesViewHolder.tv_categories.text = arrayList[i].name
//
//            if(arrayList[i]!=null) {
//                Picasso.with(context).load(WebUrls.brandImageUrl + arrayList[i].image)
//                    .placeholder(R.drawable.no_image).into(categoriesViewHolder.iv_image)
//                Log.e("Sfsafsffegd", "" + WebUrls.offerImageUrl + arrayList[i].image)
//            }
//            else
//            {
//                categoriesViewHolder.iv_image.setImageResource(R.drawable.no_image)
//
//            }
//
//            categoriesViewHolder.cvblogsCat.visibility = View.VISIBLE
//
//        }
//        else
//        {
//            categoriesViewHolder.cvblogsCat.visibility = View.GONE
//        }
//
//
//        categoriesViewHolder.iv_image.setOnClickListener {
//
//            context.startActivity(Intent(context,TrendingOffers::class.java)
//                .putExtra("offer_id",arrayList[i].id)
//                .putExtra("name",arrayList[i].name)
//
//            )
//        }
//
//
//    }
//
//    override fun getItemCount(): Int {
//        if(arrayList.size>=6){
//            return 6
//        }
//        else{
//            return arrayList.size
//        }
//    }
//
//    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    {
//        var iv_image : ImageView = itemView.findViewById(R.id.iv_image)
//        var tv_categories : TextView = itemView.findViewById(R.id.tv_categories)
//        var cvblogsCat: CardView = itemView.findViewById(R.id.cvblogsCat)
//
//
//    }
//}
