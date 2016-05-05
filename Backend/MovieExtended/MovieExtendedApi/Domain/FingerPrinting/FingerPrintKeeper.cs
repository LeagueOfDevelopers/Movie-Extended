using System;
using System.IO;
using Domain.Models.Entities;
using NAudio.Wave;
using NHibernate.Criterion;
using SoundFingerprinting;
using SoundFingerprinting.Audio;
using SoundFingerprinting.Audio.NAudio;
using SoundFingerprinting.Builder;
using SoundFingerprinting.Configuration;
using SoundFingerprinting.DAO.Data;
using SoundFingerprinting.InMemory;

namespace Domain.FingerPrinting
{
  public  class FingerPrintKeeper:IFingerPrintKeeper
    {
      public FingerPrintKeeper()
      {
          _modelService = new InMemoryModelService();
          _audioService = new NAudioService();
            _fingerprintCommandBuilder = new FingerprintCommandBuilder();
            SetFingerPrintConfiguration();
            SetQueryConfiguration();
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

      

      private  FingerprintConfiguration fingerprintConfiguration;
      private  QueryConfiguration queryConfiguration;
      private IModelService _modelService;
      private IAudioService _audioService;
      private IFingerprintCommandBuilder _fingerprintCommandBuilder;


      public void CreateHashes(string audiopath,Movie movie)
      {
            MediaFoundationReader reader = new MediaFoundationReader(audiopath);
            var track = new TrackData(
                "isrc"+movie.Id,
                "artist"+movie.Id,
                "title"+movie.Id,
                "album"+movie.Id,
                DateTime.Today.Year,
                reader.TotalTime.TotalMilliseconds
                );

      }

      
    }
}
