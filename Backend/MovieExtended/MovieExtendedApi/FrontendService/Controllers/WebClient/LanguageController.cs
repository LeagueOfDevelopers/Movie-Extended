using System;
using System.Collections.Generic;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.VisitorRepository;
using Infrastructure.VisitorRepository;
using Newtonsoft.Json;

namespace FrontendService.Controllers.WebClient
{
    public class LanguageController : ApiController
    {
        private readonly ILanguageRepository _languageRepository;

        public LanguageController(ILanguageRepository languageRepository)
        {
            _languageRepository = languageRepository;
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

        [Route("api/Languages/New/{Json}")]
        [HttpPost]
        public void SaveNewLanguageToDataBase(string json)
        {
            var newLanguage = JsonConvert.DeserializeObject<Language>(json);
            _languageRepository.SaveLanguage(newLanguage);

        }

        [Route("api/Languages/MovieId/{movieId}")]
        [HttpGet]
        public IEnumerable<Language> GetLanguagesByMovieId(int movieId)
        {
            return _languageRepository.GetLanguagesByMovieId(movieId);
        } 

    }
}