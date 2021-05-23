package unilife.com.unilife.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import unilife.com.unilife.R;
import unilife.com.unilife.home.FullViewMedia;
import unilife.com.unilife.home.Home;
import unilife.com.unilife.home.responses.Datum;
import unilife.com.unilife.updated.CommentsActivity;

public class HomeAdapter2 extends RecyclerView.Adapter<HomeAdapter2.MyViewHolder> {
    ArrayList<Datum> arrayList = new ArrayList<Datum>();
    Context context;
    RequestOptions defultImg = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .priority(Priority.IMMEDIATE);

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeAdapter2(Context context) {
        this.context = context;
    }

    public void updateData(ArrayList<Datum> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_home2, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        vh.setIsRecyclable(false);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.constraintImage.setVisibility(View.GONE);
        holder.constraintEvent.setVisibility(View.GONE);
        holder.constraintPoll.setVisibility(View.GONE);

        holder.txtLikeCount.setText(arrayList.get(position).getPostLikeCount().toString());
        holder.txtCommentCount.setText(arrayList.get(position).getPostCommentsCount().toString());

        holder.txtLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) context).likeUnlikePost(arrayList.get(position).getId());
            }
        });

        holder.txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String shareMessage = "";

                    if (arrayList.get(position).getType().equals("poll")) {
                        shareMessage = arrayList.get(position).getQuestion();
                    } else if (arrayList.get(position).getType().equals("event")) {
                        shareMessage = arrayList.get(position).getEventTitle() + "\n" + arrayList.get(position).getEventDescription();
                    } else if (arrayList.get(position).getType().equals("normal")) {
                        shareMessage = arrayList.get(position).getCaption();

                        if (arrayList.get(position).getPostAttachments() != null && arrayList.get(position).getPostAttachments().size() > 0) {
                            shareMessage = shareMessage + "\n" +
                                    arrayList.get(position).getPostAttachments().get(0).getAttachment();
                        }
                    } else if (arrayList.get(position).getType().equals("opinion")) {
                        shareMessage = arrayList.get(position).getCaption() + "\n" +
                                arrayList.get(position).getPostAttachments().get(0).getAttachment();

                        if (arrayList.get(position).getPostAttachments() != null && arrayList.get(position).getPostAttachments().size() > 0) {
                            shareMessage = shareMessage + "\n" +
                                    arrayList.get(position).getPostAttachments().get(0).getAttachment();
                        }
                    }
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Unilife");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.txtTime.setText(arrayList.get(position).getCreatedAt());
        if (arrayList.get(position).getAdminId().equals("0")) {
            holder.txtName.setText(arrayList.get(position).getUserUploadingPost().get(0).getUsername());
            Glide.with(context)
                    .load(arrayList.get(position).getUserUploadingPost().get(0).getProfileImage())
                    .apply(defultImg)
                    .into(holder.imgProfile);
        }

        holder.txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!arrayList.get(position).getType().equals("poll")) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("item", arrayList.get(position));
                if (arrayList.get(position).getAdminId().equals("0")) {
                    intent.putExtra("userItem", arrayList.get(position).getUserUploadingPost().get(0));
                }
                if (arrayList.get(position).getPostAttachments() != null && arrayList.get(position).getPostAttachments().size() > 0) {
                    intent.putExtra("postAttachment", arrayList.get(position).getPostAttachments().get(0));
                }
                context.startActivity(intent);
//                }
            }
        });

        if (arrayList.get(position).getIsLike()) {
            holder.txtLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fauroute_red, 0, 0, 0);
        } else {
            holder.txtLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_border_black_24dp, 0, 0, 0);
        }

        holder.imgOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home) context).showPopup(arrayList.get(position).getId(), arrayList.get(position).getUserId(), view);
            }
        });

        //==========================================================================================

        if (arrayList.get(position).getType().equals("poll")) {
            holder.constraintPoll.setVisibility(View.VISIBLE);
            holder.txtDetailsPoll.setText(arrayList.get(position).getQuestion());

            holder.recycvlerPoll.setLayoutManager(new GridLayoutManager(context, 2));
            holder.recycvlerPoll.setHasFixedSize(true);
            holder.recycvlerPoll.setAdapter(new PollAdapter(context, arrayList.get(position).getPostOptions()));

        } else if (arrayList.get(position).getType().equals("event")) {
            holder.constraintEvent.setVisibility(View.VISIBLE);
            if (arrayList.get(position).getPostAttachments() != null && arrayList.get(position).getPostAttachments().size() > 0) {
                Glide.with(context)
                        .load(arrayList.get(position).getPostAttachments().get(0).getAttachment())
                        .apply(defultImg)
                        .into(holder.imgPostEvnt);

                if (arrayList.get(position).getPostAttachments().get(0).getAttachmentType().equals("video")) {
                    holder.playVideo.setVisibility(View.VISIBLE);
                } else {
                    holder.playVideo.setVisibility(View.GONE);
                }
            }
            holder.txtEventTitle.setText(arrayList.get(position).getEventTitle());
            holder.txtDetailsEvnt.setText(arrayList.get(position).getEventDescription());
            holder.btnRegisterEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (arrayList.get(position).getAlready_hit_button().equalsIgnoreCase("no")) {
                        ((Home) context).eventCounter(arrayList.get(position).getId());
                    }

                    if (arrayList.get(position).getEventLink().contains("http")) {
                        Uri uri = Uri.parse(arrayList.get(position).getEventLink()); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                }
            });

            holder.imgPostEvnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FullViewMedia.class);
                    intent.putExtra("data", arrayList.get(position).getPostAttachments().get(0).getAttachment());
                    intent.putExtra("datatype", arrayList.get(position).getPostAttachments().get(0).getAttachmentType());
                    context.startActivity(intent);
                }
            });

            holder.evCount.setVisibility(View.VISIBLE);
            holder.txtEventCount.setVisibility(View.VISIBLE);
            holder.txtEventCount.setText(arrayList.get(position).getEvent_register_count());

        } else if (arrayList.get(position).getType().equals("normal")) {
            holder.constraintImage.setVisibility(View.VISIBLE);
            holder.txtOpinion.setVisibility(View.GONE);
            holder.txtDetails.setText(arrayList.get(position).getCaption());

            if (arrayList.get(position).getPostAttachments() != null && arrayList.get(position).getPostAttachments().size() > 0) {
                holder.imgPost.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(arrayList.get(position).getPostAttachments().get(0).getAttachment())
                        .apply(defultImg)
                        .into(holder.imgPost);

                if (arrayList.get(position).getPostAttachments().get(0).getAttachmentType().equals("video")) {
                    holder.playVideo.setVisibility(View.VISIBLE);
                } else {
                    holder.playVideo.setVisibility(View.GONE);
                }
            } else {
                holder.imgPost.setVisibility(View.GONE);
            }

            holder.imgPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FullViewMedia.class);
                    intent.putExtra("data", arrayList.get(position).getPostAttachments().get(0).getAttachment());
                    intent.putExtra("datatype", arrayList.get(position).getPostAttachments().get(0).getAttachmentType());
                    context.startActivity(intent);
                }
            });

        } else if (arrayList.get(position).getType().equals("opinion")) {
            holder.constraintImage.setVisibility(View.VISIBLE);
            holder.txtOpinion.setVisibility(View.VISIBLE);
            holder.txtDetails.setVisibility(View.GONE);
            holder.txtOpinion.setText(arrayList.get(position).getCaption());

            if (arrayList.get(position).getPostAttachments() != null && arrayList.get(position).getPostAttachments().size() > 0) {
                holder.imgPost.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(arrayList.get(position).getPostAttachments().get(0).getAttachment())
                        .apply(defultImg)
                        .into(holder.imgPost);

                if (arrayList.get(position).getPostAttachments().get(0).getAttachmentType().equals("video")) {
                    holder.playVideo.setVisibility(View.VISIBLE);
                } else {
                    holder.playVideo.setVisibility(View.GONE);
                }
            } else {
                holder.imgPost.setVisibility(View.GONE);
            }

            holder.imgPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FullViewMedia.class);
                    intent.putExtra("data", arrayList.get(position).getPostAttachments().get(0).getAttachment());
                    intent.putExtra("datatype", arrayList.get(position).getPostAttachments().get(0).getAttachmentType());
                    context.startActivity(intent);
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

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
        @BindView(R.id.txtShare)
        public TextView txtShare;
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

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}