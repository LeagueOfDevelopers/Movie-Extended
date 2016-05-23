using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using Domain.Models.Entities;
using NHibernate.Util;

namespace Infrastructure
{
    public class SessionKeeper : ISessionKeeper
    {
        private readonly ConcurrentBag<Session> _sessions;
        private readonly Dictionary<int,DateTime> _movieStartTime;
        public  DateTime lastcheckTime { get; set; }

        public SessionKeeper()
        {
            _sessions = new ConcurrentBag<Session>();
            _movieStartTime = new Dictionary<int, DateTime>();
            lastcheckTime = DateTime.Now;
        }

        public void CreateSession(Session session)
        {
            session.SessionState = SessionState.Ready;
            _sessions.Add(session);
            
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

        public void SetMovieTime(int movieId, DateTime movieStartTime)
        {
            _movieStartTime.Add(movieId,movieStartTime);
            foreach (var session in _sessions.Where(session => session.MovieId==movieId ))
            {
                    session.SessionState = SessionState.Active;
            }
        }

        public DateTime GetMovieStartTime(int movieId)
        {
            var startTime = _movieStartTime.SingleOrDefault(pair => pair.Key == movieId);
            return startTime.Value;
        }

        public TimeSpan GetCurrentMovieTime(int movieId)
        {

            return DateTime.Now-GetMovieStartTime(movieId);
        }

        public void ClearSessionsAndTimes()
        {
            foreach (var session in _sessions)
            {
                Session result = session;
                _sessions.TryTake(out result); // delete this shit
            }
            _movieStartTime.Clear();
        }

        public void DeleteOldSessions()
        {
            foreach (var session in _sessions)
            {
                if ((DateTime.Now - session.CreationTime).TotalSeconds > session.Lifetime)
                {
                    var deletesession = new Session(session.SessionId ,
                        session.MovieId , 
                        session.CreationTime , 
                        session.Lifetime);
                    _sessions.TryTake(out deletesession);
                }
            }
        }

    }
}