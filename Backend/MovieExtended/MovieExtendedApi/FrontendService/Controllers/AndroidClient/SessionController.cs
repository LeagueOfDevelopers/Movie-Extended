using System;
using System.Net;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.VisitorRepository;
using Journalist;

namespace FrontendService.Controllers.AndroidClient
{
    public class SessionController : ApiController
    {
        private readonly ISessionKeeper _keeper;
        private readonly IMovieRepository _movieRepository;

        public SessionController(IMovieRepository movieRepository, ISessionKeeper keeper)
        {
            Require.NotNull(keeper, nameof(ISessionKeeper));
            _keeper = keeper;
            Require.NotNull(movieRepository, nameof(IMovieRepository));
            _movieRepository = movieRepository;
        }

        [Route("api/Session/Login")]
        [HttpPost]
        public Guid Login([FromBody] string qr)

        {
            var qrGuid = new Guid(qr);
            var necessaryMovie = _movieRepository.CheckAndroidToken(qrGuid);
            if (necessaryMovie != null)
            {
                var newSession = new Session(Guid.NewGuid(), necessaryMovie.Id);
                return _keeper.CreateSession(newSession);
            }
            throw new HttpResponseException(HttpStatusCode.Unauthorized);
            ;
        }

        //[Route("api/Session/{sessionId}/StartTime")]
        //[HttpGet]
        //public string GetMovieStartTime(Guid sessionId)
        //{
        //    if (!_keeper.CheckIfSessionExists(sessionId))
        //    {
        //        throw new HttpResponseException(HttpStatusCode.Unauthorized);
        //    }
        //    if (_keeper.GetSessionState(sessionId) != SessionState.Active)
        //    {
        //        throw new HttpResponseException(HttpStatusCode.NotAcceptable);
        //    }
        //    var datetime = _keeper.GetMovieStartTime(sessionId);
        //    return ConvertToUnixTimestamp(datetime).ToString();
        //}

        //private static double ConvertToUnixTimestamp(DateTime date)
        //{
        //    var origin = new DateTime(1970, 1, 1, 0, 0, 0, 0);
        //    var diff = date - origin;
        //    return diff.TotalMilliseconds;
        //}
        [Route("api/SetTime")]
        [HttpPut]
        public void SetMovieStartTime([FromBody] int movieId, [FromBody] DateTime movieStartTime)
        {
            _keeper.SetMovieTime(movieId,movieStartTime);
        }

        [Route("api/GetTime/{movieId}")]
        [HttpGet]
        public DateTime GetMovieStartTime(int movieId)
        {
            return _keeper.GetMovieStartTime(movieId);
        }

        
    
    }
}