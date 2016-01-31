using Domain.Models;
using System;
using System.Net;
using System.Web.Http;

namespace FrontendService.Controllers.AndroidClient
{
    public class SessionController : ApiController 
    {
        private readonly SessionKeeper _keeper;
        public SessionController()
        {
            _keeper = new SessionKeeper();
        }

        [Route("api/Session/Login/{qr}")]
        [HttpGet]
        public Guid Login(string qr)
        {
            var sessionId = Guid.NewGuid();
            //_keeper.CreateSession(sessionId);
            return sessionId;
        }

        [Route("api/Session/{sessionId}/StartTime")]
        [HttpGet]
        public string GetMovieStartTime(Guid sessionId)
        {
            if (!_keeper.CheckIfSessionExists(sessionId))
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }
            if (_keeper.GetSessionState(sessionId) != SessionState.Active)
            {
                throw new HttpResponseException(HttpStatusCode.NotAcceptable);
            }
            var datetime = _keeper.GetMovieStartTime(sessionId);
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
