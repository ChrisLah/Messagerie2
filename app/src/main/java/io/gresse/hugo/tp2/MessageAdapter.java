package io.gresse.hugo.tp2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.bumptech.glide.Glide.with;

/**
 * Display chat messages
 * <p>
 * Created by Hugo Gresse on 26/11/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private List<Message> mData;


    public static final String GRAVATAR_FIX = "https://www.gravatar.com/avatar/";
    public MessageAdapter(List<Message> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Message> data) {
        mData = data;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mUserTextView;
        TextView mContentTextView;
        TextView mTextViewTest;

        ViewHolder(View itemView) {
            super(itemView);

            mUserTextView = itemView.findViewById(R.id.userTextView);
            mContentTextView = itemView.findViewById(R.id.contentTextView);
            mTextViewTest = itemView.findViewById(R.id.textViewTest);
        }

        void setData(Message message) {
            Glide.with(mUserTextView.getContext())
                    .load(Constants.GRAVATAR_FIX + Utils.md5(message.userEmail))
                    .apply(RequestOptions.circleCropTransform())
                    .into(mUserTextView);
            mContentTextView.setText(message.userName + " : " + message.content);

            if(message.timestamp != 0) {
                Date now = new Date();
                long difference = now.getTime() - message.timestamp;
                String min = TimeUnit.MILLISECONDS.toMinutes(difference) + " minute ago";
                String heure = TimeUnit.MILLISECONDS.toMinutes(difference) + " hour ago";
                String jour = TimeUnit.MILLISECONDS.toMinutes(difference) + " day ago";

                if( TimeUnit.MILLISECONDS.toMinutes(difference) < 60){
                    mTextViewTest.setText("" + min);
                }else{
                    if( TimeUnit.MILLISECONDS.toMinutes(difference) < 24 ){
                        mTextViewTest.setText("" + heure);
                    }else{
                        mTextViewTest.setText("" + jour);
                    }
                }
            }
        }
    }
}
