using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Extended_Movie.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class MovieRepository:IMovieRepository
    {
        private readonly ISession session;
        public IEnumerable<Movie> GetAllMovies()
        {
            return session.Query<Movie>();
        }

        public Movie GetMovieByMovieId(Guid? movieId)
        {
            return session.Query<Movie>().SingleOrDefault(movie => movie.Id == movieId);
        }

        public Movie GetMovieByCinemaId(Guid cinemaId)
        {
            return session.Query<Movie>().SingleOrDefault(movie => movie.CinemaId == cinemaId);
        }

        public void DeleteMovieByMovieId(Guid? movieId)
        {
            var checkIfExists = session.Query<Movie>().SingleOrDefault(movie => movie.Id == movieId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public void DeleteMovieByCinemaId(Guid cinemaId)
        {
            var checkIfExists = session.Query<Movie>().Where(movie => movie.CinemaId == cinemaId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public void SaveMovie(Movie movie)
        {
            session.BeginTransaction();
            session.SaveOrUpdate(movie);
            session.Transaction.Commit();
        }
    }
}