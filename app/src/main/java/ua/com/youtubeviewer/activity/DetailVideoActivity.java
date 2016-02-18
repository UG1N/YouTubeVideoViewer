package ua.com.youtubeviewer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ua.com.video_viewer.youtube.youtubevideoviewer.R;
import ua.com.youtubeviewer.fragment.CoverChooserFragment;
import ua.com.youtubeviewer.fragment.YouTubePlayerFragment;
import ua.com.youtubeviewer.model.VideoItem;

public class DetailVideoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String VIDEO_EXTRA = "Video_Item";

    private VideoItem mVideoItem;
    private ImageView mCoverImage;
    private TextView mTitle;
    private FrameLayout mCoverPopupFrame;

    public static void show(Activity activity, VideoItem videoItem) {
        Intent intent = new Intent(activity, DetailVideoActivity.class);
        intent.putExtra(DetailVideoActivity.VIDEO_EXTRA, videoItem);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // for new YouTube player-view
        // http://stackoverflow.com/questions/33379320/cannot-load-modern-controls-ui-unable-to-play-youtube-videos-in-full-screen
        getLayoutInflater().setFactory(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);

        Intent intent = getIntent();
        mVideoItem = (VideoItem) intent.getSerializableExtra(VIDEO_EXTRA);

        mTitle = (TextView) findViewById(R.id.video_detail_title);
        mCoverPopupFrame = (FrameLayout) findViewById(R.id.detail_cover_frame);
        mCoverPopupFrame.setOnClickListener(this);

        mCoverImage = (ImageView) findViewById(R.id.video_detail_cover);
        mCoverImage.setOnClickListener(this);

        setYouTubeFragment();

        bindView();
    }

    private void bindView() {
        mTitle.setText(mVideoItem.getTitle());
        Picasso.with(this)
                .load(mVideoItem.getMainCoverUrl().getCoverUrl())
                .into(mCoverImage);
    }

    private void setYouTubeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_youtube_frame,
                        YouTubePlayerFragment.newInstance(mVideoItem.getVideoUrl()))
                .commit();
    }

    private void setDetailInfoFragment() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.detail_cover_frame, CoverChooserFragment.newInstance(mVideoItem))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_cover_frame:
                v.setVisibility(View.GONE);
                break;
            case R.id.video_detail_cover:
                mCoverPopupFrame.setVisibility(View.VISIBLE);
                setDetailInfoFragment();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}