using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Domain.Models;
using Extended_Movie.Visitor_Repository;

namespace UnitTestProject1
{
    [TestClass]
    public class CinemaTestClass
    {
        [TestMethod]
        public void SaveCinema()
        {
            var cinemaId = "";
            var cinemaName = "name";
            var cinemaAddress = "address";
            var companyId = "id";
            var cinema = new Cinema(cinemaId,cinemaName,cinemaAddress,companyId);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var cinemaRepository = new CinemaRepository(session);
                cinemaRepository.SaveCinemaData(cinema);
            }
        }

        [TestMethod]
        public void GetAll()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var cinemaRepository = new CinemaRepository(session);
                var allCinemas = cinemaRepository.GetAllCinemas();
               
            }
        }

       

        [TestMethod]
        public void GetCinemaByCinemaId()
        {
            var cinemaId = "id";
            var cinemaName = "name1";
            var cinemaAddress = "address";
            var companyId = "dwd1w1m12m";
            var cinema = new Cinema(cinemaId, cinemaName, cinemaAddress, companyId);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var cinemaRepository = new CinemaRepository(session);
                cinemaRepository.SaveCinemaData(cinema);
                var test =cinemaRepository.GetCinemaByCinemaId(cinemaId);
            }

        }

        [TestMethod]
        public void GetCinemaByCompanyId()
        {
            var cinemaId = "id";
            var cinemaName = "name1";
            var cinemaAddress = "address";
            var companyId = "1nw1w";
            var cinema = new Cinema(cinemaId, cinemaName, cinemaAddress, companyId);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var cinemaRepository = new CinemaRepository(session);
                cinemaRepository.SaveCinemaData(cinema);
                var test =cinemaRepository.GetCinemaByCompanyId(companyId);
                
            }

        }
    }
}
