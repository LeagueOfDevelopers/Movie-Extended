using System;
using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using NHibernate;
using NHibernate.Linq;

namespace Extended_Movie.Visitor_Repository
{
    public class MovieRepository:IMovieRepository
    {
        private readonly ISession _session;

        public MovieRepository(ISession session)
        {
            _session = session;
        }
        public IEnumerable<Movie> GetAllMovies()
        {
            return _session.Query<Movie>();
        }

        public Movie GetMovieByMovieId(Guid movieId)
        {
            return _session.Query<Movie>().SingleOrDefault(movie => movie.Id == movieId);
        }

        public IEnumerable<Movie> GetMovieByCinemaId(Guid cinemaId)
        {
            return _session.Query<Movie>().Where(movie => movie.CinemaId == cinemaId);
        }

        public void DeleteMovieByMovieId(Guid movieId)
        {
            var checkIfExists = _session.Query<Movie>().SingleOrDefault(movie => movie.Id == movieId);
            if (checkIfExists != null) _session.Delete(checkIfExists);
        }

        public void DeleteMovieByCinemaId(Guid cinemaId)
        {
            var checkIfExists = _session.Query<Movie>().Where(movie => movie.CinemaId == cinemaId);
            if (checkIfExists != null) _session.Delete(checkIfExists);
        }

        public void SaveMovie(Movie movie)
        {
            _session.BeginTransaction();
            _session.Save(movie);
            _session.Transaction.Commit();
        }

        public IEnumerable<Movie> GetMovieByMovieName(string movieName)
        {
            return _session.Query<Movie>().Where(movie => movie.Name == movieName);
        }
    }
}