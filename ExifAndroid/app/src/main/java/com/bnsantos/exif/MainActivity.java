package com.bnsantos.exif;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;


public class MainActivity extends Activity implements Response.ErrorListener {
    private static final float ROTATE_LEFT = -90f;
    private static final float ROTATE_RIGHT = 90f;

    private Button mDownload;
    private ImageButton mLeft;
    private ImageButton mRight;
    private ImageButton mInfo;
    private EditText mUrl;
    private ImageView mImageView;
    private float mRotation = 0;
    private Uri mPicture;
    private DownloadType mDownloadType = DownloadType.BITMAP;
    private final FileListener mFileListener = new FileListener();
    private final BitmapListener mBitmapListener = new BitmapListener();
    private TextView mDownloadMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListeners();

        mDownloadMode.setText(getString(R.string.download_mode, mDownloadType.name()));
    }

    private void initViews() {
        mDownload = (Button) findViewById(R.id.downloadButton);
        mLeft = (ImageButton) findViewById(R.id.rotateLeftButton);
        mRight = (ImageButton) findViewById(R.id.rotateRightButton);
        mInfo = (ImageButton) findViewById(R.id.infoButton);
        mUrl = (EditText) findViewById(R.id.downloadUrl);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mDownloadMode = (TextView) findViewById(R.id.downloadMode);
    }

    private void initListeners() {
        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPicture();
            }
        });

        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate(ROTATE_LEFT);
            }
        });
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate(ROTATE_RIGHT);
            }
        });
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageExifInfo();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_download_file) {
            mDownloadType = DownloadType.FILE;
            mDownloadMode.setText(getString(R.string.download_mode, mDownloadType.name()));
            return true;
        } else if (id == R.id.action_download_bitmap) {
            mDownloadType = DownloadType.BITMAP;
            mDownloadMode.setText(getString(R.string.download_mode, mDownloadType.name()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void enableButtons(boolean enable) {
        mDownload.setEnabled(enable);
        mLeft.setEnabled(enable);
        mRight.setEnabled(enable);
    }

    private void downloadPicture() {
        enableButtons(false);
        switch (mDownloadType) {
            case FILE:
                App.getInstance().getRequestQueue().add(new DownloadRequestSavePictureSdCard(mUrl.getText().toString(), this, mFileListener));
                break;
            case BITMAP:
                App.getInstance().getRequestQueue().add(new DownloadRequestBitmapRotate(mUrl.getText().toString(), this, mBitmapListener));
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, R.string.error_downloading, Toast.LENGTH_SHORT).show();
        enableButtons(true);
    }

    private void rotate(float degrees) {
        mRotation += degrees;
        mImageView.setRotation(mRotation);
    }

    private void setImageView(String uri) {
        mPicture = Uri.parse(uri);
        mImageView.setImageURI(mPicture);
    }

    private void setImageView(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    private void showImageExifInfo() {
        if (mPicture != null) {
            new ExifInfoDialog(mPicture).show(getFragmentManager(), "ExifInfo");
        }
    }

    public enum DownloadType {
        BITMAP, FILE
    }

    private class FileListener implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            setImageView(response);
            enableButtons(true);
        }
    }

    private class BitmapListener implements Response.Listener<Bitmap> {

        @Override
        public void onResponse(Bitmap response) {
            setImageView(response);
            enableButtons(true);
        }
    }
}
