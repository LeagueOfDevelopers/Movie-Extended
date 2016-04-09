using Infrastructure;
using Infrastructure.Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace LanguageRepositoryTests
{
    [TestClass]
    public class LanguageRepositoryTest
    {
        //[TestMethod]
        //public void SaveLanguage()
        //{
        //    var file = new File(0, "testRepository", FileType.Track);
        //    var company = new Company(0, "LOD_Company", new Uri("https://vk.com/holymosh"));
        //    var cinema = new Cinema(0, "Rozoviy_Korpus", "G-588", company);
        //    var movie = new Movie(0, "10 razrabov Vitalika", cinema);
        //    movie.AndroidToken = Guid.NewGuid();
        //    var language = new Language(0, "kazahskiy", movie, file);

        //    var provider = new SessionProvider();

        //    provider.OpenSession();

        //    var languageRepository = new LanguageRepository(provider);
        //    languageRepository.SaveLanguage(language);
        //}

        [TestMethod]
        public void GetAllLanguages()
        {
            //var languageName = "name";
            //var movieId = 7;
            //var trackfieldId = 7;
            //var language = new Language( languageName, movieId, trackfieldId);
            var provider = new SessionProvider();
            provider.OpenSession();

            var languageRepository = new LanguageRepository(provider);
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
            //var languageName = "name";
            //var movieId = 8;
            //var trackfieldId = 8;
            //var language = new Language( languageName, movieId, trackfieldId);
            var provider = new SessionProvider();
            provider.OpenSession();

            var languageRepository = new LanguageRepository(provider);
        }
    }
}