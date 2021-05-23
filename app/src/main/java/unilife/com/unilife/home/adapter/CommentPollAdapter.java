package unilife.com.unilife.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import unilife.com.unilife.R;
import unilife.com.unilife.home.Comment;
import unilife.com.unilife.home.Home;
import unilife.com.unilife.home.responses.PostOption;
import unilife.com.unilife.updated.CommentsActivity;

public class CommentPollAdapter extends RecyclerView.Adapter<CommentPollAdapter.MyViewHolder> {

    private ArrayList<PostOption> arrayList;
    private Context context;
    private int prevPosition = 0;
    private boolean isVoted = false;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommentPollAdapter(Context context, ArrayList<PostOption> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

//        List<Article> articleList = new ArrayList<Article>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<PostOption> filteredArticleList = arrayList.stream().filter(article -> article.getSelected().equals("yes")).collect(Collectors.toList());
            if (filteredArticleList.size() > 0) {
                isVoted = true;
            }
        }
    }

    public void updateData(ArrayList<PostOption> arrayList) {
        this.arrayList = arrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override

    public CommentPollAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_poll, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.option1.setText(arrayList.get(position).getOptions());
        if (arrayList.get(position).getSelected().equals("yes")) {
            arrayList.get(position).setSelect(true);
            prevPosition = position;
        }
        if (arrayList.get(position).isSelect()) {
            holder.option1.setTextColor(context.getResources().getColor(R.color.white));
            holder.option1.setBackgroundResource(R.drawable.back_green_fill);
            holder.txtVotes.setText(arrayList.get(position).getSelectedCount() + " votes");
        } else {
            holder.option1.setTextColor(context.getResources().getColor(R.color.vdark_grey));
            holder.option1.setBackgroundResource(R.drawable.back_green);
        }

        holder.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isVoted) {
                    ((CommentsActivity) context).selectPoll(arrayList.get(position).getId(),position);
                }
//                arrayList.get(prevPosition).setSelect(false);
//                arrayList.get(position).setSelect(true);
//                prevPosition = position;
//                notifyDataSetChanged();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arrayList.size();
//        return arrayList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.txtVotes)
        public TextView txtVotes;
        @BindView(R.id.option1)
        public TextView option1;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}