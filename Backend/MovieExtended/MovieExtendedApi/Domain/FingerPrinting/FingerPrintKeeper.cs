using System;
using Domain.Models.Entities;
using NAudio.Wave;
using SoundFingerprinting;
using SoundFingerprinting.Audio;
using SoundFingerprinting.Audio.NAudio;
using SoundFingerprinting.Builder;
using SoundFingerprinting.Configuration;
using SoundFingerprinting.DAO.Data;
using SoundFingerprinting.InMemory;

namespace Domain.FingerPrinting
{
    public class FingerPrintKeeper : IFingerPrintKeeper
    {
        private readonly IAudioService _audioService;
        private readonly IFingerprintCommandBuilder _fingerprintCommandBuilder;
        private readonly IModelService _modelService;


        private FingerprintConfiguration fingerprintConfiguration;
        private QueryConfiguration queryConfiguration;

        public FingerPrintKeeper()
        {
            _modelService = new InMemoryModelService();
            _audioService = new NAudioService();
            _fingerprintCommandBuilder = new FingerprintCommandBuilder();
            SetFingerPrintConfiguration();
            SetQueryConfiguration();
        }


        public void CreateHashes(string audiopath, Movie movie)
        {
            var reader = new MediaFoundationReader(audiopath);
            var track = new TrackData(
                "isrc " + movie.Id,
                "artist " + movie.Id,
                "title " + movie.Name,
                "album " + movie.Id,
                DateTime.Today.Year,
                reader.TotalTime.TotalMilliseconds
                );
            var trackReference = _modelService.InsertTrack(track);
            var hashedFingerprints = _fingerprintCommandBuilder
                .BuildFingerprintCommand()
                .From(audiopath).
                WithFingerprintConfig(fingerprintConfiguration)
                .UsingServices(_audioService)
                .Hash()
                .Result;

            _modelService.InsertHashDataForTrack(hashedFingerprints, trackReference);
        }

        private void SetFingerPrintConfiguration()
        {
            fingerprintConfiguration = new CustomFingerprintConfiguration();
            fingerprintConfiguration.SampleRate = 44100;
            fingerprintConfiguration.NormalizeSignal = true;
        }

        private void SetQueryConfiguration()
        {
            queryConfiguration = new QueryConfiguration();
            queryConfiguration.MaximumNumberOfTracksToReturnAsResult = 1;
        }
    }
}