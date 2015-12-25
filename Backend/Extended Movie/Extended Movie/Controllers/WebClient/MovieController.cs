using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using NHibernate;
using Extended_Movie.Models;
using NHibernate.Linq;
namespace Extended_Movie.Controllers.WebClient
{
    public class MovieController : ApiController
    {
        private readonly ISession _session;

        public MovieController(ISession session)
        {
            _session = session;
        }

        [Route("api/Cinema/{cinemaId}/Movies")]
        [HttpGet]

        public IEnumerable<Movie> GetMoviesByCinemaId(Guid cinemaId)
        {
            return _session.Query<Movie>().Where(movie => movie.Id == cinemaId);
        }
        
        [Route("api/Movies/Delete/{movieId")]
        [HttpGet]

        public void DeleteMovieById(Guid movieId)
        {
            var checkIfMovieExists = _session.Query<Movie>().SingleOrDefault<Movie>(movie => movie.Id == movieId);
            if(checkIfMovieExists!= null)
                    _session.Delete(checkIfMovieExists);
        }

        [Route("api/Cinema/{cinemaId}/Movie/{movieId}")]
        [HttpGet]
        public Movie GetMovieByMovieIdAndCinemaId(Guid cinemaId , Guid movieId)
        {
            return _session
                .Query<Movie>()
                .Where(movie => movie._cinemaId == cinemaId)
                .SingleOrDefault(movie => movie.Id == movieId);
        }
    }
}