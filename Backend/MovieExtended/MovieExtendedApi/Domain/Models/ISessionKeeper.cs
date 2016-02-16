using System;
using Domain.Models.Entities;

namespace Domain.Models
{
     public interface ISessionKeeper
    {
        Guid CreateSession(Session session);
        bool CheckIfSessionExists(Guid sessionId);
        SessionState GetSessionState(Guid sessionId);
         int GetMovieId(Guid sessionId);
    }
}
