package com.lod.movie_extended.uil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lod.movie_extended.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    @Bind(R.id.qr_code_read_button)
    Button qrReadButton;

    @Bind(R.id.result_text_view)
    TextView resultTextView;

    private static final int QR_REQUEST_CODE = 1;
    public static final String QR_RESULT = "QR_RESULT";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.qr_code_read_button)
    protected void OnQrReadButtonClick(View v){
        Intent intent = new Intent(this,QrCodeReaderActivity.class);
        startActivityForResult(intent,QR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onQrCodeActivityResult(requestCode,resultCode,data);
    }

    private void onQrCodeActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == QR_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString(QR_RESULT);
            resultTextView.setText(result);
        }
    }
}
