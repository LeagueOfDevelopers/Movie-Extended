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
            return new FrontendMovie(movie.Id,movie.Name,movie.Language,movie.Poster);
        }
    }
}
