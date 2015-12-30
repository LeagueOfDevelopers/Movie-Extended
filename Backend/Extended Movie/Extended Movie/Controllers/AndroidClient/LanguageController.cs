using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;
using System.Web.Http;
using NHibernate;
using NHibernate.Linq;
using Extended_Movie.Models;
using Extended_Movie.Visitor_Repository;

namespace Extended_Movie.Controllers.AndroidClient
{
    public class LanguageController : ApiController

    {
        private readonly LanguageRepository languageRepository;
        private readonly SessionKeeper _keeper;

        public LanguageController( SessionKeeper keeper)
        {
            languageRepository = new LanguageRepository();
            _keeper = keeper;
        }
        

        [Route("api/Sessions/{sessionId}/Movie/{movieId}/Languages")]
        [HttpGet]

        public IEnumerable<Language> GetLanguagesByMovieId(Guid sessionId, Guid movieId)
        {
            if (!_keeper.CheckIfSessionExists(sessionId))
            {
                throw new HttpResponseException(HttpStatusCode.Unauthorized);
            }

            return languageRepository.GetLanguagesByMovieId(movieId);
        }
    }
}
