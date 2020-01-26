package org.aoli.weibo.delegates.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.aoli.weibo.BaseActivity;
import org.aoli.weibo.R;
import org.aoli.weibo.delegates.user.UserDelegate;
import org.aoli.weibo.sinasdk.bean.PicUrl;
import org.aoli.weibo.sinasdk.bean.StatusContent;
import org.aoli.weibo.sinasdk.bean.WeiBoUser;
import org.aoli.weibo.ui.ninegridimageview.NineGridImageView;
import org.aoli.weibo.ui.ninegridimageview.NineGridImageViewAdapter;
import org.aoli.weibo.util.RichTextUtil;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeiBoAdapter extends RecyclerView.Adapter<WeiBoAdapter.WeiBoViewHolder> {
    private List<StatusContent> contents;
    private BaseActivity mContext;

    public WeiBoAdapter(Context context){
        super();
        mContext = (BaseActivity) context;
    }

    @NonNull
    @Override
    public WeiBoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weibo,parent,false);
        final WeiBoViewHolder viewHolder = new WeiBoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeiBoViewHolder holder, int position) {
        final StatusContent content = contents.get(position);
        final WeiBoUser user = content.getUser();
        final PicUrl[] picUrls = content.getPic_urls();
        final StatusContent repostContent = content.getRetweeted_status();
        Glide.with(mContext).load(user.getAvatar_large()).into(holder.profileImage);
        holder.screenName.setText(user.getScreen_name());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDelegate userDelegate = new UserDelegate();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                userDelegate.setArguments(bundle);
                mContext.getSupportFragmentManager().beginTransaction()
                        .add(R.id.delegate_container,userDelegate)
                        .commit();
            }
        };
        holder.profileImage.setOnClickListener(listener);
        holder.screenName.setOnClickListener(listener);
        holder.timeAndSource.setText(content.getCreated_at());
        holder.text.setText(RichTextUtil.toRichText(content.getText()));
        holder.images.setAdapter(mNineGridImageViewAdapter);
        holder.images.setImagesData(Arrays.asList(picUrls));
        if (repostContent != null){
            holder.repostStatus.setVisibility(View.VISIBLE);
            String temp = "@" + repostContent.getUser().getScreen_name() + ":" + repostContent.getText();
            holder.repostText.setText(RichTextUtil.toRichText(temp));
            holder.repostMsg.setText("转发" + repostContent.getReposts_count() + " | 评论" + repostContent.getComments_count() + " | 点赞" + repostContent.getAttitudes_count());
            holder.respostImages.setAdapter(mNineGridImageViewAdapter);
            holder.respostImages.setImagesData(Arrays.asList(repostContent.getPic_urls()));
        }else {
            holder.repostStatus.setVisibility(View.GONE);
        }
        holder.repostCount.setText(String.valueOf(content.getReposts_count()));
        holder.commentCount.setText(String.valueOf(content.getComments_count()));
        holder.attitudeCount.setText(String.valueOf(content.getAttitudes_count()));
    }

    @Override
    public int getItemCount() {
        if (contents != null)
            return contents.size();
        else
            return 0;
    }

    public void setData(List<StatusContent> contents){
        this.contents = contents;
        notifyDataSetChanged();
    }

    public void addData(List<StatusContent> contents){
        final int start = this.contents.size();
        final int count = contents.size();
        this.contents.addAll(contents);
        notifyItemRangeInserted(start,count);
    }

    private NineGridImageViewAdapter<PicUrl> mNineGridImageViewAdapter = new NineGridImageViewAdapter<PicUrl>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, PicUrl picUrl) {
            Glide.with(context).load(picUrl.getBmiddle_pic()).into(imageView);
        }

        @Override
        protected void onItemImageClick(Context context, int index, List<PicUrl> list) {
            super.onItemImageClick(context, index, list);
        }
    };

    static class WeiBoViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profileImage;
        TextView screenName;
        TextView timeAndSource;
        TextView text;
        NineGridImageView<PicUrl> images;
        LinearLayout repostStatus;
        TextView repostText;
        TextView repostMsg;
        NineGridImageView<PicUrl> respostImages;
        ImageView repostIcon;
        TextView repostCount;
        ImageView commentIcon;
        TextView commentCount;
        ImageView attitudeIcon;
        TextView attitudeCount;
        ImageView moreFunction;

        private WeiBoViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            screenName = itemView.findViewById(R.id.screen_name);
            timeAndSource = itemView.findViewById(R.id.time_and_source);
            text = itemView.findViewById(R.id.text);
            images = itemView.findViewById(R.id.nine_images);
            repostStatus = itemView.findViewById(R.id.repost_status);
            repostText = itemView.findViewById(R.id.repost_text);
            repostMsg = itemView.findViewById(R.id.repost_msg);
            respostImages = itemView.findViewById(R.id.repost_nine_images);
            repostIcon = itemView.findViewById(R.id.repost_icon);
            repostCount = itemView.findViewById(R.id.reposts_count);
            commentIcon = itemView.findViewById(R.id.comment_icon);
            commentCount = itemView.findViewById(R.id.comments_count);
            attitudeIcon = itemView.findViewById(R.id.attitude_icon);
            attitudeCount = itemView.findViewById(R.id.attitudes_count);
            moreFunction = itemView.findViewById(R.id.more_function);
        }
    }
}
