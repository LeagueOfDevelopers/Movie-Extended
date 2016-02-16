using System;
using System.Collections.Generic;
using System.Net;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;
using Domain.VisitorRepository;
using Journalist;

namespace FrontendService.Controllers.WebClient
{
    public class LanguageController : ApiController
    {
        private readonly ILanguageRepository _languageRepository;
        private readonly ISessionKeeper _keeper;

        public LanguageController(ILanguageRepository languageRepository, ISessionKeeper keeper)
        {
            Require.NotNull(languageRepository,nameof(ILanguageRepository));
            _languageRepository = languageRepository;
            Require.NotNull(keeper , nameof(ISessionKeeper));
            _keeper = keeper;
        }

        [Route("api/Languages/DeleteByMovie/{movieId}")]
        [HttpPost]
        public void DeleteMovieFromLanguageById(int movieId)
        {
            _languageRepository.DeleteLanguageByMovieId(movieId);
        }

        [Route("api/Languages/DeleteByLang/{languageId}")]
        [HttpPost]
        public void DeleteLanguageByLanguageId(int languageId)
        {
            _languageRepository.DeleteLanguageByLanguageId(languageId);
        }

        [Route("api/Languages/All")]
        [HttpGet]
        public IEnumerable<FrontendLanguage> GetAllLanguages()
        {
           return _languageRepository.GetAllLanguages();
        }

        [Route("api/Languages/New")]
        [HttpPost]
        public void SaveNewLanguageToDataBase([FromBody] Language newLanguage)
        {
            _languageRepository.SaveLanguage(newLanguage);

        }

        [Route("api/Languages/GetMovie")]
        [HttpPost]
        public IEnumerable<AndroidLanguage> GetLanguagesByMovieId([FromBody]string session)
        {
            var sessionId = new Guid(session);
            if (_keeper.CheckIfSessionExists(sessionId)&& _keeper.GetSessionState(sessionId)==SessionState.Active)
            return _languageRepository.GetLanguagesByMovieId(_keeper.GetMovieId(sessionId));
            else throw new HttpResponseException(HttpStatusCode.Unauthorized); 
        }

        [Route("api/Languages/{movieId}")]
        [HttpGet]
        public IEnumerable<AndroidLanguage> GetLanguagesByMovieId(int movieId)
        {
                return _languageRepository.GetLanguagesByMovieId(movieId);
        }

    }
}