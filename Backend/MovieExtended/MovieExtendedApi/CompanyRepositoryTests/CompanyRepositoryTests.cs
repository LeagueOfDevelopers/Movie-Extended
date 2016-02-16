using System;
using Domain.Models.Entities;
using Infrastructure.VisitorRepository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CompanyRepositoryTests
{
    [TestClass]
    public class CompanyRepositoryTests
    {
        [TestMethod]
        public void SaveCompany()
        {
            var companyName = "company";
            var uri1 = new Uri("http://handynotes.ru/2009/09/uri-url-urn.html");

            var company = new Company(7, companyName, uri1);
            var provider = new SessionProvider();
            provider.OpenSession();

            var companyRepository = new CompanyRepository(provider);
            companyRepository.SaveCompany(company);
        }

        [TestMethod]
        public void SaveFileData()
        {
            var saveFile = new File(@"~/AudioTrack/77.mp3", FileType.Track);

            var provider = new SessionProvider();
            provider.OpenSession();

            var fileRepository = new FileRepository(provider);
            fileRepository.SaveFileData(saveFile);
        }

        [TestMethod]
        public void SaveLanguage()
        {
            var file = new File("testRepository", FileType.Track);
            var company = new Company("LOD_Company", new Uri("https://vk.com/holymosh"));
            var cinema = new Cinema("Rozoviy_Korpus", "G-588", company);
            var movie = new Movie("10 razrabov Vitalika", cinema);
            movie.AndroidToken = Guid.NewGuid();
            var language = new Language("kazahskiy", movie, file);

            var provider = new SessionProvider();

            provider.OpenSession();

            var languageRepository = new LanguageRepository(provider);
            languageRepository.SaveLanguage(language);
        }
    }
}