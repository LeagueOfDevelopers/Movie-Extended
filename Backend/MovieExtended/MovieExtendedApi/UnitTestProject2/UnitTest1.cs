using System;
using System.Collections.Generic;
using System.Diagnostics.PerformanceData;
using System.Reflection;
using Domain.Models.Entities;
using Infrastructure.Repository;
using Infrastructure.VisitorRepository;
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
            var fileRepo = new FileRepository(provider);
            var language = new Language("english",new File( "~/AudioTrack/7.mp3",0), fileRepo.GetFileData(4));
            ISet<Language> languages = new HashSet<Language>();
            languages.Add(language);
            var labnguage2 = new Language("russian" , fileRepo.GetFileData(10),fileRepo.GetFileData(5));
            languages.Add(labnguage2);
            var movie = new Movie("test",languages);
            movie.AndroidToken= Guid.NewGuid();
            movie.Poster = fileRepo.GetFileData(2);
            var lang3 = new Language("japan",fileRepo.GetFileData(1),fileRepo.GetFileData(6));
            var lang4 = new Language("bashkirsky",fileRepo.GetFileData(10),fileRepo.GetFileData(7));
            ISet<Language> languages2 = new HashSet<Language>();
            languages2.Add(lang3);
            languages2.Add(lang4);
            var movie2 = new Movie("test2",languages2);
            movie2.AndroidToken = Guid.NewGuid();
            movie2.Poster = fileRepo.GetFileData(3);
            ISet<Movie> movies = new HashSet<Movie>();
            movies.Add(movie);
            movies.Add(movie2);
            var cinema = new Cinema("holaimos cinema","rileeva 55 64 " , movies);
            ISet<Cinema> cinemas = new HashSet<Cinema>();
            cinemas.Add(cinema);
            var company = new Company("holaimos company",new Uri("https://vk.com/im?sel=206421328"),cinemas );
            var companyRepo = new CompanyRepository(provider);
            
            companyRepo.SaveCompany(company);
        }
    }
}
            


