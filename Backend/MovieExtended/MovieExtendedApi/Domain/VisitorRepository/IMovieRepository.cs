using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface IMovieRepository
    {
        IEnumerable<Movie> GetAllMovies();
        Movie GetMovieByMovieId(string movieId);
        IEnumerable<Movie> GetMovieByCinemaId(string  cinemaId);
        void DeleteMovieByMovieId(string movieId);
        void DeleteMovieByCinemaId(string cinemaId);
        void SaveMovie(Movie movie);
       IEnumerable<Movie> GetMovieByMovieName(string movieName);
    }
}
