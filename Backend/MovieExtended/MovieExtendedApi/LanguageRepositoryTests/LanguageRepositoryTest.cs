using System;
using Domain.Models;
using Domain.Models.Entities;
using Infrastructure.VisitorRepository;
using Microsoft.VisualStudio.TestTools.UnitTesting;


namespace LanguageRepositoryTests
{
    [TestClass]
    public class LanguageRepositoryTest
    {
        [TestMethod]
        public void SaveLanguage()
        {   
            var languageName = "name";
            var movieId = 7;
            var trackfieldId = 7;
            var language = new Language(languageName,movieId,trackfieldId);
            var provider = new SessionProvider();
            
            provider.OpenSession();
           
            var languageRepository = new LanguageRepository(provider);
            languageRepository.SaveLanguage(language);
        }

        [TestMethod]
        public void GetAllLanguages()
        {
            
            var languageName = "name";
            var movieId = 7;
            var trackfieldId = 7;
            var language = new Language( languageName, movieId, trackfieldId);
            var provider = new SessionProvider();
            provider.OpenSession();
           
            var languageRepository = new LanguageRepository(provider);
            var allLanguages = languageRepository.GetAllLanguages();
            
        }

        [TestMethod]
        public void GetLanguageByName()
        {
            var provider = new SessionProvider();

            provider.OpenSession();
            
            var languageRepository = new LanguageRepository(provider);
            var languageByName = languageRepository.GetLanguageByName("name");
            
        }

        [TestMethod]
        public void GetLanguageMovieid()
        {
            var languageName = "name";
            var movieId = 8;
            var trackfieldId = 8;
            var language = new Language( languageName, movieId, trackfieldId);
            var provider = new SessionProvider();
            provider.OpenSession();
           
            var languageRepository = new LanguageRepository(provider);
            var allLanguages = languageRepository.GetAllLanguages();
            
        }
    }
}
