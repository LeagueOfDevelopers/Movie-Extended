using System;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;


namespace LanguageRepositoryTests
{
    [TestClass]
    public class LanguageRepositoryTest
    {
        [TestMethod]
        public void SaveLanguage()
        {
            var languageId = new Guid();
            var languageName = "name";
            var movieId = new Guid();
            var trackfieldId = new Guid();
            var language = new Language(languageId,languageName,movieId,trackfieldId);
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
            var languageId = new Guid();
            var languageName = "name";
            var movieId = new Guid();
            var trackfieldId = new Guid();
            var language = new Language(languageId, languageName, movieId, trackfieldId);
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
    }
}
