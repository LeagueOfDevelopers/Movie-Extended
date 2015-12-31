﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using Extended_Movie.Models;
using Extended_Movie.Visitor_Repository;
using Newtonsoft.Json;

namespace Extended_Movie.Controllers.WebClient
{
    public class LanguageController : ApiController
    {
        private readonly LanguageRepository languageRepository;

        public LanguageController(LanguageRepository languageRepository)
        {
            this.languageRepository = languageRepository;
        }

        [Route("api/Languages/DeleteByMovie/{movieId}")]
        [HttpGet]

        public void DeleteMovieFromLanguageById(Guid movieId)
        {
            languageRepository.DeleteLanguageByMovieId(movieId);
        }

        [Route("api/Languages/DeleteByLang/{languageId}")]
        [HttpGet]

        public void DeleteLanguageByLanguageId(Guid? languageId)
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