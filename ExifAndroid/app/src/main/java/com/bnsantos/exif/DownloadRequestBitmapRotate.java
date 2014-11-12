package com.bnsantos.exif;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Created by bruno on 11/11/14.
 */
public class DownloadRequestBitmapRotate extends Request<Bitmap> {
    private static final String TAG = DownloadRequestBitmapRotate.class.getSimpleName();
    protected final Response.Listener<Bitmap> mListener;

    public DownloadRequestBitmapRotate(String url, Response.ErrorListener errorListener, Response.Listener<Bitmap> listener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
        int rotation = Exif.getOrientation(response.data);
        Log.d("LOG_EXIF", "Exif orientation: " + rotation);
        Bitmap image = BitmapFactory.decodeByteArray(response.data, 0, response.data.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        Bitmap rotatedBitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        return Response.success(rotatedBitmap, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Bitmap response) {
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
