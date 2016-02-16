using System;

namespace Domain.Models.Entities
{
    public class Session
    {
        public Session(Guid sessionId, int movieId)
        {
            SessionId = sessionId;
            MovieId = movieId;
        }

        public Guid SessionId { get; private set; }
        public int MovieId { get; private set; }
        public SessionState SessionState { get; set; }
    }

    public enum SessionState
    {
        Ready,
        Active,
        Closed
    }
}