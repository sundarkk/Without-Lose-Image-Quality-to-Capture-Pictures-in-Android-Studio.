package com.example.cameramegapixelclick;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnCamera;

    private static int CAMERA_PERMISSION_REQUEST_CODE = 1;

    private String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        // Check Camera Permission already granted or not
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Camera permission is already granted", Toast.LENGTH_SHORT).show();
        } else {
            // Request Camera Permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camIntent, CAMERA_PERMISSION_REQUEST_CODE);*/
               String filename = "photo";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File imageFile = File.createTempFile(filename, ".jpg",storageDirectory);

                  Uri imageuri =  FileProvider.getUriForFile(MainActivity.this,"com.example.cameramegapixelclick.fileprovider",imageFile);
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    camIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                    startActivityForResult(camIntent,1);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) BitmapFactory.decodeFile(currentPhotoPath);
                   // .get("data");

            imageView.setImageBitmap(bitmap);
        }
    }
}
