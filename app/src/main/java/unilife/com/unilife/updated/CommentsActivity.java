package unilife.com.unilife.updated;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.home.FullViewMedia;
import unilife.com.unilife.home.Home;
import unilife.com.unilife.home.adapter.CommentPollAdapter;
import unilife.com.unilife.home.requests.SelectPollRequest;
import unilife.com.unilife.home.responses.Datum;
import unilife.com.unilife.home.responses.LikeUnlikeResponse;
import unilife.com.unilife.home.responses.PostAttachment;
import unilife.com.unilife.home.responses.UserUploadingPost;
import unilife.com.unilife.home.responses.comment.CommentResponse;
import unilife.com.unilife.profile.model.responses.CommonResponse;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retrofit.WebUrls;
import unilife.com.unilife.updated.adapters.CommentsAdapter;

public class CommentsActivity extends BaseActivity {

    //====================== Post ===========================
    @BindView(R.id.imgProfile)
    public CircleImageView imgProfile;
    @BindView(R.id.txtName)
    public TextView txtName;
    @BindView(R.id.txtTime)
    public TextView txtTime;
    @BindView(R.id.imgOptions)
    public ImageView imgOptions;
    @BindView(R.id.txtOpinion)
    public TextView txtOpinion;
    //========================================================
    @BindView(R.id.txtDetails)
    public TextView txtDetails;
    @BindView(R.id.imgPost)
    public ImageView imgPost;
    @BindView(R.id.playVideo)
    public ImageView playVideo;
    @BindView(R.id.txtLikeCount)
    public TextView txtLikeCount;
    @BindView(R.id.txtCommentCount)
    public TextView txtCommentCount;
    @BindView(R.id.txtLike)
    public TextView txtLike;
    @BindView(R.id.txtComment)
    public TextView txtComment;
    @BindView(R.id.txtEventCount)
    public TextView txtEventCount;
    @BindView(R.id.txtShare)
    public TextView txtShare;
    //====================== Event ===========================
    @BindView(R.id.txtEventTitle)
    public TextView txtEventTitle;
    @BindView(R.id.txtDetailsEvnt)
    public TextView txtDetailsEvnt;
    @BindView(R.id.imgPostEvnt)
    public ImageView imgPostEvnt;
    @BindView(R.id.evCount)
    public ImageView evCount;
    @BindView(R.id.btnRegisterEvent)
    public Button btnRegisterEvent;
    //====================== Poll ===========================
    @BindView(R.id.txtDetailsPoll)
    public TextView txtDetailsPoll;
    @BindView(R.id.recycvlerPoll)
    public RecyclerView recycvlerPoll;
    //====================== Media ===========================
    @BindView(R.id.constraintImage)
    ConstraintLayout constraintImage;
    @BindView(R.id.constraintEvent)
    ConstraintLayout constraintEvent;
    @BindView(R.id.constraintPoll)
    ConstraintLayout constraintPoll;
    @BindView(R.id.recycvlerView)
    RecyclerView recyclerView;
    @BindView(R.id.edtMessage)
    EditText edtMessage;

    CommentsAdapter commentsAdapter;
    private Datum datum;
    private PostAttachment postAttachment;
    private UserUploadingPost uploadingPost;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        datum = getIntent().getParcelableExtra("item");
        uploadingPost = getIntent().getParcelableExtra("userItem");
        postAttachment = getIntent().getParcelableExtra("postAttachment");
        txtName.setText(PreferenceFile.getInstance().getPreferenceData(mContext, KEY_USERNAME));
        if (uploadingPost == null) {
            uploadingPost.setCreatedAt(datum.getCreatedAt());
            uploadingPost.setProfileImage("");
            uploadingPost.setUsername("Admin");
        }
        try {
            Glide
                    .with(mContext)
                    .load(WebUrls.INSTANCE.getPROFILE_IMAGE_URL() + PreferenceFile.getInstance().getPreferenceData(this, KEY_PROFILE_IMAGE))
                    .apply(options)
                    .into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }


        commentsAdapter = new CommentsAdapter(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(commentsAdapter);

        edtMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0)
                    findViewById(R.id.imgSend).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.imgSend).setVisibility(View.GONE);
            }
        });

        getComments();
        init();
    }

    private void init() {
        constraintImage.setVisibility(View.GONE);
        constraintEvent.setVisibility(View.GONE);
        constraintPoll.setVisibility(View.GONE);

        txtLikeCount.setText(datum.getPostLikeCount().toString());
        txtCommentCount.setText(datum.getPostCommentsCount().toString());

        txtLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeUnlikePost(datum.getId());
            }
        });

        txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String shareMessage = "";

                    if (datum.getType().equals("poll")) {
                        shareMessage = datum.getQuestion();
                    } else if (datum.getType().equals("event")) {
                        shareMessage = datum.getEventTitle() + "\n" + datum.getEventDescription();
                    } else if (datum.getType().equals("normal")) {
                        shareMessage = datum.getCaption();

                        if (datum.getPostAttachments() != null && datum.getPostAttachments().size() > 0) {
                            shareMessage = shareMessage + "\n" +
                                    datum.getPostAttachments().get(0).getAttachment();
                        }
                    } else if (datum.getType().equals("opinion")) {
                        shareMessage = datum.getCaption() + "\n" +
                                datum.getPostAttachments().get(0).getAttachment();

                        if (datum.getPostAttachments() != null && datum.getPostAttachments().size() > 0) {
                            shareMessage = shareMessage + "\n" +
                                    datum.getPostAttachments().get(0).getAttachment();
                        }
                    }
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Unilife");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        txtTime.setText(datum.getCreatedAt());
//        if (datum.getAdminId().equals("0")) {
//            txtName.setText(datum.getUserUploadingPost().get(0).getUsername());
        Glide.with(mContext)
                .load(datum.getUserUploadingPost().get(0).getProfileImage())
                .apply(defultImg)
                .into(imgProfile);
//        }

//        txtComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!datum.getType().equals("poll")) {
//                    Intent intent = new Intent(mContext, CommentsActivity.class);
//                    intent.putExtra("item", arrayList.get(position));
//                    if (datum.getAdminId().equals("0")) {
//                        intent.putExtra("userItem", datum.getUserUploadingPost().get(0));
//                    }
//                    if (datum.getPostAttachments() != null && datum.getPostAttachments().size() > 0) {
//                        intent.putExtra("postAttachment", datum.getPostAttachments().get(0));
//                    }
//                    mContext.startActivity(intent);
//                }
//            }
//        });


        if (datum.getIsLike()) {
            txtLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fauroute_red, 0, 0, 0);
        } else {
            txtLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_border_black_24dp, 0, 0, 0);
        }

//        imgOptions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((Home) mContext).showPopup(datum.getId(),datum.getUserId(), view);
//            }
//        });

        //==========================================================================================

        if (datum.getType().equals("poll")) {
            constraintPoll.setVisibility(View.VISIBLE);
            txtDetailsPoll.setText(datum.getQuestion());

            recycvlerPoll.setLayoutManager(new GridLayoutManager(mContext, 2));
            recycvlerPoll.setHasFixedSize(true);
            recycvlerPoll.setAdapter(new CommentPollAdapter(mContext, datum.getPostOptions()));

        } else if (datum.getType().equals("event")) {
            constraintEvent.setVisibility(View.VISIBLE);
            if (datum.getPostAttachments() != null && datum.getPostAttachments().size() > 0) {
                Glide.with(mContext)
                        .load(datum.getPostAttachments().get(0).getAttachment())
                        .apply(defultImg)
                        .into(imgPostEvnt);

                if (datum.getPostAttachments().get(0).getAttachmentType().equals("video")) {
                    playVideo.setVisibility(View.VISIBLE);
                } else {
                    playVideo.setVisibility(View.GONE);
                }
            }
            txtEventTitle.setText(datum.getEventTitle());
            txtDetailsEvnt.setText(datum.getEventDescription());
            btnRegisterEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (datum.getAlready_hit_button().equalsIgnoreCase("no")) {
                        eventCounter(datum.getId());
                    }

                    if (datum.getEventLink().contains("http")) {
                        Uri uri = Uri.parse(datum.getEventLink()); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        mContext.startActivity(intent);
                    }
                }
            });

            imgPostEvnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FullViewMedia.class);
                    intent.putExtra("data", datum.getPostAttachments().get(0).getAttachment());
                    intent.putExtra("datatype", datum.getPostAttachments().get(0).getAttachmentType());
                    mContext.startActivity(intent);
                }
            });

            evCount.setVisibility(View.VISIBLE);
            txtEventCount.setVisibility(View.VISIBLE);
            txtEventCount.setText(datum.getEvent_register_count());

        } else if (datum.getType().equals("normal")) {
            constraintImage.setVisibility(View.VISIBLE);
            txtOpinion.setVisibility(View.GONE);
            txtDetails.setText(datum.getCaption());

            if (datum.getPostAttachments() != null && datum.getPostAttachments().size() > 0) {
                imgPost.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(datum.getPostAttachments().get(0).getAttachment())
                        .apply(defultImg)
                        .into(imgPost);

                if (datum.getPostAttachments().get(0).getAttachmentType().equals("video")) {
                    playVideo.setVisibility(View.VISIBLE);
                } else {
                    playVideo.setVisibility(View.GONE);
                }
            } else {
                imgPost.setVisibility(View.GONE);
            }

            imgPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FullViewMedia.class);
                    intent.putExtra("data", datum.getPostAttachments().get(0).getAttachment());
                    intent.putExtra("datatype", datum.getPostAttachments().get(0).getAttachmentType());
                    mContext.startActivity(intent);
                }
            });

        } else if (datum.getType().equals("opinion")) {
            constraintImage.setVisibility(View.VISIBLE);
            txtOpinion.setVisibility(View.VISIBLE);
            txtDetails.setVisibility(View.GONE);
            txtOpinion.setText(datum.getCaption());

            if (datum.getPostAttachments() != null && datum.getPostAttachments().size() > 0) {
                imgPost.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(datum.getPostAttachments().get(0).getAttachment())
                        .apply(defultImg)
                        .into(imgPost);

                if (datum.getPostAttachments().get(0).getAttachmentType().equals("video")) {
                    playVideo.setVisibility(View.VISIBLE);
                } else {
                    playVideo.setVisibility(View.GONE);
                }
            } else {
                imgPost.setVisibility(View.GONE);
            }


            imgPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FullViewMedia.class);
                    intent.putExtra("data", datum.getPostAttachments().get(0).getAttachment());
                    intent.putExtra("datatype", datum.getPostAttachments().get(0).getAttachmentType());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_comments;
    }

    public void closeView(View view) {
        finish();
    }

    public void setComment(View view) {
        postComment(edtMessage.getText().toString().trim());
    }

    public void getComments() {
        if (!isNetworkConnected())
            return;

        GetCommentRequest commentRequest = new GetCommentRequest();
        commentRequest.setPost_id(datum.getId());

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommentResponse> call = apiInterface.getPostComment(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), commentRequest);

        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful() && response.body().getStatus()) {
                    commentsAdapter.updateData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                hideProgressDialog();
//                showToast(t.getMessage());
            }
        });
    }

    public void postComment(String comment) {
        if (!isNetworkConnected())
            return;

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setPost_id(datum.getId());
        commentRequest.setUser_id(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID));
        commentRequest.setComment(comment);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<CommentResponse> call = apiInterface.addComment(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), commentRequest);

        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                edtMessage.setText("");

                if (response.isSuccessful()) {
                    getComments();
                }
                showToast(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public final void selectPoll(@Nullable String option, int pos) {

        if (!isNetworkConnected())
            return;

        SelectPollRequest selectPollRequest = new SelectPollRequest();
        selectPollRequest.setSelect_poll_option(option);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.selectPollOption(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), selectPollRequest);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful()) {
                    datum.getPostOptions().get(pos).setSelect(true);
                    init();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public final void likeUnlikePost(@Nullable String id) {

        if (!isNetworkConnected())
            return;
        Home.LikeRequest likeRequest = new Home.LikeRequest();
        likeRequest.setUser_id(PreferenceFile.getInstance().getPreferenceData((Context) this, WebUrls.getKEY_USERID()));
        likeRequest.setPost_id(id);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<LikeUnlikeResponse> call = apiInterface.likePost(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), likeRequest);

        call.enqueue(new Callback<LikeUnlikeResponse>() {
            @Override
            public void onResponse(Call<LikeUnlikeResponse> call, Response<LikeUnlikeResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    if (datum.getIsLike())
                        datum.setIsLike(false);
                    else
                        datum.setIsLike(true);
                }
                showToast(response.body().getMessage());
                init();

            }

            @Override
            public void onFailure(Call<LikeUnlikeResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public final void eventCounter(@Nullable String id) {
        if (!isNetworkConnected())
            return;
        Home.EventCountRegister eventCountRegister = new Home.EventCountRegister();
        eventCountRegister.setEventId(id);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.registerEvent(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), eventCountRegister);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());
                if (response.isSuccessful()) {
                    datum.setEvent_register_count(Integer.parseInt(datum.getEvent_register_count() + 1) + "");
                    init();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    public class GetCommentRequest {
        String post_id;

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }
    }

    public class CommentRequest {
        String user_id;
        String post_id;
        String comment;

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
