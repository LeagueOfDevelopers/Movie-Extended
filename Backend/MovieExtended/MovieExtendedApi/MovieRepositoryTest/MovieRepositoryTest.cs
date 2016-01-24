using System;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace MovieRepositoryTest
{
    [TestClass]
    public class MovieRepositoryTest
    {
        [TestMethod]
        public void SaveMovie()
        {
            var movieId= new Guid();
            var movieName = "movie";
            var cinemaId = new Guid();
            var movie = new Movie(movieId,movieName,cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var movieRepository = new MovieRepository(session);
                movieRepository.SaveMovie(movie);
            }
        }
    }
}
