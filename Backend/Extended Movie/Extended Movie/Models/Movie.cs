using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Extended_Movie.Models
{
    public class Movie
    {
        public Movie(Guid? id, string name, Guid cinemaId )
        {
            Id = id;
            _name = name;
            _cinemaId = cinemaId;
            

        }

        protected Movie()
        {
        }

        public virtual Guid? Id { get; protected set; }

        public virtual string _name { get; protected set; }

        public virtual Guid _cinemaId { get; protected set; }

       

        

        
    }
}