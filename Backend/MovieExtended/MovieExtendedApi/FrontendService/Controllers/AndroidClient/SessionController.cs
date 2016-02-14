﻿using Domain.Models;
using System;
using System.Net;
using System.Web;
using System.Web.Http;
using Domain.Models.Entities;
using Domain.VisitorRepository;

namespace FrontendService.Controllers.AndroidClient
{
    public class SessionController : ApiController 
    {
        private readonly SessionKeeper _keeper;
        private readonly IMovieRepository _movieRepository;

        public SessionController(IMovieRepository movieRepository)
        {
            _keeper = new SessionKeeper();
            _movieRepository = movieRepository;
        }

        [Route("api/Session/Login/{qr}")]
        [HttpPost]
        public Guid Login(Guid qr)
        {
            var necessaryMovie = _movieRepository.CheckAndroidToken(qr);
            if (necessaryMovie !=null)
            {
                var newSession = new Session(Guid.NewGuid(),necessaryMovie.Id);
                return _keeper.CreateSession(newSession);
            }
            else throw new HttpResponseException(HttpStatusCode.Unauthorized); ;
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

        private static double ConvertToUnixTimestamp(DateTime date)
        {
            DateTime origin = new DateTime(1970, 1, 1, 0, 0, 0, 0);
            TimeSpan diff = date - origin;
            return diff.TotalMilliseconds;
        }
    }
}
