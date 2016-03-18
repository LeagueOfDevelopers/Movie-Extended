package com.lod.movie_extended.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Language {

    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("TrackFile")
    private TrackFile trackFile;
    @SerializedName("Subtitles")
    private Subtitle subtitle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrackFile getTrackFile() {
        return trackFile;
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTrackFile(TrackFile trackFile) {
        this.trackFile = trackFile;
    }

    public void setSubtitle(Subtitle subtitle) {
        this.subtitle = subtitle;
    }

    public static class TrackFile {
        @SerializedName("Id")
        private int id;

        public TrackFile(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public static class Subtitle {
        @SerializedName("Id")
        private int id;

        public Subtitle(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
