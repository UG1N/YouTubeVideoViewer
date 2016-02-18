package ua.com.youtubeviewer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoItem implements Serializable {
    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("cover")
    private List<CoverUrl> mVideoCoverUrlList;
    @SerializedName("playUrl")
    private String mVideoUrl;

    public int getVideoId() {
        return mId;
    }

    public void setVideoId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<CoverUrl> getVideoCoverUrlList() {
        return mVideoCoverUrlList;
    }

    public void setVideoCoverUrlList(List<CoverUrl> videoCoverUrlList) {
        mVideoCoverUrlList = videoCoverUrlList;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public CoverUrl getMainCoverUrl() {
        return mVideoCoverUrlList.get(0);
    }
}
