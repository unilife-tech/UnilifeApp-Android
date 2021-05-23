//package unilife.com.unilife.chat
//
//import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import androidx.core.content.ContextCompat
//import androidx.core.graphics.drawable.DrawableCompat
//import android.support.v7.widget.LinearLayoutManager
//import android.view.View
//import android.view.WindowManager
//import kotlinx.android.synthetic.main.activity_chat_groups_list.*
//import kotlinx.android.synthetic.main.activity_chat_groups_list.rycChatlist
//import kotlinx.android.synthetic.main.bottom_bar.*
//import unilife.com.unilife.blogs.Blog
//import unilife.com.unilife.brands.BrandsHome
//import unilife.com.unilife.home.Home
//import unilife.com.unilife.R
//
//class ChatGroupsList : AppCompatActivity() ,View.OnClickListener{
//
//    //    @BindView(R.id.rycChatlist)
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chat_groups_list)
//
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivChat.getDrawable()),
//            ContextCompat.getColor(this, R.color.colorPrimary)
//        );
//        tvChat.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivEvent.getDrawable()),
//            ContextCompat.getColor(this, R.color.medium_grey)
//        );
//
//
//        setOnclikListner()
//        //        ButterKnife.bind(this);
//
//        setChatAdapter()
//    }
//
//
//    fun setOnclikListner(){
//        rlEvent.setOnClickListener(this)
//        rlBlogs.setOnClickListener(this)
//        rlBrands.setOnClickListener(this)
//        tvCreateGroup.setOnClickListener(this)
//    }
//
//
//    private fun setChatAdapter() {
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rycChatlist?.layoutManager = layoutManager
//        val groupListAdapter = GroupListAdapter()
//        rycChatlist?.adapter = groupListAdapter
//
//    }
//
//
//    override fun onClick(v: View?) {
//        when(v?.id){
//            R.id.rlEvent->{
//                val intent= Intent(this, Home::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlBlogs->{
//                val intent= Intent(this, Blog::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlBrands->{
//                val intent= Intent(this, BrandsHome::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//            R.id.tvCreateGroup->{
//                val intent= Intent(this,CreateGroupActivity::class.java)
//                startActivity(intent)
////                finish()
//            }
//
//
//        }
//    }
//
//
//
//}
