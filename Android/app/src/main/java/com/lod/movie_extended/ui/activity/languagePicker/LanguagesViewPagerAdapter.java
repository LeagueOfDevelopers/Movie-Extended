package com.lod.movie_extended.ui.activity.languagePicker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ListView;

import com.lod.movie_extended.ui.fragment.languages.LanguagesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Жамбыл on 3/16/2016.
 */
public class LanguagesViewPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    public LanguagesViewPagerAdapter(FragmentManager fm, LanguagePickerView languagePickerView) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new LanguagesFragment(languagePickerView,true));
        fragments.add(new LanguagesFragment(languagePickerView,false));
        fragments.add(new LanguagesFragment(languagePickerView,true));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }


}
