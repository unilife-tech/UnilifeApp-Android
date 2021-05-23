package unilife.com.unilife.chat.update;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import unilife.com.unilife.chat.adapter.ChatHorizontalAdapter;
import unilife.com.unilife.chat.adapter.ChatListingAdapter;
import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class ChatActivity extends BaseActivity {

//    @BindView(R.id.recyclerChatHorizontal)
//    RecyclerView recyclerChatHorizontal;
//    @BindView(R.id.recyclerChatListing)
//    RecyclerView recyclerChatListing;
    ChatHorizontalAdapter chatHorizontalAdapter;
    ChatListingAdapter chatListingAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        hideKeyboard();
//        init();
    }

//    private void init() {
//        recyclerChatHorizontal.setNestedScrollingEnabled(false);
//        recyclerChatListing.setNestedScrollingEnabled(false);
//
//        chatHorizontalAdapter = new ChatHorizontalAdapter(mContext);
//        recyclerChatHorizontal.setHasFixedSize(true);
//        recyclerChatHorizontal.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//        recyclerChatHorizontal.setAdapter(chatHorizontalAdapter);
//
//        chatListingAdapter=new ChatListingAdapter(mContext);
//        recyclerChatListing.setHasFixedSize(true);
//        recyclerChatListing.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerChatListing.setAdapter(chatListingAdapter);
//    }

    @Override
    protected int getContentView() {
        return R.layout.activity_chat;
    }
}