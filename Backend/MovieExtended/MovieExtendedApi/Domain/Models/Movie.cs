using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    public class Movie
    {
        public Movie(Guid id, string name, Guid cinemaId)
        {
            Id = id;
            Name = name;
            CinemaId = cinemaId;


        }

        protected Movie()
        {
        }

        public virtual Guid Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual Guid CinemaId { get; protected set; }
    }
}
