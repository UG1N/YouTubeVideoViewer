package ua.com.youtubeviewer.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import ua.com.video_viewer.youtube.youtubevideoviewer.R;
import ua.com.youtubeviewer.model.CoverUrl;
import ua.com.youtubeviewer.model.VideoItem;
import ua.com.youtubeviewer.utils.ImageSaverAsync;


public class CoverChooserFragment extends Fragment implements View.OnClickListener, Target {

    public static final String VIDEO_ITEM = "VideoItemForFragment";

    private VideoItem mVideoItem;
    private List<CoverUrl> mCoverUrls;
    private int mCurrentCover = 0;

    private ImageView mCoverView;
    private Button mPreviousButton;
    private Button mNextButton;

    public static CoverChooserFragment newInstance(VideoItem videoItem) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CoverChooserFragment.VIDEO_ITEM, videoItem);

        CoverChooserFragment coverChooserFragment = new CoverChooserFragment();
        coverChooserFragment.setArguments(bundle);
        return coverChooserFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cover_chooser, container, false);
        mVideoItem = (VideoItem) getArguments().getSerializable(VIDEO_ITEM);
        if (mVideoItem != null) {
            mCoverUrls = new ArrayList<>(mVideoItem.getVideoCoverUrlList());
        }

        mCoverView = (ImageView) view.findViewById(R.id.cover_dialog);
        mCoverView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Picasso.with(getActivity())
                        .load(mCoverUrls.get(mCurrentCover).getCoverUrl())
                        .into(CoverChooserFragment.this);
                return true;
            }
        });

        mPreviousButton = (Button) view.findViewById(R.id.cover_previous);
        mPreviousButton.setOnClickListener(this);
        mPreviousButton.setEnabled(false);
        mNextButton = (Button) view.findViewById(R.id.cover_next);
        mNextButton.setOnClickListener(this);

        updateCurrentCover();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cover_previous:
                mCurrentCover--;
                updateCurrentCover();
                break;
            case R.id.cover_next:
                mCurrentCover++;
                updateCurrentCover();
                break;
            default:
                break;
        }
    }

    private void updateCurrentCover() {
        mPreviousButton.setEnabled(mCurrentCover != 0);
        mNextButton.setEnabled(mCurrentCover < mCoverUrls.size() - 1);

        Picasso.with(getActivity())
                .load(mCoverUrls.get(mCurrentCover).getCoverUrl())
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(mCoverView);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        new ImageSaverAsync(bitmap, mVideoItem.getVideoUrl(), mCurrentCover, new ImageSaverAsync.Callback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), R.string.image_save, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Exception exception) {
                Toast.makeText(getActivity(), R.string.image_save_failed, Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Toast.makeText(getActivity(), R.string.image_save_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}