using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Domain.Models;
using Domain.Models.Entities;
using Infrastructure.VisitorRepository;

namespace UnitTestProject1
{
    [TestClass]
    public class CinemaTestClass
    {
        [TestMethod]
        public void SaveCinema()
        {
            var cinemaName = "name";
            var cinemaAddress = "address";
            var companyId = 342;
            var cinema = new Cinema(cinemaName,cinemaAddress,companyId);
            var provider = new SessionProvider();
            provider.OpenSession();
           
            var cinemaRepository = new CinemaRepository(provider);
            cinemaRepository.SaveCinemaData(cinema);
        }

        [TestMethod]
        public void GetAll()
        {
            var provider = new SessionProvider();
            provider.OpenSession();
            
            var cinemaRepository = new CinemaRepository(provider);
            var allCinemas = cinemaRepository.GetAllCinemas();
        }

       

        [TestMethod]
        public void GetCinemaByCinemaId()
        {
            var cinemaName = "name1";
            var cinemaAddress = "address";
            var companyId = 232;
            var cinema = new Cinema( cinemaName, cinemaAddress, companyId);
            var provider = new SessionProvider();
            provider.OpenSession();
            
            var cinemaRepository = new CinemaRepository(provider);
            cinemaRepository.SaveCinemaData(cinema);
            var test =cinemaRepository.GetCinemaByCinemaId(232);
            }

        [TestMethod]
        public void GetCinemaByCompanyId()
        {
            var cinemaName = "name1";
            var cinemaAddress = "address";
            var companyId = 89;
            var cinema = new Cinema(cinemaName, cinemaAddress, companyId);
            var provider = new SessionProvider();
            provider.OpenSession();
            
            var cinemaRepository = new CinemaRepository(provider);
            cinemaRepository.SaveCinemaData(cinema);
            var test =cinemaRepository.GetCinemaByCompanyId(89);
        }
    }
}
