using System;
using Domain.Models.Entities;

namespace Domain.FingerPrinting
{
   public interface IFingerPrintKeeper
    {
       void CreateHashes(string audiopath,Movie movie);
       bool AudioHashExists(int id);
       void DeleteHashes(int id);
       double QueryWithTimeInformation(string snippetway);
       bool IfTimeExists(int movieId);
       double GetMovieTime(int movieId);
    }
}
