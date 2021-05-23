//package unilife.com.unilife.blogs
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.makeramen.roundedimageview.RoundedImageView
//
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.brands.BrandsRedeemedCoupons
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//import kotlin.collections.ArrayList
//
//class BlogElectronicsAdapter(
//    internal var context: Context,
//    arrayOffer: ArrayList<Blog.OfferData>,
//    brandOfferList: ArrayList<Blog.BrandOfferData>
//) :
//    RecyclerView.Adapter<BlogElectronicsAdapter.ElectronicsViewHolder>() {
//
//    private var array = ArrayList<Blog.OfferData>()
//    private var catOfferList = ArrayList<Blog.CatBlogOfferData>()
//    private var brandOfferList = ArrayList<Blog.BrandOfferData>()
//
//
//    init {
//        this.array = arrayOffer
//        this.brandOfferList=brandOfferList
//
//    }
//
//
//    override fun onBindViewHolder(electronicsViewHolder: ElectronicsViewHolder, i: Int) {
//
//        /*Log.e("brandarraylist",""+ brandOfferList.size)
//        Log.e("mainarray",""+ array[pos].catBlogOfferList[i].brandOfferList.size)*/
//
//      /*  Log.e("brandarraylist",""+ brandOfferList1.size)
//        Log.e("brandarraylist",""+ brandOfferList1.size)
//        Log.e("brandarraylist",""+ brandOfferList1.size)*/
//
//        if(brandOfferList[i].image!=null) {
//            Picasso.with(context).load(WebUrls.offerImageUrl + brandOfferList[i].image).placeholder(R.drawable.no_image)
//                .into(electronicsViewHolder.iv_image)
//
//        }
//        else
//        {
//            electronicsViewHolder.iv_image.setImageResource(R.drawable.no_image)
//        }
//
//        electronicsViewHolder.iv_image.setOnClickListener { context.startActivity(Intent(context, BrandsRedeemedCoupons::class.java)
//            .putExtra("offer_id",brandOfferList[i].id)
//            .putExtra("name",brandOfferList[i].name)
//        )
//        }
//
//        /*electronicsViewHolder.iv_image.setOnClickListener {
//
//            var intent = Intent(context,BrandsRedeemedCoupons::class.java)
//            intent.putExtra("brandlist",brandOfferList)
//            intent.putExtra("position",i)
//            context.startActivity(intent)
//
//        }*/
//
//    }
//
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ElectronicsViewHolder {
//        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.blog_electronic_adapter, viewGroup, false)
//        return ElectronicsViewHolder(view)
//    }
//
//
//
//    override fun getItemCount(): Int {
//        return if(brandOfferList.size<=6) {
//            brandOfferList.size
//        } else{
//            6
//        }
//    }
//
//    inner class ElectronicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var iv_image: RoundedImageView = itemView.findViewById(R.id.iv_image)
//
//        init {
//
//
//        }
//    }
//}
