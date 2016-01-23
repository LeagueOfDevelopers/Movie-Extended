using System;
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
            var cinemaRepository = new CinemaRepository();
            cinemaRepository.SaveCinemaData(cinema);
            var provider = new DatabaseSessionProvider();
            using (var session = provider.GetCurrentSession())
            {
                cinemaRepository.SaveCinemaData(cinema);
            }
        }
    }
}
