using System.Collections.Generic;
using System.Linq;
using Domain.Models;
using Domain.VisitorRepository;
using Journalist;
using NHibernate;
using NHibernate.Linq;

namespace Infrastructure.VisitorRepository
{
    public class MovieRepository:IMovieRepository
    {
        private readonly SessionProvider _provider;

        public MovieRepository(SessionProvider provider)
        {
            Require.NotNull(provider, nameof(SessionProvider));
            _provider = provider;
        }
        public IEnumerable<Movie> GetAllMovies()
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Movie>();
        }

        public Movie GetMovieByMovieId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Movie>().SingleOrDefault(movie => movie.Id == movieId);
        }

        public IEnumerable<Movie> GetMovieByCinemaId(int cinemaId)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Movie>().Where(movie => movie.CinemaId == cinemaId);
        }

        public void DeleteMovieByMovieId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Movie>().SingleOrDefault(movie => movie.Id == movieId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public void DeleteMovieByCinemaId(int cinemaId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Movie>().Where(movie => movie.CinemaId == cinemaId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public void SaveMovie(Movie movie)
        {
            var session = _provider.GetCurrentSession();
            session.BeginTransaction();
            session.Save(movie);
            session.Transaction.Commit();
        }

        public IEnumerable<Movie> GetMovieByMovieName(string movieName)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Movie>().Where(movie => movie.Name == movieName);
        }
    }
}