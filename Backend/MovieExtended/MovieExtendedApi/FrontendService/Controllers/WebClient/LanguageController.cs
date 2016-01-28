using System;
using System.Collections.Generic;
using System.Web.Http;
using Domain.Models;
using Domain.VisitorRepository;
using Extended_Movie.Visitor_Repository;
using Infrastructure.VisitorRepository;
using Newtonsoft.Json;

namespace FrontendService.Controllers.WebClient
{
    public class LanguageController : ApiController
    {
        private readonly ILanguageRepository languageRepository;

        public LanguageController(ILanguageRepository languageRepository)
        {
            this.languageRepository = languageRepository;
        }

        [Route("api/Languages/DeleteByMovie/{movieId}")]
        [HttpGet]

        public void DeleteMovieFromLanguageById(int movieId)
        {
            languageRepository.DeleteLanguageByMovieId(movieId);
        }

        [Route("api/Languages/DeleteByLang/{languageId}")]
        [HttpGet]

        public void DeleteLanguageByLanguageId(int languageId)
        {
            languageRepository.DeleteLanguageByLanguageId(languageId);
        }

        [Route("api/Languages/All")]
        [HttpGet]

        public IEnumerable<Language> GetAllLanguages()
        {
           return languageRepository.GetAllLanguages();
        }

        [Route("api/Languages/New/{Json}")]
        [HttpGet]

        public void SaveNewLanguageToDataBase(string json)
        {
            var newLanguage = JsonConvert.DeserializeObject<Language>(json);
            languageRepository.SaveLanguage(newLanguage);

        }

    }
}