package com.bnsantos.exif.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bnsantos.exif.ExifInfoDialog;
import com.bnsantos.exif.Mode;
import com.bnsantos.exif.R;
import com.squareup.picasso.Picasso;


public class ImageActivity extends Activity {
    private static final float ROTATE_LEFT = -90f;
    private static final float ROTATE_RIGHT = 90f;

    private ImageView mImageView;
    private float mRotation = 0;
    private Mode mMode;
    private Uri mUri;

    public static void start(Context context, Mode mode, Uri uri) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(Mode.class.getName(), mode.name());
        intent.setData(uri);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        initViews();
        initListeners();
        extractData(getIntent());
        showImage();
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    private void initListeners() {
        findViewById(R.id.rotateLeftButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate(ROTATE_LEFT);
            }
        });
        findViewById(R.id.rotateRightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate(ROTATE_RIGHT);
            }
        });

        findViewById(R.id.infoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageExifInfo();
            }
        });
    }

    private void rotate(float degrees) {
        mRotation += degrees;
        mImageView.setRotation(mRotation);
    }

    private void extractData(Intent intent) {
        if (intent != null) {
            mUri = intent.getData();
            mMode = Mode.valueOf(intent.getStringExtra(Mode.class.getName()));
        }
    }

    private void showImage() {
        if (mUri != null) {
            Picasso.with(this).load(mUri).error(android.R.color.holo_red_dark).centerInside().fit().into(mImageView);
        }
    }

    private void showImageExifInfo() {
        if (mUri != null) {
            new ExifInfoDialog(mUri).show(getFragmentManager(), "ExifInfo");
        }
    }
}
