package com.lod.movie_extended.uil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;
import com.lod.movie_extended.injection.MyApp;

import javax.inject.Inject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Жамбыл on 25.11.2015.
 */
public class QrCodeReaderActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static final String QR_CODE_RESULT = "QR_CODE_RESULT";

    @Inject
    public ZXingScannerView scannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        MyApp.get(this).getAppComponent().inject(this);
        setContentView(scannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent(this,FilmPreparationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(QR_CODE_RESULT,result.getText());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
