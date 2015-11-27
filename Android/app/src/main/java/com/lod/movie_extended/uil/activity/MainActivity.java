package com.lod.movie_extended.uil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lod.movie_extended.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    public  final static int LAYOUT = R.layout.activity_main;

    public static final boolean OMIT_QR_CODE_READING = false;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.qr_code_read_button)
    protected void OnQrReadButtonClick(View v){
        Intent intent;

        if(OMIT_QR_CODE_READING) {
            intent = new Intent(this, NewActivity.class);
        }
        else {
            intent = new Intent(this, QrCodeReaderActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
