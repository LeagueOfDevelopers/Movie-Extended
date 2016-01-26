using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Models
{
    public class Movie
    {
        public Movie(string id, string name, string cinemaId)
        {
            Id = id;
            Name = name;
            CinemaId = cinemaId;


        }

        protected Movie()
        {
        }

        public virtual string Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public virtual string CinemaId { get; protected set; }
    }
}
