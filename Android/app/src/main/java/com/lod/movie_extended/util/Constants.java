package com.lod.movie_extended.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lod.movie_extended.R;

/**
 * Created by Жамбыл on 10.01.2016.
 */
public class Constants {

    public interface ACTION {
        String MAIN_ACTION = "com.lod.movie_extended.action.main";
        String INIT_ACTION = "com.lod.movie_extended.action.init";
        String MOVE_TO_LEFT = "com.lod.movie_extended.action.prev";
        String PLAY_OR_PAUSE = "com.lod.movie_extended.action.playorpause";
        String MOVE_TO_RIGHT = "com.lod.movie_extended.action.next";
        String START_FOREGROUND_ACTION = "com.lod.movie_extended.action.startforeground";
        String STOP_FOREGROUND_ACTION = "com.lod.movie_extended.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.star_wars, options);
        } catch (Error | Exception ignored) {
        }
        return bm;
    }
}
