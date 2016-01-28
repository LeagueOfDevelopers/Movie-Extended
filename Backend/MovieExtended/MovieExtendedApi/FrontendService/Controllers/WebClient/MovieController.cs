using System.Collections.Generic;
using System.Web.Http;
using Domain.Models;
using Domain.VisitorRepository;
using Newtonsoft.Json;

namespace FrontendService.Controllers.WebClient
{
    public class MovieController : ApiController
    {
        private readonly IMovieRepository _movieRepository;

        public MovieController(IMovieRepository movieRepository)
        {
            _movieRepository = movieRepository;
        }
        
        [Route("api/Movie/new/{json}")]
        [HttpGet]
        public string SaveMovie(string json)
        {
            var newMovie = JsonConvert.DeserializeObject<Movie>(json);
            _movieRepository.SaveMovie(newMovie);
            return "ok";
        }

        [Route("api/Movie/All")]
        [HttpGet]
        public IEnumerable<Movie> GetAllMoviesFromDatabase()
        {
            return _movieRepository.GetAllMovies();
        } 

        [Route("api/Movie/{movieId}")]
        [HttpGet]
        public Movie GetMovieByMovieId(int movieId)
        {
          return  _movieRepository.GetMovieByMovieId(movieId);
        }

        [Route("api/Movie/Cinema/{cinemaId}")]
        [HttpGet]
        public IEnumerable<Movie> GetMovieByCinemaId(int cinemaId)
        {
            return _movieRepository.GetMovieByCinemaId(cinemaId);
        }

        [Route("api/Movie/Delete/{movieId}")]
        [HttpGet]
        public void DeleteMovieByMovieId(int movieId)
        {
            _movieRepository.DeleteMovieByMovieId(movieId);
        }

        [Route("api/Movie/Delete/Cinema/{cinemaId}")]
        [HttpGet]
        public void DeleteMovieByCinemaId(int cinemaId)
        {
            _movieRepository.GetMovieByCinemaId(cinemaId);
        }

    }
}



