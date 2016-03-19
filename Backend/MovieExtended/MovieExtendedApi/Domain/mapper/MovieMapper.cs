using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;

namespace Domain.mapper
{
    public  class MovieMapper
    {
        public  FrontendMovie ToFrontendMovie(Movie movie)
        {
            var frontendMovie = new FrontendMovie();
            frontendMovie.Id = movie.Id;
            frontendMovie.Name = movie.Name;
            frontendMovie.Poster = new FrontendFile();
            frontendMovie.Poster.Id = movie.Poster.Id;
            frontendMovie.Language=new HashSet<FrontendLanguage>();
            foreach (var language in movie.Language)
            {
                var frontendLanguage = new FrontendLanguage();
                frontendLanguage.Id = language.Id;
                frontendLanguage.Name = language.Name;
                frontendLanguage.Subtitles= new FrontendFile();
                frontendLanguage.TrackFile = new FrontendFile();
                frontendLanguage.Subtitles.Id = language.Subtitles.Id;
                frontendLanguage.TrackFile.Id = language.TrackFile.Id;
                frontendMovie.Language.Add(frontendLanguage);
            }
            return frontendMovie;
        }
    }
}
