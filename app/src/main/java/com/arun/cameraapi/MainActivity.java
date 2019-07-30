package com.arun.cameraapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnCamera, btnView;
    public static final int REQUEST_IMAGE_CAPTURE = 101;

    String currentImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = findViewById(R.id.btnCamera);
        btnView = findViewById(R.id.btnView);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayImage();
            }
        });

    }

    private void displayImage() {
        Intent intent = new Intent(this, ViewImageActivity.class);
        intent.putExtra("image_path", currentImagePath);
        startActivity(intent);
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (camera.resolveActivity(getPackageManager()) != null) {

            File imageFile = null;

            imageFile = getImageFile();

            if (imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(this, "com.arun.cameraapi.fileprovider", imageFile);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
            }


        }
    }

    private File getImageFile() {
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        String fileName = "jpg_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = null;
        try {
            imageFile = File.createTempFile(fileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;

    }


}
