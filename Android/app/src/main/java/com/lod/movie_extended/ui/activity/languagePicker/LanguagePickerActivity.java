package com.lod.movie_extended.ui.activity.languagePicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lod.movie_extended.R;
import com.lod.movie_extended.data.model.Film;
import com.lod.movie_extended.injection.App;
import com.lod.movie_extended.injection.component.activity.DaggerLanguagesPickerComponent;
import com.lod.movie_extended.injection.component.activity.LanguagesPickerComponent;
import com.lod.movie_extended.injection.module.activity.LanguagesPickerModule;
import com.lod.movie_extended.ui.base.ComponentGetter;
import com.lod.movie_extended.ui.base.InjectActivityBase;
import com.lod.movie_extended.ui.base.Presenter;
import com.lod.movie_extended.ui.fragment.languages.LanguagesFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Жамбыл on 3/16/2016.
 */
public class LanguagePickerActivity extends InjectActivityBase implements LanguagePickerView, ComponentGetter<LanguagesPickerComponent> {

    private final static int LAYOUT = R.layout.activity_language_picker;
    private LanguagesPickerComponent component;
    private List<ImageView> dots;
    @Inject
    LanguagePickerPresenter presenter;
    @Inject
    LanguagesViewPagerAdapter languagesViewPagerAdapter;

    @Bind(R.id.languages_selection_view_pager)
    CustomViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.next_text_view)
    TextView nextTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPager();
        initDots();
        setToolbarTitleByPosition(0);
        initNextTextView();
    }

    @Override
    public void notifyLanguageHasBeenPicked() {
        allowNext(View.VISIBLE, CustomViewPager.SwipeDirection.all);
    }

    private void initNextTextView() {
        nextTextView.setVisibility(View.INVISIBLE);
        nextTextView.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem()+1));
    }

    private void initDots() {
        dots = new ArrayList<>();
        dots.add((ImageView) findViewById(R.id.firstDot));
        dots.add((ImageView) findViewById(R.id.secondDot));
        dots.add((ImageView) findViewById(R.id.thirdDot));
    }

    private void initViewPager() {
        viewPager.setAdapter(languagesViewPagerAdapter);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.left);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LanguagesFragment currentFragment = (LanguagesFragment) languagesViewPagerAdapter.getItem(position);
                if(currentFragment.isAllowedNext()) {
                    allowNext(View.VISIBLE, CustomViewPager.SwipeDirection.all);
                }
                else {
                    allowNext(View.INVISIBLE, CustomViewPager.SwipeDirection.left);
                }
                setToolbarTitleByPosition(position);
                setActiveDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void allowNext(int visible, CustomViewPager.SwipeDirection swipeDirection) {
        nextTextView.setVisibility(visible);
        viewPager.setAllowedSwipeDirection(swipeDirection);
    }

    private void setToolbarTitleByPosition(int position) {
        switch (position) {
            case 0:
                toolbar.setTitle("Язык озвучки");
                break;
            case 1:
                toolbar.setTitle("Язык субтитров");
                break;
            case 2:
                toolbar.setTitle("Поехали");
                break;
        }
        setSupportActionBar(toolbar);
    }

    private void setActiveDot(int position) {
        for (int i = 0; i < dots.size(); i++) {
            if(i == position) {
                dots.get(i).setImageResource(R.mipmap.active_dot);
            }
            else {
                dots.get(i).setImageResource(R.mipmap.inactive_dot);
            }
        }
    }

    @Override
    public int getContentView() {
        return LAYOUT;
    }

    @Override
    public void inject() {
        ButterKnife.bind(this);
        getComponent().inject(this);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }


    @Override
    public LanguagesPickerComponent getComponent() {
        if(component == null) {
            component = DaggerLanguagesPickerComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .languagesPickerModule(new LanguagesPickerModule(this))
                .build();
        }
        return component;
    }

    @Override
    public void setComponent(LanguagesPickerComponent component) {
        this.component = component;
    }
}
