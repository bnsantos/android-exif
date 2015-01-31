package com.bnsantos.exif;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.bnsantos.exif.activities.ChooseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bruno on 31/01/15.
 */
public class DownloadTask extends AsyncTask<String, Void, String> {
    private static final BitmapFactory.Options OPTIONS = new BitmapFactory.Options();

    static {
        OPTIONS.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    private final ChooseActivity mListener;

    public DownloadTask(ChooseActivity mListener) {
        this.mListener = mListener;
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream = downloadImage(params[0]);
        return saveImageToCache(inputStream);
    }

    @Override
    protected void onPostExecute(String s) {
        mListener.imageDownloaded(s);
    }

    private InputStream downloadImage(String path) {
        InputStream inputStream = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    private String saveImageToCache(InputStream input) {
        final File file = new File(mListener.getCacheDir(), "cached.png");
        try {
            final OutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int read;

            while ((read = input.read(buffer)) != -1)
                output.write(buffer, 0, read);

            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "file://" + file.getAbsolutePath();
    }
}
