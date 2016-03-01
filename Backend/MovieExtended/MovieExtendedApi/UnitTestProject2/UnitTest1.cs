using System;
using System.Collections.Generic;
using System.Diagnostics.PerformanceData;
using System.Reflection;
using Domain.Models.Entities;
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
            var language = new Language("kazahsky", fileRepo.GetFileData(1));
            var languages = new HashSet<Language>();
            languages.Add(language);
            var movie = new Movie("movie", languages);
            var movies = new HashSet<Movie>();
            movies.Add(movie);
            var cinema = new Cinema("movie-extended", "g-588", movies);
            var cinemas = new HashSet<Cinema>();
            cinemas.Add(cinema);
            var company = new Company("holyConst", new Uri("https://vk.com/im?sel=206421328"), cinemas);
            var companyRepo = new CompanyRepository(provider);
            companyRepo.SaveCompany(company);
        }
    }
}
            


