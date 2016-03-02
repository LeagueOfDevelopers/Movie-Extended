package com.lod.movie_extended.data.model.player;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;

import com.google.android.exoplayer.C;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.SingleSampleSource;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer.text.TextTrackRenderer;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.MimeTypes;
import com.lod.movie_extended.data.model.player.Player;

import timber.log.Timber;

/**
 * Created by Жамбыл on 2/15/2016.
 */
public class ExtractorRendererBuilder {

    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;

    private final Context context;
    private final String userAgent;
    private Uri uri;

    public ExtractorRendererBuilder(Context context, String userAgent) {
        this.context = context;
        this.userAgent = userAgent;
    }

    public void startBuildingRenderers(Player player, String audiUri) {
        this.uri = Uri.parse(audiUri);
        buildRenderers(player);
    }

    public void setSubtitlesUrl() {

    }

    private void buildRenderers(Player player) {
        if(uri == null) {
            Timber.e("uri is null");
            return;
        }

        Timber.v("building renderers");
        Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(player.getMainHandler(),null);
        DataSource dataSource = new DefaultUriDataSource(context, bandwidthMeter, userAgent);
        Extractor extractor = new Mp3Extractor();
        Uri subUrl = Uri.parse("http://movieextended1.azurewebsites.net/api/File/Get/54");

        MediaFormat mediaFormat = MediaFormat.createTextFormat(0, MimeTypes.APPLICATION_SUBRIP, MediaFormat.NO_VALUE, C.MATCH_LONGEST_US, null);
        SingleSampleSource textSource = new SingleSampleSource(subUrl, new DefaultUriDataSource(context, bandwidthMeter, userAgent)
                , mediaFormat);
        ExtractorSampleSource audioSource = new ExtractorSampleSource(uri, dataSource, allocator,
                BUFFER_SEGMENT_COUNT * BUFFER_SEGMENT_SIZE, extractor);
        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(audioSource,
                null, true, player.getMainHandler(), player,
                AudioCapabilities.getCapabilities(context), AudioManager.STREAM_MUSIC);
        TrackRenderer textRenderer = new TextTrackRenderer(textSource, player,
                player.getMainHandler().getLooper());

        // Invoke the callback.
        TrackRenderer[] renderers = new TrackRenderer[Player.RENDERER_COUNT];
        renderers[Player.TYPE_AUDIO] = audioRenderer;
        renderers[Player.TYPE_TEXT] = textRenderer;
        Timber.v("player.onRenderers");
        player.onRenderers(renderers, bandwidthMeter);
    }
}

