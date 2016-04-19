using System.Collections.Generic;
using System.Dynamic;
using System.Web.Http;
using Domain.Models;
using Domain.Models.Entities;
using Domain.Repository;

namespace FrontendService.Controllers.WebClient
{
    public class MovieController : ApiController
    {
        private readonly ISessionKeeper _keeper;
        private readonly IMovieRepository _movieRepository;

        public MovieController(IMovieRepository movieRepository, ISessionKeeper keeper)
        {
            _movieRepository = movieRepository;
            _keeper = keeper;
        }


        [Route("movie/new/{cinemaId}")]
        [HttpPost]
        public void SaveMovie([FromBody] Movie newMovie)
        {
            _movieRepository.SaveMovie(newMovie);
        }

        [Route("movie/all")]
        [HttpGet]
        public IEnumerable<Movie> GetAllMoviesFromDatabase()
        {
            return _movieRepository.GetAllMovies();
        }

        [Route("movie/get/{movieId}")]
        [HttpGet]
        public Movie GetMovieByMovieId(int movieId)
        {
            return _movieRepository.GetMovieByMovieId(movieId);
        }

        [Route("movie/cinema/{cinemaId}")]
        [HttpGet]
        public IEnumerable<Movie> GetMovieByCinemaId(int cinemaId)
        {
            return _movieRepository.GetMovieByCinemaId(cinemaId);
        }

        [Route("movie/delete/{movieId}")]
        [HttpPost]
        public void DeleteMovieByMovieId(int movieId)
        {
            _movieRepository.DeleteMovieByMovieId(movieId);
        }

        [Route("movie/delete/cinema/{cinemaId}")]
        [HttpPost]
        public void DeleteMovieByCinemaId(int cinemaId)
        {
            _movieRepository.GetMovieByCinemaId(cinemaId);
        }

        [Route("create/language/{movieId}")]
        [HttpPost]
        public void CreateLanguage([FromBody] Language language, int movieId)
        {
            
        }
    }
}