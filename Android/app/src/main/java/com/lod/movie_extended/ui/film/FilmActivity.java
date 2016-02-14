package com.lod.movie_extended.ui.film;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.PopupMenu;

import com.google.android.exoplayer.text.CaptionStyleCompat;
import com.google.android.exoplayer.text.SubtitleLayout;
import com.google.android.exoplayer.util.Util;
import com.lod.movie_extended.App;
import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Player;
import com.lod.movie_extended.injection.component.activity.DaggerFilmComponent;
import com.lod.movie_extended.injection.component.activity.FilmComponent;
import com.lod.movie_extended.injection.module.activity.FilmModule;
import com.lod.movie_extended.ui.base.ComponentCreator;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.Presenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Жамбыл on 29.12.2015.
 */

public class FilmActivity extends InjectActivityBase implements FilmMvpView, ComponentCreator<FilmComponent>{

    private static final int LAYOUT = R.layout.activity_film;
    private MediaController mediaController;

    @Bind(R.id.controls_root)
    View debugRootView;
    @Bind(R.id.subtitles)
    SubtitleLayout subtitleLayout;
    @Bind(R.id.audio_controls)
    Button audioButton;
    @Bind(R.id.text_controls)
    Button textButton;
    @Bind(R.id.root)
    View root;

    @Inject
    FilmPresenter presenter;

    private boolean enableBackgroundAudio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.get(this).setAudioUrl("http://movieextended1.azurewebsites.net/api/file/get/43");
        super.onCreate(savedInstanceState);
        Timber.v("onCreate" + presenter.hashCode());

        mediaController = new MediaController(this);
        mediaController.setAnchorView(root);

        root.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                toggleControlsVisibility();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.performClick();
            }
            return true;
        });

        root.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE
                    || keyCode == KeyEvent.KEYCODE_MENU) {
                return false;
            }
            return mediaController.dispatchKeyEvent(event);
        });
    }

    @Override
    protected int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this);
        createComponent().inject(this);
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public FilmComponent createComponent() {
        return DaggerFilmComponent.builder()
                .filmModule(new FilmModule(this))
                .applicationComponent(App.get(this).getComponent())
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();
        configureSubtitleView();

        presenter.preparePlayer(true);
        presenter.startPlayerNotificationService();
    }

    // User controls

    @Override
    public MediaController getMyMediaController() {
        return mediaController;
    }

    public void updateButtonVisibilities() {
        audioButton.setVisibility(presenter.haveTracks(Player.TYPE_AUDIO) ? View.VISIBLE : View.GONE);
        textButton.setVisibility(presenter.haveTracks(Player.TYPE_TEXT) ? View.VISIBLE : View.GONE);
    }

    public void showAudioPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        Menu menu = popup.getMenu();
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE,"enable background audio");
        final MenuItem backgroundAudioItem = menu.findItem(0);
        backgroundAudioItem.setCheckable(true);
        backgroundAudioItem.setChecked(enableBackgroundAudio);
        PopupMenu.OnMenuItemClickListener clickListener = item -> {
            if (item == backgroundAudioItem) {
                enableBackgroundAudio = !item.isChecked();
                return true;
            }
            return false;
        };
        presenter.configurePopupWithTracks(popup, clickListener, Player.TYPE_AUDIO);
        popup.show();
    }

    public void showTextPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        presenter.configurePopupWithTracks(popup, null, Player.TYPE_TEXT);
        popup.show();
    }

    private void toggleControlsVisibility()  {
        if (mediaController.isShowing()) {
            mediaController.hide();
            debugRootView.setVisibility(View.GONE);
        } else {
            showControls();
        }
    }

    public void showControls() {
        mediaController.show(0);
        debugRootView.setVisibility(View.VISIBLE);
    }

    @Override
    public SubtitleLayout getSubtitleLayout() {
        return subtitleLayout;
    }


    private void configureSubtitleView() {
        CaptionStyleCompat style;
        float fontScale;
        if (Util.SDK_INT >= 19) {
            style = getUserCaptionStyleV19();
            fontScale = getUserCaptionFontScaleV19();
        } else {
            style = CaptionStyleCompat.DEFAULT;
            fontScale = 1.0f;
        }
        subtitleLayout.setStyle(style);
        subtitleLayout.setFractionalTextSize(SubtitleLayout.DEFAULT_TEXT_SIZE_FRACTION * fontScale);
    }

    @TargetApi(19)
    private float getUserCaptionFontScaleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) getSystemService(Context.CAPTIONING_SERVICE);
        return captioningManager.getFontScale();
    }

    @TargetApi(19)
    private CaptionStyleCompat getUserCaptionStyleV19() {
        CaptioningManager captioningManager =
                (CaptioningManager) getSystemService(Context.CAPTIONING_SERVICE);
        return CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
    }

    @Override
    protected void onDestroy() {
        Timber.v("onDestroy");
        super.onDestroy();
        presenter.removeListener();
        presenter.unregisterAudioCapabilitiesReceiver();
    }
}

