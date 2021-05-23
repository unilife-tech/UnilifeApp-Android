//package unilife.com.unilife.chat
//
//import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import androidx.core.content.ContextCompat
//import androidx.core.graphics.drawable.DrawableCompat
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.PopupMenu
//import android.view.View
//import android.view.WindowManager
//import kotlinx.android.synthetic.main.activity_requst_recieve.*
//import kotlinx.android.synthetic.main.bottom_bar.*
//import unilife.com.unilife.blogs.Blog
//import unilife.com.unilife.brands.BrandsHome
//import unilife.com.unilife.brands.TrendingOffers
//import unilife.com.unilife.home.Home
//import unilife.com.unilife.R
//
//class RequstRecieve : AppCompatActivity(),View.OnClickListener{
//
//
////    @BindView(R.id.rycGroup)
//
//
//    override fun onCreate(savedInstanceState: Bundle?)  {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_requst_recieve)
////        ButterKnife.bind(this)
//
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivChat.getDrawable()),
//            ContextCompat.getColor(this, R.color.colorPrimary)
//        );
//
//        tvChat.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivEvent.getDrawable()),
//            ContextCompat.getColor(this, R.color.medium_grey)
//        );
//
//
//        setOnclikListner()
//
//        setChatAdapter()
//        setChatgroupAdapter()
//    }
//
//    private fun setChatgroupAdapter() {
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        rycGroup?.layoutManager = layoutManager
////        val chatgroupAdapter = OnlineFriendsAdapter(this)
////        rycGroup?.adapter = chatgroupAdapter
//
//    }
//
//    private fun setChatAdapter() {
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rycChatlist?.layoutManager = layoutManager
//        val recieveRequstAdapter = RecieveRequstAdapter(this)
//        rycChatlist?.adapter = recieveRequstAdapter
//
//    }
//
//    fun setOnclikListner(){
//        rlEvent.setOnClickListener(this)
//        rlBlogs.setOnClickListener(this)
//        rlBrands.setOnClickListener(this)
//        tvFriend_list.setOnClickListener(this)
//        dot_line.setOnClickListener(this)
//        ivback_arrow.setOnClickListener(this)
//    }
//
//
//    override fun onClick(v: View?) {
//       when(v?.id){
//           R.id.rlEvent->{
//               val intent= Intent(this,Home::class.java)
//               startActivity(intent)
//           }
//
//           R.id.rlBlogs->{
//               val intent= Intent(this,Blog::class.java)
//               startActivity(intent)
//           }
//
//           R.id.rlBrands->{
//               val intent= Intent(this,BrandsHome::class.java)
//               startActivity(intent)
//               finish()
//           }
//
//           R.id.tvFriend_list->{
//               val intent= Intent(this,ChatUsersList::class.java)
//               startActivity(intent)
//               finish()
//           }
//
//
//           R.id.ivback_arrow->{
//               onBackPressed()
//           }
//
//           R.id.dot_line->{
//              popup1Menu()
//           }
//       }
//    }
//
//    override fun onBackPressed() {
//        finish()
//    }
//
//    fun popup1Menu(){
//
//
//        val popupMenu=PopupMenu(this,dot_line)
//        popupMenu.menuInflater.inflate(R.menu.chat_menu_item,popupMenu.menu)
//        popupMenu.show()
//        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
//            when(item?.itemId) {
//                R.id.create_group -> {
//                    val intent = Intent(this, ChatGroupsList::class.java)
//                    startActivity(intent)
//                }
//                R.id.change_walpaper ->{
//                    val intent= Intent(this, TrendingOffers::class.java)
//                    startActivity(intent)
//                }
//                R.id.go_private ->{
//
//                }
//                R.id.requests_recieved ->{
//
//                }
//
//            }
//            true
//        })
//
//    }
//
//
//
//
//}
