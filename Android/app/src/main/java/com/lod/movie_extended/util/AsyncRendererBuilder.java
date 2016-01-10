package com.lod.movie_extended.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.google.android.exoplayer.DefaultLoadControl;
import com.google.android.exoplayer.LoadControl;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecUtil;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.chunk.VideoFormatSelectorUtil;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.hls.HlsMasterPlaylist;
import com.google.android.exoplayer.hls.HlsPlaylist;
import com.google.android.exoplayer.hls.HlsPlaylistParser;
import com.google.android.exoplayer.hls.HlsSampleSource;
import com.google.android.exoplayer.metadata.Id3Parser;
import com.google.android.exoplayer.metadata.MetadataTrackRenderer;
import com.google.android.exoplayer.text.eia608.Eia608TrackRenderer;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.ManifestFetcher;
import com.lod.movie_extended.data.model.Player;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Жамбыл on 09.01.2016.
 */
public class AsyncRendererBuilder implements ManifestFetcher.ManifestCallback<HlsPlaylist> {

    private static final int BUFFER_SEGMENT_SIZE =64 * 1024;
    private static final int BUFFER_SEGMENTS = 256;
    private final Context context;
    private final String userAgent;
    private final String url;
    private final Player player;
    private final ManifestFetcher<HlsPlaylist> playlistFetcher;

    private boolean canceled;

    public AsyncRendererBuilder(Context context, String userAgent, String url, Player player) {
        this.context = context;
        this.userAgent = userAgent;
        this.url = url;
        this.player = player;
        HlsPlaylistParser parser = new HlsPlaylistParser();
        playlistFetcher = new ManifestFetcher<>(url, new DefaultUriDataSource(context, userAgent),
                parser);
    }

    public void init() {
        playlistFetcher.singleLoad(player.getMainHandler().getLooper(), this);
    }

    public void cancel() {
        canceled = true;
    }

    @Override
    public void onSingleManifestError(IOException e) {
        if (canceled) {
            return;
        }

        player.onRenderersError(e);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onSingleManifest(HlsPlaylist manifest) {
        if (canceled) {
            return;
        }

        Handler mainHandler = player.getMainHandler();
        LoadControl loadControl = new DefaultLoadControl(new DefaultAllocator(BUFFER_SEGMENT_SIZE));
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        int[] variantIndices = null;
        if (manifest instanceof HlsMasterPlaylist) {
            HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) manifest;
            try {
                variantIndices = VideoFormatSelectorUtil.selectVideoFormatsForDefaultDisplay(
                        context, masterPlaylist.variants, null, false);
            } catch (MediaCodecUtil.DecoderQueryException e) {
                player.onRenderersError(e);
                return;
            }
            if (variantIndices.length == 0) {
                player.onRenderersError(new IllegalStateException("No variants selected."));
                return;
            }
        }

        DataSource dataSource = new DefaultUriDataSource(context, bandwidthMeter, userAgent);
        HlsChunkSource chunkSource = new HlsChunkSource(dataSource, url, manifest, bandwidthMeter,
                variantIndices, HlsChunkSource.ADAPTIVE_MODE_SPLICE);
        HlsSampleSource sampleSource = new HlsSampleSource(chunkSource, loadControl,
                BUFFER_SEGMENTS * BUFFER_SEGMENT_SIZE, mainHandler, player, Player.TYPE_AUDIO);
        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource,
                null, true, player.getMainHandler(), player, AudioCapabilities.getCapabilities(context));
        MetadataTrackRenderer<Map<String, Object>> id3Renderer = new MetadataTrackRenderer<>(
                sampleSource, new Id3Parser(), player, mainHandler.getLooper());
        Eia608TrackRenderer closedCaptionRenderer = new Eia608TrackRenderer(sampleSource, player,
                mainHandler.getLooper());

        TrackRenderer[] renderers = new TrackRenderer[Player.RENDERER_COUNT];
        renderers[Player.TYPE_AUDIO] = audioRenderer;
        renderers[Player.TYPE_METADATA] = id3Renderer;
        renderers[Player.TYPE_TEXT] = closedCaptionRenderer;
        player.onRenderers(renderers, bandwidthMeter);
    }

}
