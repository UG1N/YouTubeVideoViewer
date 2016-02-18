package ua.com.youtubeviewer.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ua.com.youtubeviewer.fragment.CoverChooserFragment;

public class ImageSaverAsync extends AsyncTask<Void, Void, Void> {

    private static final String TAG = ImageSaverAsync.class.getSimpleName();

    private Bitmap mBitmap;
    private String mFileName;
    private Callback mCallback;
    private Exception mException;

    public ImageSaverAsync(Bitmap bitmap, String videoUrl, int currentCover, Callback callback) {
        mBitmap = bitmap;
        mFileName = videoUrl + currentCover + ".jpg";
        mCallback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                mFileName);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (Exception e) {
            Log.e(TAG, "Could not save bitmap to file: " + file.getAbsolutePath());
            mException = e;
        } finally {
            if (fileOutputStream != null) {
                try { fileOutputStream.close(); } catch (IOException ignore) { }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (mCallback == null) {
            return;
        }
        if (mException != null) {
            mCallback.onFailed(mException);
        } else {
            mCallback.onSuccess();
        }
    }

    public interface Callback {
        void onSuccess();

        void onFailed(Exception exception);
    }
}

