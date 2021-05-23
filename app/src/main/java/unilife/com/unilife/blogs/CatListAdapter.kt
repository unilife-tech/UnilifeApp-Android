//package unilife.com.unilife.blogs
//
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
//class CatListAdapter(
//    var context: Context,
//    arrayBlogs: ArrayList<Blog.BlogerData>
//) : RecyclerView.Adapter<CatListAdapter.MyViewHolder>() {
//
//    var arrayList: ArrayList<Blog.BlogerData> = ArrayList()
//
//    init {
//        this.arrayList = arrayBlogs
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
//        return if (arrayList.size <= 9) {
//            arrayList.size
//
//        } else {
//            10
//        }
//
//    }
//
//    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
//
//        myViewHolder.tv_showName.text = arrayList[i].categories_name
//        if(i==arrayList.size-1){
//            myViewHolder.tv_ViewAll.visibility = View.VISIBLE
//        }
//
//        myViewHolder.tv_showName.setOnClickListener {
//
//            context.startActivity(Intent(context,ViewAllBlogs::class.java)
//                .putExtra("category_id", arrayList[i].id)
//            )
//
//
//        }
//
//
//        myViewHolder.tv_ViewAll.setOnClickListener {
//           context.startActivity(Intent(context,ViewAllCategories::class.java))
//        }
//
//    }
//
//
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var tv_showName: TextView = itemView.findViewById(R.id.tv_showName)
//        var tv_ViewAll : TextView = itemView.findViewById(R.id.tv_ViewAll)
//        var viewspin2 : View = itemView.findViewById(R.id.viewspin2)
//    }
//}