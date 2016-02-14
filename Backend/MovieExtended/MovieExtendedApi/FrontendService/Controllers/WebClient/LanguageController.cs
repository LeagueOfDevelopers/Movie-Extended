using System;
using System.Collections.Generic;
using System.Net;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.VisitorRepository;

namespace FrontendService.Controllers.WebClient
{
    public class LanguageController : ApiController
    {
        private readonly ILanguageRepository _languageRepository;
        private readonly ISessionKeeper _keeper;

        public LanguageController(ILanguageRepository languageRepository, ISessionKeeper keeper)
        {
            _languageRepository = languageRepository;
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
        public IEnumerable<Language> GetAllLanguages()
        {
           return _languageRepository.GetAllLanguages();
        }

        [Route("api/Languages/New")]
        [HttpPost]
        public void SaveNewLanguageToDataBase([FromBody] Language newLanguage)
        {
            _languageRepository.SaveLanguage(newLanguage);

        }

        [Route("api/Languages/MovieId/{movieId}/Session/{sessionId}")]
        [HttpGet]
        public IEnumerable<Language> GetLanguagesByMovieId(int movieId,Guid sessionId)
        {
            if(_keeper.CheckIfSessionExists(sessionId)&& _keeper.GetSessionState(sessionId)==SessionState.Active)
            return _languageRepository.GetLanguagesByMovieId(movieId);
            else throw new HttpResponseException(HttpStatusCode.Unauthorized); 
        } 

    }
}