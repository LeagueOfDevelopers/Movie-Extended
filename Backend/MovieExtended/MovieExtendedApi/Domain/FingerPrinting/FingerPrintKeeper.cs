using System;
using System.Collections.Generic;
using Domain.Models.Entities;
using NAudio.Wave;
using SoundFingerprinting;
using SoundFingerprinting.Audio;
using SoundFingerprinting.Audio.NAudio;
using SoundFingerprinting.Builder;
using SoundFingerprinting.Configuration;
using SoundFingerprinting.DAO;
using SoundFingerprinting.DAO.Data;
using SoundFingerprinting.InMemory;

namespace Domain.FingerPrinting
{
    public class FingerPrintKeeper : IFingerPrintKeeper
    {
        private readonly IAudioService _audioService;
        private readonly IFingerprintCommandBuilder _fingerprintCommandBuilder;
        private readonly IModelService _modelService;
        private readonly Dictionary<int,IModelReference> hashedTracks;
        private readonly IQueryCommandBuilder _queryCommandBuilder;
        private readonly Dictionary<int, DateTime> _timekeeper; 


        private FingerprintConfiguration fingerprintConfiguration;
        private QueryConfiguration queryConfiguration;

        public FingerPrintKeeper()
        {
            _modelService = new InMemoryModelService();
            _audioService = new NAudioService();
            _fingerprintCommandBuilder = new FingerprintCommandBuilder();
            SetFingerPrintConfiguration();
            SetQueryConfiguration();
            hashedTracks = new Dictionary<int, IModelReference>();
            _queryCommandBuilder = new QueryCommandBuilder();
            _timekeeper = new Dictionary<int, DateTime>();
        }


        public void CreateHashes(string audiopath, Movie movie)
        {
            var reader = new MediaFoundationReader(audiopath);
            var track = new TrackData(
                "isrc " + movie.Id,
                 movie.Id.ToString(),
                "title " + movie.Name,
                "album " + movie.Id,
                DateTime.Today.Year,
                reader.TotalTime.TotalMilliseconds
                );
            var trackReference = _modelService.InsertTrack(track);
            hashedTracks.Add(movie.Id,trackReference);
            var hashedFingerprints = _fingerprintCommandBuilder
                .BuildFingerprintCommand()
                .From(audiopath).
                WithFingerprintConfig(fingerprintConfiguration)
                .UsingServices(_audioService)
                .Hash()
                .Result;
            _modelService.InsertHashDataForTrack(hashedFingerprints, trackReference);
        }

        public bool AudioHashExists(int id)
        {
            return hashedTracks.ContainsKey(id);
        }

        public void DeleteHashes(int id)
        {
            _modelService.DeleteTrack(hashedTracks[id]);
            hashedTracks.Remove(id);
        }

        public  double QueryWithTimeInformation(string snippetway , int movieId)
        {
            if (IfTimeExists(movieId))
                return GetMovieTime(movieId);
            var reader = new MediaFoundationReader(snippetway);
            var result = _queryCommandBuilder.BuildQueryCommand()
                   .From(snippetway).WithConfigs(fingerprintConfiguration, queryConfiguration)
                   .UsingServices(_modelService, _audioService)
                   .QueryWithTimeSequenceInformation().Result;
            if (!result.IsSuccessful)
            {
                throw new Exception("fragment not found ",new NullReferenceException());
            }
            var id = Convert.ToInt32(result.BestMatch.Track.Artist);
            _timekeeper.Add(id,DateTime.Now);
            return result.TimeInfo.Start+reader.TotalTime.TotalSeconds;
        }

        public bool IfTimeExists(int movieId)
        {
            return _timekeeper.ContainsKey(movieId);
        }

        public double GetMovieTime(int movieId)
        {
            var syncTime = _timekeeper[movieId];
            return (DateTime.Now - syncTime).TotalSeconds;
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