package com.bnsantos.exif;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by bruno on 11/11/14.
 */
public class DownloadRequest extends Request<String> {
    private static final String TAG = DownloadRequest.class.getSimpleName();
    protected final Response.Listener<String> mListener;

    public DownloadRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        File photo = new File(Environment.getExternalStorageDirectory(), "photo.jpg");

        if (photo.exists()) {
            photo.delete();
        }

        try {
            Bitmap image = BitmapFactory.decodeByteArray(response.data, 0, response.data.length);
            FileOutputStream fos = new FileOutputStream(photo.getPath());
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return Response.success(photo.getAbsolutePath(), HttpHeaderParser.parseCacheHeaders(response));
        } catch (java.io.IOException e) {
            Log.e("PictureDemo", "Exception in photoCallback", e);
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        try {
            String stringError = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
            Log.e(TAG, "Volley error: " + stringError, error);
        } catch (Exception e) {
            Log.e(TAG, "Volley error: ", error);
        }
    }
}
