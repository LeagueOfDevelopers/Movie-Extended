package com.lod.movie_extended.util;

import com.google.android.exoplayer.DecoderInfo;
import com.google.android.exoplayer.MediaCodecUtil;
import com.google.android.exoplayer.MediaFormat;

/**
 * Created by Жамбыл on 2/15/2016.
 */
public interface MediaCodecSelector {

    /**
     * Default implementation of {@link MediaCodecSelector}.
     */
    public static final MediaCodecSelector DEFAULT = new MediaCodecSelector() {

        /**
         * The name for the raw (passthrough) decoder OMX parentComponent.
         */
        private static final String RAW_DECODER_NAME = "OMX.google.raw.decoder";

        @Override
        public DecoderInfo getDecoderInfo(MediaFormat format, boolean requiresSecureDecoder)
                throws MediaCodecUtil.DecoderQueryException {
            return MediaCodecUtil.getDecoderInfo(format.mimeType, requiresSecureDecoder);
        }

        @Override
        public String getPassthroughDecoderName() throws MediaCodecUtil.DecoderQueryException {
            // TODO: Return null if the raw decoder doesn't exist.
            return RAW_DECODER_NAME;
        }

    };

    /**
     * Selects a decoder to instantiate for a given format.
     *
     * @param format The format for which a decoder is required.
     * @param requiresSecureDecoder Whether a secure decoder is required.
     * @return A {@link DecoderInfo} describing the decoder to instantiate, or null if no suitable
     *     decoder exists.
     * @throws MediaCodecUtil.DecoderQueryException Thrown if there was an error querying decoders.
     */
    DecoderInfo getDecoderInfo(MediaFormat format, boolean requiresSecureDecoder)
            throws MediaCodecUtil.DecoderQueryException;

    /**
     * Gets the name of a decoder suitable for audio passthrough.
     *
     * @return The name of a decoder suitable for audio passthrough, or null if no suitable decoder
     *     exists.
     * @throws MediaCodecUtil.DecoderQueryException Thrown if there was an error querying decoders.
     */
    String getPassthroughDecoderName() throws MediaCodecUtil.DecoderQueryException;

}
