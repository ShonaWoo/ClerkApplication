package com.example.clerkapplication;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private Button trackMeBtn;
    private Button viewDisbursementBtn;
    private String dirPath = "/data/user/0/com.example.clerkapplication/app_images";
    private int disbursementId = 1001; //hardcoded

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initChannels(this);

        trackMeBtn = findViewById(R.id.buttonTrackMe);
        trackMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackMeBtn.setOnClickListener(null);
                trackMeBtn.setText("Tracking...");
                trackMeBtn.setTextColor(Color.RED);
                Intent i = new Intent(MainActivity.this, TrackerActivity.class);
                startActivity(i);
            }
        });

        viewDisbursementBtn = findViewById(R.id.buttonViewDisbursement);
        viewDisbursementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DisbursementActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImageFromInternalStorage();
    }

    private void loadImageFromInternalStorage()
    {

        try {
            File f = new File(dirPath, "disbursement_"+disbursementId+".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = findViewById(R.id.imageView);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    private void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Default Notifications Channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Default Notifications");
        notificationManager.createNotificationChannel(channel);
    }

}
