using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Web.Http;
using Domain.Models.Entities;
using Domain.Repository;

namespace FrontendService.Controllers.WebClient
{
    public class MovieController : ApiController
    {
        private readonly IMovieRepository _movieRepository;

        public MovieController(IMovieRepository movieRepository)
        {
            _movieRepository = movieRepository;
        }


        [Route("movie/new/{cinemaId}")]
        [HttpPut]
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
        [HttpDelete]
        public void DeleteMovieByMovieId(int movieId)
        {
            _movieRepository.DeleteMovieByMovieId(movieId);
        }

        [Route("movie/delete/cinema/{cinemaId}")]
        [HttpDelete]
        public void DeleteMovieByCinemaId(int cinemaId)
        {
            _movieRepository.GetMovieByCinemaId(cinemaId);
        }

        [Route("create/language/{movieId}")]
        [HttpPut]
        public int CreateLanguage([FromBody] Language language, int movieId)
        {
            int IdAdmin = Convert.ToInt32(ActionContext.RequestContext.Principal.Identity.Name);
            return _movieRepository.CreateLanguage(language,movieId,IdAdmin);
        }
    }
}