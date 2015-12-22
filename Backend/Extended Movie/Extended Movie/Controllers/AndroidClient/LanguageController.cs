using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;
using System.Web.Http;
using NHibernate;
using NHibernate.Linq;
using Extended_Movie.Models;

namespace Extended_Movie.Controllers.AndroidClient
{
    public class LanguageController : ApiController

    {
        private readonly ISession _session;
        private readonly SessionKeeper _keeper;

        public LanguageController(ISession session , SessionKeeper keeper)
        {
            _session = session;
            _keeper = keeper;
        }

        [Route("api/Sessions/{sessionId}/Languages")]
        [HttpGet]

        public IEnumerable<Language> GetLanguages(Guid sessionId)
        {
            if (!_keeper.CheckIfSessionExists(sessionId))
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }

            var movieId = _keeper.GetMovieId(sessionId);
            return _session.Query<Language>().Where(lang => lang.MovieId == movieId);
        }
    }
}