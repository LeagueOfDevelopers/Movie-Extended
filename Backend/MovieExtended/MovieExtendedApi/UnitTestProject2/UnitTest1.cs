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
            var file1 = new File("way",FileType.Track);
            file1.FileType=FileType.Track;
            var file2 = new File("way",FileType.Subtitles);
            file2.FileType=FileType.Subtitles;
            var language1 = new Language("russian", file1, file2);
            var language2 = new Language("english", new File(), new File());
            var langset = new HashSet<Language>();
            langset.Add(language1);
            langset.Add(language2);
            var movie1= new Movie("movie1",langset);
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
            var lang = langRepo.GetLanguageById(52);
            lang.TrackFile= new File("~/AudioTrack/7.mp3",FileType.Track);
            //lang.Subtitles = new File("~/Subtitles/4.srt",FileType.Subtitles);
            langRepo.UpdateLanguage(lang);



        }
    }
}
            


