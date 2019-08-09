package com.example.clerkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.kyanogen.signatureview.SignatureView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DisbursementActivity extends AppCompatActivity {

    private int disbursementId;
    private ListView lv;
    Bitmap bitmapImg;
    Button clearBtn,submitBtn;
    SignatureView signatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement);

        disbursementId = 1001; //hardcoded here
        signatureView =  (SignatureView) findViewById(R.id.signature_view);
        clearBtn = (Button) findViewById(R.id.button_signatureClear);
        submitBtn = (Button) findViewById(R.id.button_signatureSubmit);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapImg = signatureView.getSignatureBitmap();
                String dirPath = saveImageToInternalStorage(bitmapImg);
                //pass this string back to next activity/fragment
                finish();
            }
        });
    }

    private String saveImageToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        File directory = cw.getDir("images", Context.MODE_PRIVATE);

        String dirPath = directory.getAbsolutePath();

        File imgFile=new File(directory,"disbursement_"+disbursementId+".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imgFile);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dirPath;
    }
}
