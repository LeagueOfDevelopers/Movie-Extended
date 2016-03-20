using System;
using Domain.Models.Entities;

namespace Domain.Models
{
    public interface ISessionKeeper
    {
        void CreateSession(Session session);
        bool CheckIfSessionExists(Guid sessionId);
        SessionState GetSessionState(Guid sessionId);
        int GetMovieId(Guid sessionId);
        void SetMovieTime(int movieId, DateTime movieStartTime);
        DateTime GetMovieStartTime(int movieId);
        TimeSpan GetCurrentMovieTime(int movieId);
        void ClearSessionsAndTimes();
    }
}