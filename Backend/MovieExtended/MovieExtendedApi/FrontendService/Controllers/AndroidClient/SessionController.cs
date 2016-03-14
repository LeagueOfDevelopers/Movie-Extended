using System;
using System.Net;
using System.Web.Http;
using Domain.mapper;
using Domain.Models;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;
using Domain.Repository;
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
        public FrontendMovie Login([FromBody] string qr)
        {
            var qrGuid = new Guid(qr);
            var necessaryMovie = _movieRepository.CheckAndroidToken(qrGuid);
            
            var newSessionGuid = Guid.NewGuid();
            if (necessaryMovie != null)
            {
                var frontendMovie = new MovieMapper().ToFrontendMovie(necessaryMovie);
                frontendMovie.AndroidToken = newSessionGuid;
                var newSession = new Session(newSessionGuid, necessaryMovie.Id);
                _keeper.CreateSession(newSession);
                return frontendMovie;
            }
            throw new HttpResponseException(HttpStatusCode.Unauthorized);
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
        [Route("time/{movieId}")]
        [HttpGet]
        public void SetMovieStartTime( int movieId)
        {
            _keeper.SetMovieTime(movieId,DateTime.Now);
            
        }

        [Route("time/start/{movieId}")]
        [HttpGet]
        public DateTime GetMovieStartTime(int movieId)
        {
            return _keeper.GetMovieStartTime(movieId);
        }

        [Route("time/current/{movieId}")]
        [HttpGet]
        public TimeSpan GetCurrentMovieTime(int movieId)
        {
            return _keeper.GetCurrentMovieTime(movieId);
        }


        
    
    }
}