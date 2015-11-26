package com.lod.movie_extended.uil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lod.movie_extended.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    public static final boolean OMIT_QR_CODE = true;

    @Bind(R.id.qr_code_read_button)
    private Button qrReadButton;

    @Bind(R.id.result_text_view)
    private TextView resultTextView;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.qr_code_read_button)
    protected void OnQrReadButtonClick(View v){
        Intent intent;

        if(OMIT_QR_CODE) {
            intent = new Intent(this, NewActivity.class);
        }
        else {
            intent = new Intent(this, QrCodeReaderActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
