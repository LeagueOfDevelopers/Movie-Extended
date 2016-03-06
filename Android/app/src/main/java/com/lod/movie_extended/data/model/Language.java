package com.lod.movie_extended.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class Language {

    @SerializedName("Name")
    private String name;
    @SerializedName("TrackFile")
    private TrackFile trackFile;
    @SerializedName("Subtitles")
    private Subtitles subtitles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrackFile getTrackFile() {
        return trackFile;
    }

    public Subtitles getSubtitles() {
        return subtitles;
    }


    public static class TrackFile {
        @SerializedName("Id")
        private int id;
        @SerializedName("FilePath")
        private String filePath;
        @SerializedName("FileType")
        private String fileType;

        public int getId() {
            return id;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getFileType() {
            return fileType;
        }
    }

    public static class Subtitles {
        @SerializedName("Id")
        private int id;
        @SerializedName("FilePath")
        private String filePath;
        @SerializedName("FileType")
        private String fileType;

        public int getId() {
            return id;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getFileType() {
            return fileType;
        }
    }
}
