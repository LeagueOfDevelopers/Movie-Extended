using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Extended_Movie.Models;

namespace Extended_Movie.Visitor_Repository
{
    interface IMovieRepository
    {
        IEnumerable<Movie> GetAllMovies();
        Movie GetMovieByMovieId(Guid? movieId);
        Movie GetMovieByCinemaId(Guid cinemaId);
        void DeleteMovieByMovieId(Guid? movieId);
        void DeleteMovieByCinemaId(Guid cinemaId);
        void SaveMovie(Movie movie);
    }
}
