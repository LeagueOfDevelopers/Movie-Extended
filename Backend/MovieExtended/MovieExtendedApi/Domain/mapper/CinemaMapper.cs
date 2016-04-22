using System.Collections.Generic;
using Domain.Models.Entities;
using Domain.Models.FrontendEntities;

namespace Domain.mapper
{
   public class CinemaMapper
    {
       public FrontendCinema ToFrontendCinema(Cinema cinema)
       {
           var frontendCinema = new FrontendCinema(cinema.Id,cinema.Name,cinema.Address);
            frontendCinema.Movie=new HashSet<FrontendMovie>();
           foreach (var movie in cinema.Movie)
           {
               var frontendMovie = new FrontendMovie(movie.Id,movie.Name);
               frontendCinema.Movie.Add(frontendMovie);
           }
           return frontendCinema;
       }
    }
}
