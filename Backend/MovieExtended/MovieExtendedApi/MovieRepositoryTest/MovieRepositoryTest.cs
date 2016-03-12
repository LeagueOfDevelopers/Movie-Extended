using System.Collections.Generic;
using Domain.Models.Entities;
using Infrastructure;
using Infrastructure.Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace MovieRepositoryTest
{
    [TestClass]
    public class MovieRepositoryTest
    {
        [TestMethod]
        public void SaveMovie()
        {
            //var movieName = "movie";
            //var cinemaId = 9;
            //var movie = new Movie(movieName,cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();

            var movieRepository = new MovieRepository(provider);
            //movieRepository.SaveMovie(movie);
        }

        [TestMethod]
        public void GetAllMovies()
        {
            //var movieName = "movie";
            //var cinemaId = 10;
            //var movie = new Movie( movieName, cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();
            IEnumerable<Movie> allmovies;

            var movieRepository = new MovieRepository(provider);
            //movieRepository.SaveMovie(movie);
            allmovies = movieRepository.GetAllMovies();
        }

        [TestMethod]
        public void GetMovieByCinemaId()
        {
            //var movieName = "movie";
            //var cinemaId = 11;
            //var movie = new Movie( movieName, cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();

            var movieRepository = new MovieRepository(provider);
            //var allmovies = movieRepository.GetMovieByCinemaId(
            // cinemaId);
        }

        [TestMethod]
        public void GetMovieByMovieName()
        {
            var movieName = "movie";
            //var cinemaId = 12;
            //var movie = new Movie( movieName, cinemaId);
            var provider = new SessionProvider();
            provider.OpenSession();

            var movieRepository = new MovieRepository(provider);
            var movieByName = movieRepository.GetMovieByMovieName(movieName);
        }
    }
}