package unilife.com.unilife.chat.update;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.adapter.FriendDetailsAdapter;
import unilife.com.unilife.chat.groupdetails.ViewSavedMultimedia;
import unilife.com.unilife.chat.model.ItemFriendDetails;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse2;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retro.WebUrls;
import unilife.com.unilife.updated.BaseActivity;
import unilife.com.unilife.utils.RecyclerItemClickListener;

public class FriendDetailActivity extends BaseActivity {

    String[] title = {
            "Media, Links, & Docs",
            "Mentions",
            "Starred Messages",
            "Apps and Integration",
            "",
            "Do not Disturb",
            "Delete Conversation",
            "Block"};
    int[] image = {
            R.drawable.fdetails_1,
            R.drawable.fdetails_2,
            R.drawable.fdetails_3,
            R.drawable.fdetails_4,
            0,
            R.drawable.fdetails_5,
            R.drawable.fdetails_6,
            R.drawable.fdetails_7};
    String[] color = {
            "#15be85",
            "#e53030",
            "#e2a731",
            "#006cb5",
            "",
            "#e818c5",
            "#db7b28",
            "#f24545"};

    ArrayList<ItemFriendDetails> arrayList = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.textName)
    TextView textName;
    private String receiverId = "";
    private String senderId = "";
    private String receiver_name = "";
    private String receiver_profile_image = "";
    private String roomId = "";

    private void showBlockDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set icon
//                .setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle("Confirmation")
                //set message
                .setMessage("Are you sure to block this user?")
                //set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //set what would happen when positive button is clicked
                        blockUserService();
                    }
                })
                //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void deleteChatDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set icon
//                .setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle("Confirmation")
                //set message
                .setMessage("Are you sure to delete this chat?")
                //set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //set what would happen when positive button is clicked
                        deleteChatApi();
                    }
                })
                //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void deleteChatApi() {
        if (!isNetworkConnected())
            return;

        BlockRequest chatDetailRequest = new BlockRequest();
        chatDetailRequest.setUser_id(senderId);
        chatDetailRequest.setChat_room_id(roomId);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<CommonResponse2> call = apiInterface.deleteChat(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), chatDetailRequest);
        call.enqueue(new Callback<CommonResponse2>() {
            @Override
            public void onResponse(Call<CommonResponse2> call, Response<CommonResponse2> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    showToast(response.body().getData());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse2> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        for (int i = 0; i < title.length; i++) {
            ItemFriendDetails itemFriendDetails = new ItemFriendDetails();
            itemFriendDetails.setTitle(title[i]);
            itemFriendDetails.setColor(color[i]);
            itemFriendDetails.setImage(image[i]);
            arrayList.add(itemFriendDetails);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new FriendDetailsAdapter(mContext, arrayList));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onLongItemClick(@Nullable View view, int position) {
            }

            @Override
            public void onItemClick(@NotNull View view, int position) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(mContext, ViewSavedMultimedia.class);
                        intent.putExtra("receiverId", receiverId);
                        startActivity(intent);
                        break;
                    case 3:
                        showToast("Coming soon...");
                        break;
                    case 6:
                        deleteChatDialog();
                        break;
                    case 7:
                        showBlockDialog();
                        break;
                }
            }
        }));
        init();
    }

    private void init() {
        senderId = PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID);
        receiverId = getIntent().getStringExtra("receiver_id");
        roomId = getIntent().getStringExtra("roomId");
        receiver_name = getIntent().getStringExtra("receiver_name");
        receiver_profile_image = getIntent().getStringExtra("receiver_profile_image");

        textName.setText(receiver_name);
        Glide.with(mContext)
                .load(WebUrls.getInstance().getPROFILE_IMAGE_URL() + receiver_profile_image)
                .apply(defultImg)
                .into(imgProfile);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_friend_detials;
    }

    public void goBack(View view) {
        finish();
    }

    public void blockUserService() {
        if (!isNetworkConnected())
            return;

        BlockRequest chatDetailRequest = new BlockRequest();
        chatDetailRequest.setUser_id(senderId);
        chatDetailRequest.setBlock_id(receiverId);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<CommonResponse2> call = apiInterface.blockUser(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), chatDetailRequest);
        call.enqueue(new Callback<CommonResponse2>() {
            @Override
            public void onResponse(Call<CommonResponse2> call, Response<CommonResponse2> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    showToast(response.body().getData());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse2> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public class BlockRequest {
        String user_id;
        String block_id;

        public void setChat_room_id(String chat_room_id) {
            this.chat_room_id = chat_room_id;
        }

        String chat_room_id;

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setBlock_id(String block_id) {
            this.block_id = block_id;
        }
    }


}