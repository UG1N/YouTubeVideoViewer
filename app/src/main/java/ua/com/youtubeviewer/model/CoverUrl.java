package ua.com.youtubeviewer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CoverUrl implements Serializable {
    @SerializedName("coverUrl")
    private String mCoverUrl;

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        mCoverUrl = coverUrl;
    }
}
