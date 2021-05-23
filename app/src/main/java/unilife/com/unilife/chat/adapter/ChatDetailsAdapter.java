package unilife.com.unilife.chat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ankushgrover.hourglass.Hourglass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.FullMediaScreen;
import unilife.com.unilife.chat.update.ChatDetailsActivity;
import unilife.com.unilife.chat.update.chatresponse.Datum;
import unilife.com.unilife.chat.update.chatresponse.UserDetail;

public class ChatDetailsAdapter extends RecyclerView.Adapter<ChatDetailsAdapter.MyViewHolder> {

    public ArrayList<Datum> arrayList = new ArrayList<>();
    public String CHAT_MEDIA_URL = "http://15.206.103.14/public/chatdata/";
    int prevPos = -1;
    Hourglass hourglass;
    private MediaPlayer mediaPlayer = null;
    private int time;
    private Context mContext;
    private String PROFILE_IMAGE_URL = "http://15.206.103.14/public/profile_imgs/";
    private RequestOptions defultImg = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH);
    private ArrayList<UserDetail> userDetail = new ArrayList<>();

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatDetailsAdapter(Context context) {
        this.mContext = context;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
                prevPos = -1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateData(ArrayList<Datum> arrayList, ArrayList<UserDetail> userDetail) {
        this.arrayList = arrayList;
        this.userDetail = userDetail;
        notifyDataSetChanged();
    }

    public void addNewData(Datum chatDatum, RecyclerView recyclerView) {
        this.arrayList.add(chatDatum);
//        this.userDetail = userDetail;
        ((ChatDetailsActivity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Stuff that updates the UI
                notifyItemRangeInserted(arrayList.size() - 1, getItemCount());
                recyclerView.scrollToPosition(arrayList.size() - 1);
            }
        });
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_chat_details, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.imgVideoOG.setVisibility(View.GONE);
        holder.imgVideo.setVisibility(View.GONE);

        if (position == 0) {
            holder.releDate.setVisibility(View.VISIBLE);
            holder.txtDate.setText(arrayList.get(position).getOnlyDate());
        } else if ((!arrayList.get(position).getOnlyDate().equals(arrayList.get(position - 1).getOnlyDate()))) {
            holder.releDate.setVisibility(View.VISIBLE);
            holder.txtDate.setText(arrayList.get(position).getOnlyDate());
//            ConstraintLayout.LayoutParams buttonLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            buttonLayoutParams.setMargins(0, 16, 0, 0);
//            holder.txtDate.setLayoutParams(buttonLayoutParams);
        } else {
            holder.releDate.setVisibility(View.GONE);
        }

        if (((ChatDetailsActivity) mContext).senderId.equals(arrayList.get(position).getSenderId())) {
            //================= OUTGOING MESSAGE ====================
            holder.constraintIncoming.setVisibility(View.GONE);
            holder.constraintOutgoing.setVisibility(View.VISIBLE);
            holder.txtTimeOG.setText(getConvertedTime(arrayList.get(position).getCreatedAt()));
            holder.txtSenderNameOG.setText(((ChatDetailsActivity) mContext).senderName);
            if (arrayList.get(position).getChatId() != null) {
                holder.clReplayOG.setVisibility(View.VISIBLE);
                checkReplayValOG(holder, arrayList.get(position).getChatId());
            } else {
                holder.clReplayOG.setVisibility(View.GONE);
            }

            if (arrayList.get(position).getMessageType().equals("text")) {
                holder.txtMessageOG.setVisibility(View.VISIBLE);
                holder.imgChatsOG.setVisibility(View.GONE);
                holder.constraintAudioOG.setVisibility(View.GONE);
                String decode = "";
                try {
                    decode = URLDecoder.decode(arrayList.get(position).getMessage(), "UTF-8");
                    Log.e("decode", arrayList.get(position).getMessage());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                holder.txtMessageOG.setText(decode);

            } else if (arrayList.get(position).getMessageType().equals("image") || arrayList.get(position).getMessageType().equals("gif")) {
                holder.txtMessageOG.setVisibility(View.GONE);
                holder.imgChatsOG.setVisibility(View.VISIBLE);
                holder.constraintAudioOG.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(CHAT_MEDIA_URL + arrayList.get(position).getMessage())
                        .apply(defultImg)
                        .into(holder.imgChatsOG);

            } else if (arrayList.get(position).getMessageType().equals("document")) {
                holder.txtMessageOG.setVisibility(View.GONE);
                holder.imgChatsOG.setVisibility(View.VISIBLE);
                holder.constraintAudioOG.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(R.drawable.doc_sender)
                        .apply(defultImg)
                        .into(holder.imgChatsOG);
            } else if (arrayList.get(position).getMessageType().equals("video")) {
                holder.imgVideoOG.setVisibility(View.VISIBLE);
                holder.txtMessageOG.setVisibility(View.GONE);
                holder.imgChatsOG.setVisibility(View.VISIBLE);
                holder.constraintAudioOG.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(CHAT_MEDIA_URL + arrayList.get(position).getThumb())
                        .apply(defultImg)
                        .into(holder.imgChatsOG);
            } else if (arrayList.get(position).getMessageType().equals("audio")) {
                holder.txtMessageOG.setVisibility(View.GONE);
                holder.imgChatsOG.setVisibility(View.GONE);
                holder.constraintAudioOG.setVisibility(View.VISIBLE);

                Glide.with(mContext)
                        .load(PROFILE_IMAGE_URL + userDetail.get(1).getProfileImage())
                        .apply(defultImg)
                        .into(holder.imgProfileAudioOG);

                holder.playAudioOG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (position == prevPos) {
                            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                holder.playAudioOG.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                                mediaPlayer.pause();
                                if (hourglass != null) {
                                    hourglass.pauseTimer();
                                }

                            } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                                holder.playAudioOG.setImageResource(R.drawable.ic_baseline_pause_24);
                                mediaPlayer.start();
                                if (hourglass != null) {
                                    hourglass.resumeTimer();
                                }
                            }

                        } else {
                            if (mediaPlayer == null) {
                                mediaPlayer = MediaPlayer.create(mContext, Uri.parse(CHAT_MEDIA_URL + arrayList.get(position).getMessage()));
                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                    }
                                });
                                holder.playAudioOG.setImageResource(R.drawable.ic_baseline_pause_24);
                                mediaPlayer.start();
                                int duration = mediaPlayer.getDuration();
                                time = 0;
                                holder.seekbarOG.setMax(duration / 1000);
                                hourglass = new Hourglass(duration, 1000) {
                                    @Override
                                    public void onTimerTick(long timeRemaining) {
                                        time++;
                                        holder.seekbarOG.setProgress(time);
                                        holder.txtAudioDurationOG.setText(time / +60 + ":" + time % 60);
                                        // Update UI
                                    }

                                    @Override
                                    public void onTimerFinish() {
                                        time = 0;
                                        holder.txtAudioDurationOG.setText("-");
                                        holder.seekbarOG.setProgress(time);
                                        killMediaPlayer();
                                        holder.playAudioOG.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                                    }
                                };

                                hourglass.startTimer();
                            }
                        }

                        prevPos = position;
                    }
                });
            }

            holder.imgChatsOG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!arrayList.get(position).getMessageType().equals("text") && !arrayList.get(position).getMessageType().equals("audio")) {
                        Intent intent = new Intent(mContext, FullMediaScreen.class);
//                        intent.putExtra("file_type", "media");
                        intent.putExtra("file_type", arrayList.get(position).getMessageType());
                        intent.putExtra("message", arrayList.get(position).getMessage());
                        mContext.startActivity(intent);
                    }
                }
            });

        } else {
            //================= INCOMING MESSAGE ====================
            holder.constraintIncoming.setVisibility(View.VISIBLE);
            holder.constraintOutgoing.setVisibility(View.GONE);
//            holder.txtTime.setVisibility(View.GONE);
            holder.txtTime.setText(getConvertedTime(arrayList.get(position).getCreatedAt()));
            holder.txtTime2.setText(getConvertedTime(arrayList.get(position).getCreatedAt()));
            holder.txtSenderName.setText(((ChatDetailsActivity) mContext).receiver_name);
            if (arrayList.get(position).getChatId() != null) {
                holder.clReplay.setVisibility(View.VISIBLE);

//                =======================================================
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.clReplay);
                params.addRule(RelativeLayout.ALIGN_START, R.id.clReplay);
                params.addRule(RelativeLayout.ALIGN_START, R.id.txtSenderName);
                holder.rel_layout_left.removeView(holder.constraintMain);
                holder.rel_layout_left.addView(holder.constraintMain, params);
//                =======================================================
                checkReplayVal(holder, arrayList.get(position).getChatId());
            } else {
                holder.clReplay.setVisibility(View.GONE);
            }

            if (arrayList.get(position).getMessageType().equals("text")) {
                holder.txtMessage.setVisibility(View.VISIBLE);
                holder.imgChats.setVisibility(View.GONE);
                holder.constraintAudio.setVisibility(View.GONE);
                String decode = "";
                try {
                    decode = URLDecoder.decode(arrayList.get(position).getMessage(), "UTF-8");
                    Log.e("decode", arrayList.get(position).getMessage());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                holder.txtMessage.setText(decode);

                if (arrayList.get(position).getMessage().length() < 28) {
                    holder.txtTime.setVisibility(View.VISIBLE);
                    holder.txtTime2.setVisibility(View.GONE);
                }

            } else if (arrayList.get(position).getMessageType().equals("image") || arrayList.get(position).getMessageType().equals("gif")) {
                holder.txtMessage.setVisibility(View.GONE);
                holder.imgChats.setVisibility(View.VISIBLE);
                holder.constraintAudio.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(CHAT_MEDIA_URL + arrayList.get(position).getMessage())
                        .apply(defultImg)
                        .into(holder.imgChats);

            } else if (arrayList.get(position).getMessageType().equals("document")) {
                holder.txtMessage.setVisibility(View.GONE);
                holder.imgChats.setVisibility(View.VISIBLE);
                holder.constraintAudio.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(R.drawable.doc_sender)
                        .apply(defultImg)
                        .into(holder.imgChats);
            } else if (arrayList.get(position).getMessageType().equals("video")) {
                holder.imgVideo.setVisibility(View.VISIBLE);
                holder.txtMessage.setVisibility(View.GONE);
                holder.imgChats.setVisibility(View.VISIBLE);
                holder.constraintAudio.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(CHAT_MEDIA_URL + arrayList.get(position).getThumb())
                        .apply(defultImg)
                        .into(holder.imgChats);
            } else if (arrayList.get(position).getMessageType().equals("audio")) {
                holder.txtMessage.setVisibility(View.GONE);
                holder.imgChats.setVisibility(View.GONE);
                holder.constraintAudio.setVisibility(View.VISIBLE);

                Glide.with(mContext)
                        .load(PROFILE_IMAGE_URL + userDetail.get(1).getProfileImage())
                        .apply(defultImg)
                        .into(holder.imgProfileAudio);


                holder.playAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (position == prevPos) {
                            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                holder.playAudio.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                                mediaPlayer.pause();
                                if (hourglass != null) {
                                    hourglass.pauseTimer();
                                }

                            } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                                holder.playAudio.setImageResource(R.drawable.ic_baseline_pause_24);
                                mediaPlayer.start();
                                if (hourglass != null) {
                                    hourglass.resumeTimer();
                                }
                            }

                        } else {
                            if (mediaPlayer == null) {
                                mediaPlayer = MediaPlayer.create(mContext, Uri.parse(CHAT_MEDIA_URL + arrayList.get(position).getMessage()));
                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                    }
                                });
                                holder.playAudio.setImageResource(R.drawable.ic_baseline_pause_24);
                                mediaPlayer.start();
                                int duration = mediaPlayer.getDuration();
                                time = 0;
                                holder.seekbar.setMax(duration / 1000);
                                hourglass = new Hourglass(duration, 1000) {
                                    @Override
                                    public void onTimerTick(long timeRemaining) {
                                        time++;
                                        holder.seekbar.setProgress(time);
                                        holder.txtAudioDuration.setText(time / +60 + ":" + time % 60);
                                        // Update UI
                                    }

                                    @Override
                                    public void onTimerFinish() {
                                        time = 0;
                                        holder.txtAudioDuration.setText("-");
                                        holder.seekbar.setProgress(time);
                                        killMediaPlayer();
                                        holder.playAudio.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                                    }
                                };

                                hourglass.startTimer();
                            }
                        }

                        prevPos = position;
                    }
                });
            }
            holder.imgChats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!arrayList.get(position).getMessageType().equals("text") && !arrayList.get(position).getMessageType().equals("audio")) {
                        Intent intent = new Intent(mContext, FullMediaScreen.class);
                        intent.putExtra("file_type", "media");
                        intent.putExtra("message_type", arrayList.get(position).getMessageType());
                        intent.putExtra("message", arrayList.get(position).getMessage());
                        mContext.startActivity(intent);
                    }
                }
            });

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return arrayList.size();
        return arrayList.size();
    }

    private String getConvertedTime(String raw) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
        try {
            date = dateFormat.parse(raw);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formatter = new SimpleDateFormat("hh:mm a"); //If you need time just put specific format for time like 'HH:mm:ss'
        String dateStr = formatter.format(date);
        return dateStr;
    }

    //================================= Outgoing replay ============================================
    private void checkReplayValOG(MyViewHolder holder, String rplId) {
//        List<Article> articleList = new ArrayList<Article>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<Datum> filteredArticleList = arrayList.stream().filter(article -> article.getId().equals(rplId)).collect(Collectors.toList());
            if (filteredArticleList.size() > 0)
                setUpReplayOutGoing(holder, filteredArticleList.get(0));
        }
    }

    private void setUpReplayOutGoing(MyViewHolder holder, Datum chatDatum) {
//        holder.clReplay.setVisibility(View.VISIBLE);
//        if (((ChatDetailsActivity) mContext).senderId.equals(chatDatum.getSenderId())) {
        holder.txtSenderNameReplayOG.setText(((ChatDetailsActivity) mContext).senderName);
//        } else {
//            holder.txtSenderNameReplayOG.setText(((ChatDetailsActivity) mContext).receiver_name);
//        }

        if (chatDatum.getMessageType().equals("text")) {
            holder.txtMessageReplayOG.setText(chatDatum.getMessage());
            holder.imgReplayOG.setVisibility(View.GONE);
        } else {
            holder.txtMessageReplayOG.setText(chatDatum.getMessageType());
            holder.imgReplayOG.setVisibility(View.VISIBLE);

            if (chatDatum.getMessageType().equals("image") || chatDatum.getMessageType().equals("gif")) {
                Glide.with(mContext)
                        .load(CHAT_MEDIA_URL + chatDatum.getMessage())
                        .apply(defultImg)
                        .into(holder.imgReplayOG);

            } else if (chatDatum.getMessageType().equals("document")) {
                Glide.with(mContext)
                        .load(R.drawable.doc_sender)
                        .apply(defultImg)
                        .into(holder.imgReplayOG);

            } else if (chatDatum.getMessageType().equals("video")) {
                Glide.with(mContext)
                        .load(CHAT_MEDIA_URL + chatDatum.getThumb())
                        .apply(defultImg)
                        .into(holder.imgReplayOG);

            } else if (chatDatum.getMessageType().equals("audio")) {
                Glide.with(mContext)
                        .load(R.drawable.ic_baseline_mic_24)
                        .apply(defultImg)
                        .into(holder.imgReplayOG);
            }
        }
    }

    //================================= Incoming replay ============================================
    private void checkReplayVal(MyViewHolder holder, String rplId) {
//        List<Article> articleList = new ArrayList<Article>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<Datum> filteredArticleList = arrayList.stream().filter(article -> article.getId().equals(rplId)).collect(Collectors.toList());
            if (filteredArticleList.size() > 0)
                setUpReplayIncoming(holder, filteredArticleList.get(0));
        }
    }

    private void setUpReplayIncoming(MyViewHolder holder, Datum chatDatum) {
//        holder.clReplay.setVisibility(View.VISIBLE);
//        if (((ChatDetailsActivity) mContext).senderId.equals(chatDatum.getSenderId())) {
//            holder.txtSenderNameReplay.setText(((ChatDetailsActivity) mContext).senderName);
//        } else {
        holder.txtSenderNameReplay.setText(((ChatDetailsActivity) mContext).receiver_name);
//        }

        if (chatDatum.getMessageType().equals("text")) {
            holder.txtMessageReplay.setText(chatDatum.getMessage());
            holder.imgReplay.setVisibility(View.GONE);
        } else {
            holder.txtMessageReplay.setText(chatDatum.getMessageType());
            holder.imgReplay.setVisibility(View.VISIBLE);

            if (chatDatum.getMessageType().equals("image") || chatDatum.getMessageType().equals("gif")) {
                Glide.with(mContext)
                        .load(CHAT_MEDIA_URL + chatDatum.getMessage())
                        .apply(defultImg)
                        .into(holder.imgReplay);

            } else if (chatDatum.getMessageType().equals("document")) {
                Glide.with(mContext)
                        .load(R.drawable.doc_sender)
                        .apply(defultImg)
                        .into(holder.imgReplay);

            } else if (chatDatum.getMessageType().equals("video")) {
                Glide.with(mContext)
                        .load(CHAT_MEDIA_URL + chatDatum.getThumb())
                        .apply(defultImg)
                        .into(holder.imgReplay);

            } else if (chatDatum.getMessageType().equals("audio")) {
                Glide.with(mContext)
                        .load(R.drawable.ic_baseline_mic_24)
                        .apply(defultImg)
                        .into(holder.imgReplay);
            }
        }
    }
    //=============================================================================================

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.constraintIncoming)
        ConstraintLayout constraintIncoming;
        @BindView(R.id.constraintOutgoing)
        ConstraintLayout constraintOutgoing;

        @BindView(R.id.releDate)
        RelativeLayout releDate;
        @BindView(R.id.txtDate)
        TextView txtDate;

        //========================= Incoming ================================
        @BindView(R.id.imgChats)
        ImageView imgChats;
        @BindView(R.id.txtTime)
        TextView txtTime;
        @BindView(R.id.txtTime2)
        TextView txtTime2;
        @BindView(R.id.txtMessage)
        TextView txtMessage;


        @BindView(R.id.constraintAudio)
        ConstraintLayout constraintAudio;
        @BindView(R.id.imgProfileAudio)
        CircleImageView imgProfileAudio;
        @BindView(R.id.playAudio)
        ImageView playAudio;
        @BindView(R.id.pauseAudio)
        ImageView pauseAudio;
        @BindView(R.id.seekbar)
        SeekBar seekbar;
        @BindView(R.id.txtAudioDuration)
        TextView txtAudioDuration;

        @BindView(R.id.rel_layout_left)
        RelativeLayout rel_layout_left;
        @BindView(R.id.constraintMain)
        RelativeLayout constraintMain;
        @BindView(R.id.clReplay)
        ConstraintLayout clReplay;
        @BindView(R.id.txtSenderNameReplay)
        TextView txtSenderNameReplay;
        @BindView(R.id.txtSenderName)
        TextView txtSenderName;
        @BindView(R.id.txtMessageReplay)
        TextView txtMessageReplay;
        @BindView(R.id.imgReplay)
        ImageView imgReplay;

        @BindView(R.id.imgVideo)
        ImageView imgVideo;
        //========================= Outgoing ================================

        @BindView(R.id.imgChatsOG)
        ImageView imgChatsOG;
        @BindView(R.id.txtTimeOG)
        TextView txtTimeOG;
        @BindView(R.id.txtMessageOG)
        TextView txtMessageOG;

        @BindView(R.id.imgVideoOG)
        ImageView imgVideoOG;
//        @BindView(R.id.card1)
//        CardView card1;

        @BindView(R.id.constraintAudioOG)
        ConstraintLayout constraintAudioOG;
        @BindView(R.id.imgProfileAudioOG)
        CircleImageView imgProfileAudioOG;
        @BindView(R.id.playAudioOG)
        ImageView playAudioOG;
        @BindView(R.id.pauseAudioOG)
        ImageView pauseAudioOG;
        @BindView(R.id.seekbarOG)
        SeekBar seekbarOG;
        @BindView(R.id.txtAudioDurationOG)
        TextView txtAudioDurationOG;
        @BindView(R.id.txtSenderNameOG)
        TextView txtSenderNameOG;

        @BindView(R.id.clReplayOG)
        ConstraintLayout clReplayOG;
        @BindView(R.id.txtSenderNameReplayOG)
        TextView txtSenderNameReplayOG;
        @BindView(R.id.txtMessageReplayOG)
        TextView txtMessageReplayOG;
        @BindView(R.id.imgReplayOG)
        ImageView imgReplayOG;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}