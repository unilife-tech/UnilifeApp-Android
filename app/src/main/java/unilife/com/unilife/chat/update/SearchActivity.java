package unilife.com.unilife.chat.update;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.Chat;
import unilife.com.unilife.chat.ChatAdapter;
import unilife.com.unilife.chat.adapter.SearchAdapter;
import unilife.com.unilife.chat.model.ItemSearch;
import unilife.com.unilife.retro.WebUrls;
import unilife.com.unilife.retrofit.RetrofitResponse;
import unilife.com.unilife.retrofit.RetrofitService;
import unilife.com.unilife.updated.BaseActivity;
import unilife.com.unilife.utils.Alerts;
import unilife.com.unilife.utils.RecyclerItemClickListener;

public class SearchActivity extends BaseActivity implements RetrofitResponse {

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<ItemSearch> arrayList = new ArrayList<>();

    String[] arrName = {"Students", "Professors/Teachers", "Administrative Staff", "Chats", "Media"};
    int[] arrImage = {R.drawable.ic_assignment, R.drawable.ic_school_24px, R.drawable.ic_account_circle_black_24dp, R.drawable.ic_baseline_forum_24, R.drawable.ic_baseline_perm_media_24};

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        for (int i = 0; i < arrImage.length; i++) {
            ItemSearch itemSearch1 = new ItemSearch();
            itemSearch1.setImage(arrImage[i]);
            itemSearch1.setName(arrName[i]);
            arrayList.add(itemSearch1);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new SearchAdapter(mContext, arrayList));

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    callSearchChats();
                    return true;
                }
                return false;
            }
        });

//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.toString().trim().length() > 0)
//
//            }
//        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    public void goBack(View view) {
        finish();
    }

    private void callSearchChats() {
        Log.e("Called", "searchChat");

        String search = etSearch.getText().toString().trim();
        if (Alerts.isNetworkAvailable(this)) {
            try {
                JSONObject postParam = new JSONObject();
                try {
                    postParam.put(
                            "user_id",
                            PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                    );
                    postParam.put("search", search);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                new RetrofitService(
                        this, this, WebUrls.CHAT_SEARCH, WebUrls.CHAT_SEARCH_CODE,
                        3, postParam
                ).callService(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponse(int requestCode, @NotNull String response) {
        Log.e("response", response.toLowerCase());
        try {
            JSONObject object = new JSONObject(response);
            if (!object.getString("data").equals("") && object.getString("data") != null) {
                JSONArray jsonArray = object.getJSONArray("data");
                ArrayList<Chat.ChatModel> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    Chat.ChatModel chatModel = new Chat.ChatModel();

                    if (object1.has("chat_group") && !object1.isNull("chat_group")) {
                        JSONObject jsonObject=object1.getJSONObject("chat_group");
                        chatModel.setGroupId(jsonObject.getString("id"));
                        chatModel.setGroupName(jsonObject.getString("group_name"));
                        chatModel.setGroupImg(jsonObject.getString("group_image"));
                        chatModel.setFileType("group");
                    } else {
                        JSONObject jsonObject=object1.getJSONObject("sender_user");
                        chatModel.setReceiverId(jsonObject.getString("id"));
                        chatModel.setReceiverName(jsonObject.getString("username"));
                        chatModel.setReceiverProfileImage(jsonObject.getString("profile_image"));
                        chatModel.setFileType("single");
                    }

                    arrayList.add(chatModel);
                }

                recyclerView.setAdapter(new ChatAdapter(mContext, arrayList));
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onLongItemClick(@Nullable View view, int position) {
                    }

                    @Override
                    public void onItemClick(@NotNull View view, int position) {

                        if (arrayList.get(position).getFileType().equals("single"))
                            startActivity(
                                    new Intent(mContext, ChatDetailsActivity.class)
                                            .putExtra("receiver_id", arrayList.get(position).getReceiverId())
                                            .putExtra("receiver_name", arrayList.get(position).getReceiverName())
                                            .putExtra("receiver_profile_image", arrayList.get(position).getReceiverProfileImage()));

                        startActivity(
                                new Intent(mContext, GroupChatDetailsActivity.class)
                                        .putExtra("groupId", arrayList.get(position).getGroupId())
                                        .putExtra("groupName", arrayList.get(position).getGroupName())
                                        .putExtra("groupImg", arrayList.get(position).getGroupImg()));
                    }
                }));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public void getRequest() {
//        if (!isNetworkConnected())
//            return;
//
////        ChatDetailRequest chatDetailRequest = new ChatDetailRequest();
////        chatDetailRequest.setReceiver_id(receiverId);
////        chatDetailRequest.setSender_id(senderId);
//
//        showProgressDialog();
//        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
//        Call<CommonResponse> call = apiInterface.getRequest(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID));
//        call.enqueue(new Callback<CommonResponse>() {
//            @Override
//            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
//                hideProgressDialog();
//                Log.e("response code -->", "" + response.code());
//                if (response.isSuccessful()) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CommonResponse> call, Throwable t) {
//                hideProgressDialog();
//                showToast(t.getMessage());
//            }
//        });
//    }

}