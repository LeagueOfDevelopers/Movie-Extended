using System;
using System.Collections.Generic;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using Infrastructure.VisitorRepository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace MovieRepositoryTest
{
    [TestClass]
    public class MovieRepositoryTest
    {
        [TestMethod]
        public void SaveMovie()
        {
            
            var movieName = "movie";
            var cinemaId = 9;
            var movie = new Movie(movieName,cinemaId);
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
            
            var movieName = "movie";
            var cinemaId = 10;
            var movie = new Movie( movieName, cinemaId);
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
            
            var movieName = "movie";
            var cinemaId = 11;
            var movie = new Movie( movieName, cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var movieRepository = new MovieRepository(session);
               var allmovies = movieRepository.GetMovieByCinemaId(
                    cinemaId);
            }
        }

        [TestMethod]
        public void GetMovieByMovieName()
        {
           
            var movieName = "movie";
            var cinemaId = 12;
            var movie = new Movie( movieName, cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();
            using (var session = provider.GetCurrentSession())
            {
                var movieRepository = new MovieRepository(session);
                var movieByName = movieRepository.GetMovieByMovieName(movieName);
            }
        }
    }
}
