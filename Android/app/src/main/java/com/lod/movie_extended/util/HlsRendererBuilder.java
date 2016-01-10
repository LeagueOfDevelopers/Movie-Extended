package com.lod.movie_extended.util;

import android.content.Context;

import com.lod.movie_extended.data.model.Player;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class HlsRendererBuilder {

    private final Context context;
    private final String userAgent;
    private final String url;

    private AsyncRendererBuilder currentAsyncBuilder;

    public HlsRendererBuilder(Context context, String userAgent, String url) {
        this.context = context;
        this.userAgent = userAgent;
        this.url = url;
    }

    public void buildRenderers(Player player) {
        currentAsyncBuilder = new AsyncRendererBuilder(context, userAgent, url, player);
        currentAsyncBuilder.init();
    }


    public void cancel() {
        if (currentAsyncBuilder != null) {
            currentAsyncBuilder.cancel();
            currentAsyncBuilder = null;
        }
    }
}
