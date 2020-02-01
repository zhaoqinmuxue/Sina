package org.aoli.weibo.delegates.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import org.aoli.weibo.ui.imagesdialog.BrowseImagesDialog;
import org.aoli.weibo.ui.ninegridimageview.NineGridImageView;
import org.aoli.weibo.ui.ninegridimageview.NineGridImageViewAdapter;
import org.aoli.weibo.util.RichTextUtil;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeiBoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<StatusContent> contents;
    private BaseActivity mContext;

    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;

    public WeiBoAdapter(Context context){
        super();
        mContext = (BaseActivity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weibo, parent, false);
            return new WeiBoViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WeiBoViewHolder) {
            WeiBoViewHolder weiBoViewHolder = (WeiBoViewHolder) holder;
            final StatusContent content = contents.get(position);
            final WeiBoUser user = content.getUser();
            final PicUrl[] picUrls = content.getPic_urls();
            final StatusContent repostContent = content.getRetweeted_status();
            Glide.with(mContext).load(user.getAvatar_large()).into(weiBoViewHolder.profileImage);
            weiBoViewHolder.screenName.setText(user.getScreen_name());
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDelegate userDelegate = new UserDelegate();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    userDelegate.setArguments(bundle);
                    mContext.getSupportFragmentManager().beginTransaction()
                            .add(R.id.delegate_container, userDelegate)
                            .addToBackStack("")
                            .commit();
                }
            };
            weiBoViewHolder.profileImage.setOnClickListener(listener);
            weiBoViewHolder.screenName.setOnClickListener(listener);
            weiBoViewHolder.timeAndSource.setText(content.getCreated_at());
            weiBoViewHolder.text.setText(RichTextUtil.toRichText(content.getText()));
            weiBoViewHolder.images.setAdapter(mNineGridImageViewAdapter);
            weiBoViewHolder.images.setImagesData(Arrays.asList(picUrls));
            if (repostContent != null) {
                weiBoViewHolder.repostStatus.setVisibility(View.VISIBLE);
                String temp = "@" + repostContent.getUser().getScreen_name() + ":" + repostContent.getText();
                weiBoViewHolder.repostText.setText(RichTextUtil.toRichText(temp));
                weiBoViewHolder.repostMsg.setText("转发" + repostContent.getReposts_count() + " | 评论" + repostContent.getComments_count() + " | 点赞" + repostContent.getAttitudes_count());
                weiBoViewHolder.respostImages.setAdapter(mNineGridImageViewAdapter);
                weiBoViewHolder.respostImages.setImagesData(Arrays.asList(repostContent.getPic_urls()));
            } else {
                weiBoViewHolder.repostStatus.setVisibility(View.GONE);
            }
            weiBoViewHolder.repostCount.setText(String.valueOf(content.getReposts_count()));
            weiBoViewHolder.commentCount.setText(String.valueOf(content.getComments_count()));
            weiBoViewHolder.attitudeCount.setText(String.valueOf(content.getAttitudes_count()));
        }
    }

    @Override
    public int getItemCount() {
        if (contents != null && contents.size() != 0)
            return contents.size() + 1;
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == contents.size()){
            return TYPE_FOOTER;
        }else {
            return TYPE_NORMAL;
        }
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
            Dialog dialog = new BrowseImagesDialog(context,list,index);
            dialog.show();
        }
    };

    class WeiBoViewHolder extends RecyclerView.ViewHolder{
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

    class FooterViewHolder extends RecyclerView.ViewHolder{

        private FooterViewHolder(@NonNull View view){
            super(view);
        }
    }
}
