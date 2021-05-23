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
//class OfferAdapter2(var context: Context, offerArraylist: ArrayList<BrandsHome.BrandOffersBrands>)
//    :RecyclerView.Adapter<OfferAdapter2.MyViewHolder>() {
//
//    var offerArraylist : ArrayList<BrandsHome.BrandOffersBrands> =  ArrayList()
//    init {
//        this.offerArraylist=offerArraylist
//    }
//
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.offer_adapter2, viewGroup, false)
//        return MyViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return if (offerArraylist.size<=3){
//            offerArraylist.size
//
//        } else {
//            3
//        }
//    }
//
//    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
//
//        if(i%2==0){
//
//            myViewHolder.layout2.visibility=View.GONE
//
//
//            Picasso.with(context).load(WebUrls.offerImageUrl+offerArraylist[i].image)
//                .placeholder(R.drawable.no_image).into(myViewHolder.img1)
//
//            myViewHolder.brandname1.text = offerArraylist[i].brandnameList[0].name
//
//            myViewHolder.offer1.text = offerArraylist[i].discount_percent + " % OFF " +  offerArraylist[i].brandnameList[0].name
//
//            myViewHolder.layout1.setOnClickListener {
//
//
//                context.startActivity(Intent(context,BrandsRedeemedCoupons::class.java)
//                    .putExtra("offer_id",offerArraylist[i].offer_id)
//                    .putExtra("name",offerArraylist[i].brandnameList[0].name)
//                )
//            }
//
//        }
//        else {
//                myViewHolder.layout1.visibility = View.GONE
//            if (offerArraylist[i].image != null) {
//                Picasso.with(context).load(WebUrls.offerImageUrl + offerArraylist[i].image)
//                    .placeholder(R.drawable.no_image).into(myViewHolder.img2)
//            } else {
//                myViewHolder.img2.setImageResource(R.drawable.no_image)
//            }
//            myViewHolder.brandname2.text = offerArraylist[i].brandnameList[0].name
//
//            myViewHolder.offer2.text =
//                offerArraylist[i].discount_percent + " % OFF " + offerArraylist[i].brandnameList[0].name
//
//            myViewHolder.layout2.setOnClickListener {
//
//                context.startActivity(
//                    Intent(context, BrandsRedeemedCoupons::class.java)
//                        .putExtra("offer_id", offerArraylist[i].offer_id)
//                        .putExtra("name", offerArraylist[i].brandnameList[0].name)
//                )
//            }
//        }
//
//
//
//    }
//
//
//    class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
//
//        var layout1 : CardView = itemView.findViewById(R.id.layout1)
//        var layout2 : CardView = itemView.findViewById(R.id.layout2)
//
//        var brandname1 : TextView = itemView.findViewById(R.id.brandname1)
//        var brandname2 : TextView = itemView.findViewById(R.id.brandname2)
//
//        var offer1 : TextView = itemView.findViewById(R.id.offer1)
//        var offer2 : TextView = itemView.findViewById(R.id.offer2)
//
//        var img1 :ImageView = itemView.findViewById(R.id.img1)
//        var img2 :ImageView = itemView.findViewById(R.id.img2)
//    }
//
//}