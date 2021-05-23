//package unilife.com.unilife.brands
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import unilife.com.unilife.R
//
//class CatListBrandAdapter(var context: Context, mainOfferArrayList: ArrayList<BrandsHome.OfferData>)
//    : RecyclerView.Adapter<CatListBrandAdapter.MyViewHolder>() {
//
//    var mainOfferArrayList: ArrayList<BrandsHome.OfferData> = ArrayList()
//
//    init {
//        this.mainOfferArrayList = mainOfferArrayList
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyViewHolder {
//        val view =
//            LayoutInflater.from(viewGroup.context)
//                .inflate(R.layout.cat_spin_list, viewGroup, false)
//        return MyViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return if (mainOfferArrayList.size <= 9) {
//            mainOfferArrayList.size
//
//        } else {
//            10
//        }
//    }
//
//    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
//
//        myViewHolder.tv_showName.text = mainOfferArrayList[i].name
//        if(i==mainOfferArrayList.size-1){
//            myViewHolder.tv_ViewAll.visibility = View.VISIBLE
//        }
//
//        myViewHolder.tv_showName.setOnClickListener {
//
//            context.startActivity(
//                Intent(context, TrendingOffers::class.java)
//                    .putExtra("offer_id", mainOfferArrayList[i].id)
//                    .putExtra("name", mainOfferArrayList[i].name)
//            )
//
//
//        }
//
//
//        myViewHolder.tv_ViewAll.setOnClickListener {
//            context.startActivity(Intent(context, ViewAllBrands::class.java))
//        }
//
//
//    }
//
//
//    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//
//        var tv_showName: TextView = itemView.findViewById(R.id.tv_showName)
//        var tv_ViewAll : TextView = itemView.findViewById(R.id.tv_ViewAll)
//        var viewspin2 : View = itemView.findViewById(R.id.viewspin2)
//
//    }
//}
