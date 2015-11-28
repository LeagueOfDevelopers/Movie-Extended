package com.lod.movie_extended.uil.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lod.movie_extended.R;
import com.lod.movie_extended.bll.IServer;
import com.lod.movie_extended.event.ServerError;
import com.lod.movie_extended.injection.MyApp;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 25.11.2015.
 */

public class FilmPreparationActivity extends AppCompatActivity {

    public  final static int LAYOUT = R.layout.activity_new;
    private static final String L_TAG = "FilmPrepActivity";

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.main_text_view)
    TextView mainTextView;

    public ActionBar toolbar;

    @Inject
    public Bus serverEvents;

    @Inject
    public IServer server;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        injectFields();
        setUpUI();
    }

    private void injectFields() {
        ButterKnife.bind(this);
        MyApp.get(this).getServerComponent().inject(this);
        serverEvents.register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        handleQrCodeResult(getIntent().getExtras());
    }

    public void handleQrCodeResult(Bundle resultBundle) {
        if(resultBundle == null) {
            Log.v(MyApp.TAG + L_TAG,"qrCode bundle is null");
            return;
        }

        String result = resultBundle.getString(QrCodeReaderActivity.QR_CODE_RESULT);
        Log.v(MyApp.TAG + L_TAG, "qr code is \"" + result +"\"");

        server.sendCode(result);
    }

    private void setUpUI() {
        initToolbar();
        setViewsVisible();
    }

    @Subscribe
    public void onServerError(ServerError event){

    }

    private void setViewsVisible() {
        progressBar.setVisibility(View.INVISIBLE);
        mainTextView.setVisibility(View.VISIBLE);
        toolbar.setTitle("Title");
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        toolbar = getSupportActionBar();

        if(toolbar != null) {
            toolbar.setTitle("");
        }
    }
}
