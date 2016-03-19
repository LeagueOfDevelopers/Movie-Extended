using System;
using System.Collections.Generic;
using System.Linq;
using Domain.Models.Entities;
using Infrastructure;
using Infrastructure.Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using NHibernate.Util;

namespace UnitTestProject2
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void save()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            var companyRepo = new CompanyRepository(provider);
            var file1 = new File("Russian1track",FileType.Track);
            var file2 = new File("Russian1subtitles",FileType.Subtitles);
            var file3 = new File("EnglishTrack",FileType.Track);
            var file4 = new File("SubtitlesEnglish" , FileType.Subtitles);
            var language1 = new Language("russian", file1, file2);
            var language2 = new Language("english", file3, file4);
            var langset = new HashSet<Language>();
            langset.Add(language1);
            langset.Add(language2);
            var poster= new File("Poster",FileType.Poster);
            var movie1= new Movie("movie1",langset,poster);
            var movieset = new HashSet<Movie>();
            movieset.Add(movie1);
            var cinema_1= new Cinema("cinema_1","address",movieset);
            var cinemaset = new HashSet<Cinema>();
            cinemaset.Add(cinema_1);
            var company = new Company("company",new Uri("https://vk.com/audios141213476?performer=1&q=Summer%20Of%20Haze"),cinemaset );
            companyRepo.SaveCompany(company);
            //var session = provider.GetCurrentSession();
            //session.Transaction.Begin();
            //session.Save(company);
            //session.Transaction.Commit();
        }

        [TestMethod]
        public void testCompanyRepository()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            var langRepo = new LanguageRepository(provider);
            var lang = langRepo.GetLanguageById(2);
            lang.TrackFile.FilePath = "2idTrack";
            lang.Subtitles.FilePath = "2idSubtitle";
            
            //lang.Subtitles = new File("~/Subtitles/4.srt",FileType.Subtitles);
            langRepo.UpdateLanguage(lang);

        }

        [TestMethod]
        public void getMovie()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            var movieRepo = new MovieRepository(provider);
            var film = movieRepo.CheckAndroidToken(new Guid("00000000-0000-0000-0000-000000000000"));
            film.Poster.FilePath = film.Poster.FilePath;
        }
    }
}
            


