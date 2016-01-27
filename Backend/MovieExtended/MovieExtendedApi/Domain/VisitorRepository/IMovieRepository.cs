using System;
using System.Collections.Generic;
using Domain.Models;

namespace Extended_Movie.Visitor_Repository
{
   public interface IMovieRepository
    {
        IEnumerable<Movie> GetAllMovies();
        Movie GetMovieByMovieId(Guid movieId);
        IEnumerable<Movie> GetMovieByCinemaId(Guid cinemaId);
        void DeleteMovieByMovieId(Guid movieId);
        void DeleteMovieByCinemaId(Guid cinemaId);
        void SaveMovie(Movie movie);
       IEnumerable<Movie> GetMovieByMovieName(string movieName);
    }
}
