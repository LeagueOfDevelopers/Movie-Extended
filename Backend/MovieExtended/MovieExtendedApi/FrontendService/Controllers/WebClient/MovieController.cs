using System;
using System.Collections.Generic;
using System.Web.Http;
using Domain.Models;
using Extended_Movie.Visitor_Repository;
using Newtonsoft.Json;

    namespace FrontendService.Controllers.WebClient
{
    public class MovieController : ApiController
    {
        private readonly MovieRepository movieRepository;

        public MovieController()
        {
            movieRepository = new MovieRepository();
        }

        
        [Route("api/Movie/new/{json}")]
        [HttpGet]

        public string SaveMovie(string json)
        {
            var newMovie = JsonConvert.DeserializeObject<Movie>(json);
            movieRepository.SaveMovie(newMovie);
            return "ok";
        }

        [Route("api/Movie/All")]
        [HttpGet]
        public IEnumerable<Movie> GetAllMoviesFromDatabase()
        {
            return movieRepository.GetAllMovies();
        } 

        [Route("api/Movie/{movieId}")]
        [HttpGet]
        public Movie GetMovieByMovieId(Guid? movieId)
        {
          return  movieRepository.GetMovieByMovieId(movieId);
        }

        [Route("api/Movie/Cinema/{cinemaId}")]
        [HttpGet]
        public Movie GetMovieByCinemaId(Guid cinemaId)
        {
            return movieRepository.GetMovieByCinemaId(cinemaId);
        }

        [Route("api/Movie/Delete/{movieId}")]
        [HttpGet]
        public void DeleteMovieByMovieId(Guid? movieId)
        {
            movieRepository.DeleteMovieByMovieId(movieId);
        }

        [Route("api/Movie/Delete/Cinema/{cinemaId}")]
        [HttpGet]
        public void DeleteMovieByCinemaId(Guid cinemaId)
        {
            movieRepository.GetMovieByCinemaId(cinemaId);
        }

    }
}



