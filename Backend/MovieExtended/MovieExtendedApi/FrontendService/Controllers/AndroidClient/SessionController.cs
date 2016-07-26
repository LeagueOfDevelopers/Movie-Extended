using System;
using System.Net;
using System.Web.Http;
using Domain.FingerPrinting;
using Domain.mapper;
using Domain.Models;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;
using Domain.Repository;
using Journalist;

namespace FrontendService.Controllers.AndroidClient
{
    [AllowAnonymous]
    public class SessionController : ApiController
    {
        
        private readonly ISessionKeeper _keeper;
        private readonly IMovieRepository _movieRepository;
        private readonly IFingerPrintKeeper _fingerPrintKeeper;

        public SessionController(IMovieRepository movieRepository, ISessionKeeper keeper,IFingerPrintKeeper fingerPrintKeeper)
        {
            Require.NotNull(keeper, nameof(ISessionKeeper));
            _keeper = keeper;
            Require.NotNull(movieRepository, nameof(IMovieRepository));
            _movieRepository = movieRepository;
            Require.NotNull(fingerPrintKeeper,nameof(IFingerPrintKeeper));
            _fingerPrintKeeper = fingerPrintKeeper;
        }

        [Route("token")]
        [HttpPost]
        public FrontendMovie Login([FromBody] string qr)
        {
            var qrGuid = new Guid(qr);
            var movie = _movieRepository.CheckAndroidToken(qrGuid);
            
            var token = Guid.NewGuid();
            double lifetime;
            if (movie != null)
            {
                if (!_fingerPrintKeeper.AudioHashExists(movie.Id))
                {
                    var path =
                        movie.RussianTrack 
                            .FilePath;
                    lifetime = _fingerPrintKeeper.CreateHashesAndGetMovieDurationTime(path,movie);
                    var session = new Session(token, movie.Id , DateTime.Now,lifetime);
                    _keeper.CreateSession(session);
                }
                var frontendMovie = new MovieMapper().ToFrontendMovie(movie); // сделать получше
                frontendMovie.AndroidToken = token;
                
                if ((DateTime.Now - _keeper.lastcheckTime).Hours==10)
                {
                    _keeper.DeleteOldSessions();
                }
                return frontendMovie;
            }
            throw new HttpResponseException(HttpStatusCode.Unauthorized);
        }

        [Route("time/{movieId}")]
        [HttpPost]
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

        [Route("clear/session/all")]
        [HttpGet]
        public void DeleteSessions()
        {
            _keeper.ClearSessionsAndTimes();
        }
    
    }
}