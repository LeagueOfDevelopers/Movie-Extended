using System.Collections.Generic;
using Domain.Models;
using Domain.Models.Entities;

namespace Domain.VisitorRepository
{
   public interface IMovieRepository
    {
       IEnumerable<Movie> GetAllMovies();
       Movie GetMovieByMovieId(int movieId);
       IEnumerable<Movie> GetMovieByCinemaId(int cinema);
       void DeleteMovieByMovieId(int movieId);
       void DeleteMovieByCinemaId(int cinema);
       void SaveMovie(Movie movie);
       IEnumerable<Movie> GetMovieByMovieName(string movieName);
       void UpdateMovie(string jsonForUpdate);
       bool Exists(int movieId);
    }
}
