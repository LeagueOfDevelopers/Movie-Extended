using System.Collections.Generic;
using Domain.Models;

namespace Domain.VisitorRepository
{
   public interface IMovieRepository
    {
        IEnumerable<Movie> GetAllMovies();
        Movie GetMovieByMovieId(int movieId);
        IEnumerable<Movie> GetMovieByCinemaId(int cinemaId);
        void DeleteMovieByMovieId(int movieId);
        void DeleteMovieByCinemaId(int cinemaId);
        void SaveMovie(Movie movie);
       IEnumerable<Movie> GetMovieByMovieName(string movieName);
       void UpdateMovie(string jsonForUpdate);
    }
}
