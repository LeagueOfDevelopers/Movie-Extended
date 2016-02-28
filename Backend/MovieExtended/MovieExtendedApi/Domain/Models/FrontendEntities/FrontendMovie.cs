using System;
using Domain.Models.Entities;

namespace Domain.Models.FrontendEntities
{
    public class FrontendMovie
    {
        public FrontendMovie(int id, string name, FrontendCinema cinema)
        {
            Id = id;
            Name = name;
            Cinema = cinema;
        }

        public FrontendMovie(string name, FrontendCinema cinema)
        {
            Name = name;
            Cinema = cinema;
        }

        protected FrontendMovie()
        {
        }

        public FrontendMovie(Movie movie)
        {
            Id = movie.Id;
            Name = movie.Name;
           // Cinema = new FrontendCinema(movie.Cinema);
            AndroidToken = movie.AndroidToken;
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual FrontendCinema Cinema { get; protected set; }

        public virtual Guid AndroidToken { get; set; }
    }
}