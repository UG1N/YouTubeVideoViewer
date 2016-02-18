package ua.com.youtubeviewer.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ua.com.video_viewer.youtube.youtubevideoviewer.R;
import ua.com.youtubeviewer.adapter.OpenVideoClickListener;
import ua.com.youtubeviewer.adapter.VideoAdapter;
import ua.com.youtubeviewer.model.VideoItem;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class VideoViewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(isPortrait() ? VERTICAL : HORIZONTAL);

        VideoAdapter videoAdapter = new VideoAdapter(this);
        videoAdapter.setOpenVideoClickListener(new OpenVideoClickListener() {
            @Override
            public void onVideoClicked(VideoItem videoItem) {
                DetailVideoActivity.show(VideoViewListActivity.this, videoItem);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(videoAdapter);
    }

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
