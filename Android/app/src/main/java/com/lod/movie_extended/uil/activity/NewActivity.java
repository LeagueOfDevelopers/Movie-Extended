package com.lod.movie_extended.uil.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lod.movie_extended.R;
import com.lod.movie_extended.bll.IServer;
import com.lod.movie_extended.bll.ServerMock;

/**
 * Created by Жамбыл on 25.11.2015.
 */

public class NewActivity extends AppCompatActivity {

    private ActionBar toolbar;

    private IServer server;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        initToolbar();
        initServer();

    }

    private void initServer() {
        server = new ServerMock();
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        toolbar = getSupportActionBar();
    }
}
