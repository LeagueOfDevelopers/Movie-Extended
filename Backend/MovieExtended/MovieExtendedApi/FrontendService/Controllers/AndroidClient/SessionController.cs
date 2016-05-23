﻿using System;
using System.Net;
using System.Web.Http;
using Domain.FingerPrinting;
using Domain.mapper;
using Domain.Models;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;
using Domain.Repository;
using Journalist;
using NHibernate.Mapping;

namespace FrontendService.Controllers.AndroidClient
{
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

        [Route("login")]
        [HttpPost]
        public FrontendMovie Login([FromBody] string qr)
        {
            var qrGuid = new Guid(qr);
            var necessaryMovie = _movieRepository.CheckAndroidToken(qrGuid);
            
            var newSessionGuid = Guid.NewGuid();
            if (necessaryMovie != null)
            {
                if (!_fingerPrintKeeper.AudioHashExists(necessaryMovie.Id))
                {
                    var path =
                        necessaryMovie.RussianTrack 
                            .FilePath;
                    _fingerPrintKeeper.CreateHashes(path,necessaryMovie);
                }
                var frontendMovie = new MovieMapper().ToFrontendMovie(necessaryMovie); // сделать получше
                frontendMovie.AndroidToken = newSessionGuid;
                var newSession = new Session(newSessionGuid, necessaryMovie.Id , DateTime.Now);
                _keeper.CreateSession(newSession);
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