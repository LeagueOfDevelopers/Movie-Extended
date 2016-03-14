using System;
using System.Collections.Generic;
using System.Diagnostics.PerformanceData;
using System.Reflection;
using Domain.Models.Entities;
using Infrastructure;
using Infrastructure.Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

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
            var language = new Language("english",new File( "~/AudioTrack/7.mp3",FileType.Track), new File("~/Subtitles/4.srt",FileType.Subtitles));
            ISet<Language> languages = new HashSet<Language>();
            languages.Add(language);
            var labnguage2 = new Language("russian" , new File("~/AudioTrack/10.mp3",FileType.Track),new File("~/Subtitles/8.srt",FileType.Subtitles));
            languages.Add(labnguage2);
            var movie = new Movie("test test",languages);
            movie.AndroidToken= Guid.NewGuid();
            movie.Poster = new File("~/Posters/2.jpg",FileType.Poster);
            var lang3 = new Language("japan",new File("~/AudioTrack/10.mp3",FileType.Track), new File("~/Subtitles/8.srt", FileType.Subtitles));
            var lang4 = new Language("bashkirsky", new File("~/AudioTrack/7.mp3", FileType.Track), new File("~/Subtitles/4.srt", FileType.Subtitles));
            ISet<Language> languages2 = new HashSet<Language>();
            languages2.Add(lang3);
            languages2.Add(lang4);
            var movie2 = new Movie("testFilmNormal",languages2);
            movie2.AndroidToken = Guid.NewGuid();
            movie2.Poster = new File("~/Posters/10.jpg",FileType.Poster);
            ISet<Movie> movies = new HashSet<Movie>();
            movies.Add(movie);
            movies.Add(movie2);
            var cinema = new Cinema(11,"holaimos cinema","rileeva 55 64 " , movies);
            ISet<Cinema> cinemas = new HashSet<Cinema>();
            cinemas.Add(cinema);
            var company = new Company(10,"holaimos company",new Uri("https://vk.com/im?sel=206421328"),cinemas );
            var companyRepo = new CompanyRepository(provider);
            
            companyRepo.SaveCompany(company);
        }

        [TestMethod]
        public void testCompanyRepository()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            var companyRepo = new CompanyRepository(provider);
            var comp = companyRepo.GetCompanyByCompanyId(1);
            


        }
    }
}
            


