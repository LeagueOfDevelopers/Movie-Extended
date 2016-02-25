﻿using System;
using System.Collections.Generic;
using Domain.Models.Entities;

namespace Domain.VisitorRepository
{
    public interface IMovieRepository
    {
        IEnumerable<Movie> GetAllMovies();
        Movie GetMovieByMovieId(int movieId);
        IEnumerable<Movie> GetMovieByCinemaId(int cinema);
        void DeleteMovieByMovieId(int movieId);
        void DeleteMovieByCinemaId(int cinema);
        void SaveMovie(Movie movie);
        IEnumerable<Movie> GetMovieByMovieName(string movieName);
        void SetPoster(int movieId , string posterPath);
        bool Exists(int movieId);
        Movie CheckAndroidToken(Guid token);
    }
}