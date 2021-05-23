package unilife.com.unilife.brands

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception


class AllBrandSettingsAdapter(var context: Context, value: String, brandDataArrayList: ArrayList<AllBrandSettings.BrandSettingsData>)
    : RecyclerView.Adapter<AllBrandSettingsAdapter.MyViewHolder>() {

    var value = ""
    var arrayList : ArrayList<AllBrandSettings.BrandSettingsData> = ArrayList()

    init {
        this.arrayList=brandDataArrayList
        this.value=value
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.brand_settings_adapter, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
             return arrayList.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i : Int) {


        try {
                    if(value == "View Redeemed") {

                        myViewHolder.tv_Coupon.visibility = View.VISIBLE
                        myViewHolder.tv_Coupon.text = arrayList[i].discount_code
                    }

            if(arrayList[i].type == "online"){
                        myViewHolder.tv2btn.visibility = View.INVISIBLE
            }
            else{
                myViewHolder.tv1btn.visibility = View.INVISIBLE
            }

            if (arrayList[i].image.isNotEmpty()) {

                Picasso.with(context).load(WebUrls.offerImageUrl + arrayList[i].image)
                    .placeholder(R.drawable.no_image)
                    .into(myViewHolder.iv_imageViewAll)

                Log.e("IwantyourPAth",""+WebUrls.brandImageUrl + arrayList[i].image)
            } else {
                myViewHolder.iv_imageViewAll.setImageResource(R.drawable.no_image)
            }

            myViewHolder.tv_Title.text = Html.fromHtml(arrayList[i].title)
            myViewHolder.tv_Coupon.text = Html.fromHtml(arrayList[i].discount_code)

      /*      myViewHolder.tv1btn.setOnClickListener {////      View Online
                context.startActivity(Intent(context,OnlineRedeemedCoupons::class.java)
                    .putExtra("offer_id",arrayList[i].brand_id)
                )

            }
            myViewHolder.tv2btn.setOnClickListener {///// View Inline
                context.startActivity(Intent(context,RevelRedeemedCoupons::class.java)
                    .putExtra("offer_id",arrayList[i].brand_id)
                )

            }*/
            myViewHolder.cardview.setOnClickListener {
//                context.startActivity(Intent(context,BrandDetailsActivity::class.java)
//                    .putExtra("offer_id",arrayList[i].id)
//                    .putExtra("name",arrayList[i].title)
//                )
            }

        }
        catch (e : Exception){

        }
    }








    class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        var iv_imageViewAll : ImageView =  itemView.findViewById(R.id.iv_imageViewAll)
        var tv_Coupon : TextView = itemView.findViewById(R.id.tv_Coupon)
        var tv_Title : TextView = itemView.findViewById(R.id.tv_Title)
        var tv1btn : TextView = itemView.findViewById(R.id.tv1btn)
        var tv2btn : TextView = itemView.findViewById(R.id.tv2btn)
        var cardview : CardView = itemView.findViewById(R.id.cardview)
    }

}
