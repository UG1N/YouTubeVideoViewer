package ua.com.youtubeviewer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ua.com.video_viewer.youtube.youtubevideoviewer.R;
import ua.com.youtubeviewer.database.VideoDatabase;
import ua.com.youtubeviewer.model.VideoItem;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private VideoDatabase mVideoDatabase;
    private OpenVideoClickListener mOpenVideoClickListener;

    public VideoAdapter(Context context) {
        mVideoDatabase = VideoDatabase.loadDatabaseFromResources(context.getResources());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoItem item = mVideoDatabase.getItem(position);
        holder.mVideoTitle.setText(item.getTitle());
        Picasso.with(holder.itemView.getContext())
                .load(item.getMainCoverUrl().getCoverUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.mVideoCover);
    }

    @Override
    public int getItemCount() {
        return mVideoDatabase.getVideoDatabaseSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mVideoTitle;
        private ImageView mVideoCover;

        public ViewHolder(View itemView) {
            super(itemView);
            mVideoTitle = (TextView) itemView.findViewById(R.id.video_title);
            mVideoCover = (ImageView) itemView.findViewById(R.id.video_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (mOpenVideoClickListener != null) {
                mOpenVideoClickListener.onVideoClicked(mVideoDatabase.getItem(position));
            }
        }
    }

    public void setOpenVideoClickListener(OpenVideoClickListener openVideoClickListener) {
        mOpenVideoClickListener = openVideoClickListener;
    }
}
