using System;
using System.Collections.Generic;
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

        [TestMethod]
        public void  GetAllMovies()
        {
            var movieId = new Guid();
            var movieName = "movie";
            var cinemaId = new Guid();
            var movie = new Movie(movieId, movieName, cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();
            IEnumerable<Movie> allmovies;
            using (var session = provider.GetCurrentSession())
            {
                var movieRepository = new MovieRepository(session);
                allmovies=  movieRepository.GetAllMovies();
            }
            
        }

        [TestMethod]
        public void GetMovieByCinemaId()
        {
            var movieId = new Guid();
            var movieName = "movie";
            var cinemaId = new Guid();
            var movie = new Movie(movieId, movieName, cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var movieRepository = new MovieRepository(session);
               var allmovies = movieRepository.GetMovieByCinemaId(
                    cinemaId);
            }
        }
    }
}
