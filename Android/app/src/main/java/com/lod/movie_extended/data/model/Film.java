package com.lod.movie_extended.data.model;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Film {
    private List<Language> soundLanguages;
    private List<Language> subtitleLanguages;
    private Language selectedSoundLanguage;
    private Language selectedSubtitleLanguage;
    private Bitmap poster;
    private String name;
    private int id;

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    public List<Language> getSoundLanguages() {
        return soundLanguages;
    }

    public void setSoundLanguages(List<Language> soundLanguages) {
        this.soundLanguages = soundLanguages;
    }

    public Language getSelectedSoundLanguage() {
        return selectedSoundLanguage;
    }

    public List<Language> getSubtitleLanguages() {
        return subtitleLanguages;
    }

    public void setSubtitleLanguages(List<Language> subtitleLanguages) {
        this.subtitleLanguages = subtitleLanguages;
    }

    public void setSelectedSubtitleLanguage(Language selectedSubtitleLanguage) {
        if(!subtitleLanguages.contains(selectedSubtitleLanguage)) {
            throw new IllegalArgumentException();
        }
        this.selectedSubtitleLanguage = selectedSubtitleLanguage;
    }

    public void setSelectedSoundLanguage(Language selectedSoundLanguage) {
        if(!soundLanguages.contains(selectedSoundLanguage)) {
            throw new IllegalArgumentException();
        }
        this.selectedSoundLanguage = selectedSoundLanguage;
    }

    public Language getSelectedSubtitleLanguage() {
        return selectedSubtitleLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
