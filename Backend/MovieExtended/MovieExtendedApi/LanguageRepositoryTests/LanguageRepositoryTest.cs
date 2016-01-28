using System;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
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
            using (var session = provider.GetCurrentSession())
            {
                var languageRepository = new LanguageRepository(session);
                languageRepository.SaveLanguage(language);
            }
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
            using (var session = provider.GetCurrentSession())
            {
                var languageRepository = new LanguageRepository(session);
                var allLanguages = languageRepository.GetAllLanguages();
            }
        }

        [TestMethod]
        public void GetLanguageByName()
        {
            var provider = new SessionProvider();

            provider.OpenSession();

            using (var session = provider.GetCurrentSession())
            {
                var languageRepository = new LanguageRepository(session);
                var languageByName = languageRepository.GetLanguageByName("name");
            }
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
            using (var session = provider.GetCurrentSession())
            {
                var languageRepository = new LanguageRepository(session);
                var allLanguages = languageRepository.GetAllLanguages();
            }
        }
    }
}
