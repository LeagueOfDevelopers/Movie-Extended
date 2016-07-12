using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Authentication;
using Domain.Models.Entities;
using Domain.Repository;
using Journalist;
using NHibernate.Linq;

namespace Infrastructure.Repository
{
    public class MovieRepository : IMovieRepository
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

        public IEnumerable<Movie> GetMovieByCinemaId(int cinema)
        {
            throw new NotImplementedException();
        }


        public void DeleteMovieByMovieId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Movie>().SingleOrDefault(movie => movie.Id == movieId);
            if (checkIfExists != null) session.Delete(checkIfExists);
        }

        public void DeleteMovieByCinemaId(int cinema)
        {
            throw new NotImplementedException();
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

        public void SetPoster(int movieId , File posterPath)
        {
            var session = _provider.GetCurrentSession();
            var movie = session.Get<Movie>(movieId);
            movie.Poster= posterPath;
            session.Update(movie,movieId);
        }

        public bool Exists(int movieId)
        {
            var session = _provider.GetCurrentSession();
            var checkIfExists = session.Query<Movie>().SingleOrDefault(company => company.Id == movieId);
            return checkIfExists != null;
        }

        public Movie CheckAndroidToken(Guid token)
        {
            var session = _provider.GetCurrentSession();
            return session.Query<Movie>().SingleOrDefault(movie => movie.AndroidToken == token);
        }

        public int GetPosterId(int movieId)
        {
            var session = _provider.GetCurrentSession();
            var movieforposter = session.Query<Movie>().SingleOrDefault(movie => movie.Id == movieId);
            return movieforposter.Poster.Id;
        }

        public int  CreateLanguage(Language language, int movieId , int IdAdmin)
        {
            var session = _provider.GetCurrentSession();
            var movie = session.Query<Movie>().SingleOrDefault(model => model.Id == movieId);
            if (movie.IdAdmin == IdAdmin)
            {
            language.IdAdmin = IdAdmin;
            movie.Language.Add(language);
            session.Update(movie);
            return language.Id;
            }
            throw new AuthenticationException();
        }
    }
}