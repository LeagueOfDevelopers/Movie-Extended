using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Extended_Movie.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Controllers.AndroidClient
{
    public class SessionController : ApiController
    {
        private readonly SessionKeeper _keeper;
        private readonly ISession _session;

        public SessionController(SessionKeeper keeper , ISession session)
        {
            _keeper = keeper;
            _session = session;
        }

        [Route("api/Session/Login/qr")]
        [HttpGet]

        public Guid CreateNewSessionByQrCode(string qr)
        {
            var sessionId = new Guid(qr);
            _keeper.CreateSession(sessionId);
            return sessionId;
        }
        [Route("api/Sessions/{sessionId}/StartTime")]
        [HttpGet]
        public string GetMovieStartTime(Guid sessionId)
        {
            if (!_sessionKeeper.CheckIfSessionExists(sessionId))
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
            if (_sessionKeeper.GetSessionState(sessionId) != SessionState.Active)
            {
                throw new HttpResponseException(HttpStatusCode.NotAcceptable);
            }
            var datetime = _sessionKeeper.GetMovieStartTime(sessionId);
            return ConvertToUnixTimestamp(datetime).ToString();
        }

        private static double ConvertToUnixTimestamp(DateTime date)
        {
            DateTime origin = new DateTime(1970, 1, 1, 0, 0, 0, 0);
            TimeSpan diff = date - origin;
            return diff.TotalMilliseconds;
        }
    }
}
