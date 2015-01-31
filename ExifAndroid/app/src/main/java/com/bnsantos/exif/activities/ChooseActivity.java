package com.bnsantos.exif.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bnsantos.exif.DownloadTask;
import com.bnsantos.exif.Mode;
import com.bnsantos.exif.R;


public class ChooseActivity extends Activity {
    private static final int REQ_GALLERY = 752;
    private EditText mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        initViews();
        initListeners();
    }

    private void initViews() {
        mUrl = (EditText) findViewById(R.id.downloadUrl);
    }

    private void initListeners() {
        findViewById(R.id.galleryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageGallery();
            }
        });
        findViewById(R.id.downloadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    private void pickImageGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, REQ_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQ_GALLERY == requestCode && resultCode == RESULT_OK) {
            ImageActivity.start(this, Mode.GALLERY, data.getData());
        }
    }

    private void download() {
        DownloadTask downloadTask = new DownloadTask(this);
        downloadTask.execute(mUrl.getText().toString());
    }

    public void imageDownloaded(String path) {
        ImageActivity.start(this, Mode.DOWNLOAD, Uri.parse(path));
    }
}
