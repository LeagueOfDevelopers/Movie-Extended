using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;


namespace Extended_Movie.Models
{
    public class Session
    {
        public Session(Guid sessionId, Guid movieId)
        {
            SessionId = sessionId;
            MovieId = movieId;
        }

        public Guid SessionId { get; private set; }
        public Guid MovieId { get; private set; }
        public SessionState SessionState { get; set; }
    }
    public enum SessionState
    {
        Ready,
        Active,
        Closed
    }
}