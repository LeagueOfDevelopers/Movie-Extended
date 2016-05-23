using System;

namespace Domain.Models.Entities
{
    public class Session
    {
        public Session(Guid sessionId, int movieId,DateTime creationTime , double lifetime)
        {
            SessionId = sessionId;
            MovieId = movieId;
            CreationTime = creationTime;
            Lifetime = lifetime;
        }

        public Guid SessionId { get; private set; }
        public int MovieId { get; private set; }
        public SessionState SessionState { get; set; }
        public DateTime CreationTime { get; private set; }
        public double Lifetime { get; private set; }
    }

    public enum SessionState
    {
        Ready,
        Active,
        Closed
    }
}