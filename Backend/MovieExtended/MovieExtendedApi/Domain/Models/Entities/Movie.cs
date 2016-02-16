using System;

namespace Domain.Models.Entities
{
    public class Movie
    {
        public Movie(int id, string name, Cinema cinema)
        {
            Id = id;
            Name = name;
            Cinema = cinema;
        }

        public Movie(string name, Cinema cinema)
        {
            Name = name;
            Cinema = cinema;
        }

        protected Movie()
        {
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Cinema Cinema { get; protected set; }

        public virtual Guid AndroidToken { get;  set; }
    }
}
