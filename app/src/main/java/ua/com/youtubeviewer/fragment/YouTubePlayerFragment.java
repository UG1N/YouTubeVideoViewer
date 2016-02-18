package ua.com.youtubeviewer.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class YouTubePlayerFragment extends YouTubePlayerSupportFragment
        implements YouTubePlayer.OnFullscreenListener {
    private static final String API_KEY = "AIzaSyAv4fecCOiL4nxAwq_KXN86bwkIRR8T5SY";
    private static final String VIDEO_URL = "Video_url";
    private static final int PORTRAIT_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    private static final int LANDSCAPE_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

    private YouTubePlayer mYouTubePlayer = null;
    private YouTubePlayer.OnFullscreenListener mFullScreenListener = null;
    private boolean mAutoRotation = false;

    public static YouTubePlayerFragment newInstance(String videoId) {
        YouTubePlayerFragment fragment = new YouTubePlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_URL, videoId);
        fragment.setArguments(bundle);
        fragment.init();
        return fragment;
    }

    public YouTubePlayerFragment() {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mAutoRotation = Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
        mFullScreenListener = this;
    }

    private void init() {
        initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean wasRestored) {
                mYouTubePlayer = youTubePlayer;
                mYouTubePlayer.setOnFullscreenListener(mFullScreenListener);
                mYouTubePlayer.addFullscreenControlFlag(
                        YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

                if (!wasRestored) {
                    mYouTubePlayer.loadVideo(getArguments().getString(VIDEO_URL));
                }
            }

            @Override
            public void onInitializationFailure(
                    YouTubePlayer.Provider provider,
                    YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public void onFullscreen(boolean isFullScreen) {
        if (isFullScreen) {
            getActivity().setRequestedOrientation(LANDSCAPE_ORIENTATION);
        } else if (!mAutoRotation) {
            getActivity().setRequestedOrientation(PORTRAIT_ORIENTATION);
        } else {
            getActivity().setRequestedOrientation(
                    getActivity().getResources().getConfiguration().orientation);
        }
    }
}
