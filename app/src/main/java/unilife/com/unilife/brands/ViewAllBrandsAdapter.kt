package unilife.com.unilife.brands

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class ViewAllBrandsAdapter(
    var context: Context,
    brandCatList: ArrayList<ViewAllBrands.BrandsCatData>
) : RecyclerView.Adapter<ViewAllBrandsAdapter.MyViewHolder>() {


    var   brandCatList: ArrayList<ViewAllBrands.BrandsCatData> = ArrayList()

    init {
        this.brandCatList =   brandCatList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.blog_category_adapter, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return brandCatList.size

    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {

        myViewHolder.tv_Title.text = brandCatList[i].name
      //  myViewHolder.tvDesc.visibility = View.GONE
        if(brandCatList[i].image!=null){

            Picasso.with(context).load(WebUrls.brandImageUrl+brandCatList[i].image)
                .placeholder(R.drawable.no_image).into(myViewHolder.iv_imageViewAll)

        }
        else
        {
            myViewHolder.iv_imageViewAll.setImageResource(R.drawable.no_image)
        }


        myViewHolder.iv_imageViewAll.setOnClickListener {

//            context.startActivity(Intent(context,TrendingOffers::class.java)
//                .putExtra("offer_id",brandCatList[i].id)
//                .putExtra("name",brandCatList[i].name)
//            )
        }



    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var iv_imageViewAll: ImageView = itemView.findViewById(R.id.iv_image)
       // var tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        var tv_Title: TextView = itemView.findViewById(R.id.tv_categories)
       // var btn_View: TextView = itemView.findViewById(R.id.btn_ViewAll)
    }

}
