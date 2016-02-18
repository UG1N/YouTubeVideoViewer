package ua.com.youtubeviewer.database;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ua.com.video_viewer.youtube.youtubevideoviewer.R;
import ua.com.youtubeviewer.model.VideoItem;

public class VideoDatabase {
    private List<VideoItem> mVideoItems;

    public static VideoDatabase loadDatabaseFromResources(Resources res) {
        Gson gson = new GsonBuilder().create();

        Type listOfTestObject = new TypeToken<ArrayList<VideoItem>>() {
        }.getType();
        ArrayList<VideoItem> videoList = gson.fromJson(new InputStreamReader(res.openRawResource(R.raw.videoitems)),
                listOfTestObject);
        return new VideoDatabase(videoList);
    }

    public VideoDatabase(List<VideoItem> videoItems) {
        mVideoItems = videoItems;
    }

    public VideoItem getItem(int position) {
        return mVideoItems.get(position);
    }

    public int getVideoDatabaseSize() {
        return mVideoItems.size();
    }
}
