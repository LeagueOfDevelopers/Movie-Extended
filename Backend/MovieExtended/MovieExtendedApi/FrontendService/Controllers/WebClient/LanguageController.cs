using System;
using System.Collections.Generic;
using System.Net;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.Repository;
using Journalist;

namespace FrontendService.Controllers.WebClient
{
    public class LanguageController : ApiController
    {
        private readonly ISessionKeeper _keeper;
        private readonly ILanguageRepository _languageRepository;

        public LanguageController(ILanguageRepository languageRepository, ISessionKeeper keeper)
        {
            Require.NotNull(languageRepository, nameof(ILanguageRepository));
            _languageRepository = languageRepository;
            Require.NotNull(keeper, nameof(ISessionKeeper));
            _keeper = keeper;
        }

        //[Route("languages/DeleteByMovie/{movieId}")]
        //[HttpPost]
        //public void DeleteMovieFromLanguageById(int movieId)
        //{
        //    _languageRepository.DeleteLanguageByMovieId(movieId);
        //}

        [Route("language/delete/{languageId}")]
        [HttpDelete]
        public void DeleteLanguageByLanguageId(int languageId)
        {
            _languageRepository.DeleteLanguageByLanguageId(languageId);
        }

        

        [Route("language/new/{movieId}")]
        [HttpPut]
        public void SaveNewLanguageToDataBase([FromBody] Language newLanguage)
        {
            _languageRepository.SaveLanguage(newLanguage);
        }

        [Route("language/get/movie")]
        [HttpPost]
        public ISet<Language> GetLanguagesByMovieId([FromBody] string session)
        {
            var sessionId = new Guid(session);
            if (_keeper.CheckIfSessionExists(sessionId) )
                return _languageRepository.GetLanguagesByMovieId(_keeper.GetMovieId(sessionId));
            throw new HttpResponseException(HttpStatusCode.Unauthorized);
        }

        [Route("language/all")]
        [HttpGet]
        public IEnumerable<Language> GetAllLanguages()
        {
            return _languageRepository.GetAllLanguages();
        }

        [Route("language/get/{languageId}")]
        [HttpGet]
        public Language GetLanguageById(int languageId)
        {
            return _languageRepository.GetLanguageById(languageId);
        }
    }
}