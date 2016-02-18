using System;
using System.Collections.Generic;
using System.Linq;
using Domain.Models.Entities;

namespace Domain.Models
{
    public class SessionKeeper : ISessionKeeper
    {
        private readonly List<Session> _sessions;
        private readonly Dictionary<int,DateTime> _movieStartTime; 

        public SessionKeeper()
        {
            _sessions = new List<Session>();
            _movieStartTime = new Dictionary<int, DateTime>();
        }

        public Guid CreateSession(Session session)
        {
            //var session = new Session(Guid.NewGuid());
            //if (_timeMarks.ContainsKey(movieId))
            //{
            //    session.SessionState = SessionState.Active;
            //}

            //_sessions.Add(session);
            //return session.SessionId;
            session.SessionState = SessionState.Ready;
            _sessions.Add(session);
            return session.SessionId;
        }

        public bool CheckIfSessionExists(Guid sessionId)
        {
            return _sessions.Any(session => session.SessionId == sessionId);
        }

        public SessionState GetSessionState(Guid sessionId)
        {
            var session = _sessions.FirstOrDefault(innerSession => innerSession.SessionId == sessionId);
            return session == null ? SessionState.Closed : session.SessionState;
        }

        public int GetMovieId(Guid sessionId)
        {
            var session = _sessions.FirstOrDefault(innerSession => innerSession.SessionId == sessionId);
            if (session == null) throw new ArgumentNullException();
            return session.MovieId;
        }

        //        _sessions.RemoveAll(session => session.MovieId == movieId);
        //    {
        //    if (state == SessionState.Closed)
        //{

        //public void SetState(Guid movieId, SessionState state, DateTime changingOccured)
        // private readonly Dictionary<Guid, DateTime> _timeMarks = new Dictionary<Guid, DateTime>();
        //        _timeMarks.Remove(movieId);
        //    }
        //    if (state == SessionState.Active)
        //    {
        //        _timeMarks.Add(movieId, changingOccured);
        //        foreach (var session in _sessions.Where(s => s.MovieId == movieId))
        //        {
        //            session.SessionState = state;
        //        }
        //    }
        //}

        //public DateTime GetMovieStartTime(Guid sessionId)
        //{
        //    var movieId = GetMovieId(sessionId);
        //    return _timeMarks[movieId];
        //}
        public void SetMovieTime(int movieId, DateTime movieStartTime)
        {
            _movieStartTime.Add(movieId,movieStartTime);
        }

        public DateTime GetMovieStartTime(int movieId)
        {
            var startTime = _movieStartTime.SingleOrDefault(pair => pair.Key == movieId);
            return startTime.Value;
        }
    }
}