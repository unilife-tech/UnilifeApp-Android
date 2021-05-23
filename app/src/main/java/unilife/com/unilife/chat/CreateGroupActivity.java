package unilife.com.unilife.chat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.adapter.FriendListingAdapter;
import unilife.com.unilife.chat.model.FriendsResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.profile.model.responses.UploadImageResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.updated.BaseActivity;

public class CreateGroupActivity extends BaseActivity {
    String strImage = "";
    Uri mImageUri;

    @BindView(R.id.ivGroupImg)
    CircleImageView imgProfile;
    @BindView(R.id.edtGroupName)
    EditText edtGroupName;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.rrecyclerFriendList)
    RecyclerView recyclerFriendList;
    ArrayList<FriendsResponse.Datum> membersList = new ArrayList<FriendsResponse.Datum>();
    FriendListingAdapter friendListingAdapter;
    private byte[] imageBytes = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        recyclerFriendList.setHasFixedSize(true);
        recyclerFriendList.setLayoutManager(new LinearLayoutManager(mContext));
        init();
        getFriendsList();
    }

    private void init() {
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(CreateGroupActivity.this);
            }
        });

//        edtSearch.addTextChangedListener(new TextWatcher() {
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
//                ArrayList<FriendsResponse.Datum> filteredList = new ArrayList<FriendsResponse.Datum>();
//                if (editable.toString().trim().length() > 0) {
//                    for (int i = 0; i < membersList.size(); i++) {
//                        if (membersList.get(i).getUsername().toLowerCase().contains(editable.toString().toLowerCase().trim())) {
//                            filteredList.add(membersList.get(i));
//                        }
//                    }
//                    friendListingAdapter.updateData(filteredList);
//                } else {
//                    friendListingAdapter.updateData(membersList);
//                }
//            }
//        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_create_group2;
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == Activity.RESULT_OK) {
                mImageUri = result.getUri();
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(mImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    imageBytes = getBytes(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (imageBytes != null) {
                    uploadImage();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception e = result.getError();
                Toast.makeText(mContext, "Possible error is: " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }

    public void uploadImage() {
        if (!isNetworkConnected())
            return;

        MultipartBody.Part image = null;
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        image = MultipartBody.Part.createFormData("club_image", "image.jpg", requestFile);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UploadImageResponse> call = apiInterface.uploadImage(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERID), image);

        showProgressDialog();
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.body());
                strImage = response.body().getData();
                imgProfile.setImageURI(mImageUri);
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void createGroupCall(View view) {
        if (edtGroupName.getText().toString().trim().equals("")) {
            edtGroupName.setError("Please enter group name");
            return;
        }

        String ids = "";
        boolean isSelect = false;
        membersList = friendListingAdapter.arrayList;
        for (int i = 0; i < membersList.size(); i++) {
            if (membersList.get(i).isSelected()) {
                isSelect = true;
                ids = ids + "," + membersList.get(i).getId().toString();
            }
        }

        if (!isSelect) {
            showToast("Add at least one friend");
            return;
        }

        if (ids.length() > 1)
            ids = ids.substring(1);

        if (!isNetworkConnected())
            return;

        CreateGroupRequest createGroupRequest = new CreateGroupRequest();
        createGroupRequest.setGroup_image(strImage);
        createGroupRequest.setFriend_id(ids);
        createGroupRequest.setGroup_name(edtGroupName.getText().toString().trim());

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.createGroup(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), createGroupRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override

            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    showToast(response.body().getMessage());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public void getFriendsList() {
        if (!isNetworkConnected())
            return;

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setUser_id(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID));
        friendRequest.setGroup_id("");

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<FriendsResponse> call = apiInterface.showFriendList(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), friendRequest);

        call.enqueue(new Callback<FriendsResponse>() {
            @Override

            public void onResponse(Call<FriendsResponse> call, Response<FriendsResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        friendListingAdapter = new FriendListingAdapter(mContext, response.body().getData());
                        recyclerFriendList.setAdapter(friendListingAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<FriendsResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public class CreateGroupRequest {
        String group_name;
        String group_image;
        String friend_id;

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public void setGroup_image(String group_image) {
            this.group_image = group_image;
        }

        public void setFriend_id(String friend_id) {
            this.friend_id = friend_id;
        }
    }

    public class FriendRequest {
        String user_id;
        String group_id;

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }
    }
}