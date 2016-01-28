using Domain.Models;
using Infrastructure.VisitorRepository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

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
            
            var cinemaName = "name1";
            var cinemaAddress = "address";
            var companyId = 232;
            var cinema = new Cinema( cinemaName, cinemaAddress, companyId);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var cinemaRepository = new CinemaRepository(session);
                cinemaRepository.SaveCinemaData(cinema);
                var test =cinemaRepository.GetCinemaByCinemaId(232);
            }

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
            using (var session = provider.GetCurrentSession())
            {
                var cinemaRepository = new CinemaRepository(session);
                cinemaRepository.SaveCinemaData(cinema);
                var test =cinemaRepository.GetCinemaByCompanyId(89);
                
            }

        }
    }
}
