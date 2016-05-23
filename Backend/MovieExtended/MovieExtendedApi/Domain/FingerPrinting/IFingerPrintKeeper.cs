using System;
using Domain.Models.Entities;

namespace Domain.FingerPrinting
{
   public interface IFingerPrintKeeper
    {
       double CreateHashesAndGetMovieDurationTime(string audiopath,Movie movie); // creates hashes and return movie duration time 
       bool AudioHashExists(int id);
       void DeleteHashes(int id);
       double QueryWithTimeInformation(string snippetway , int movieId);
       bool IfTimeExists(int movieId);
       double GetMovieTime(int movieId);
    }
}
