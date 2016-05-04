using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SoundFingerprinting;
using SoundFingerprinting.Audio;
using SoundFingerprinting.Audio.NAudio;
using SoundFingerprinting.Builder;
using SoundFingerprinting.Configuration;
using SoundFingerprinting.InMemory;

namespace Domain
{
  public  class FingerPrintKeeper
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

      private void CreateHashes(string audiopath )
      {
          
      }

      private  FingerprintConfiguration fingerprintConfiguration;
      private  QueryConfiguration queryConfiguration;
      private IModelService _modelService;
      private IAudioService _audioService;
      private IFingerprintCommandBuilder _fingerprintCommandBuilder;


    }
}
