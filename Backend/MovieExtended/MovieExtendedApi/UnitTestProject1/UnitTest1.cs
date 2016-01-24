﻿using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Domain.Models;
using Extended_Movie.Visitor_Repository;

namespace UnitTestProject1
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void SaveCinema()
        {
            var cinemaId = new Guid();
            var cinemaName = "name";
            var cinemaAddress = "address";
            var companyId = new Guid();
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
                Console.WriteLine(allCinemas.ToString());
            }
        }
    }
}
