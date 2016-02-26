using System;
using System.Collections.Generic;
using NHibernate.Mapping;

namespace Domain.Models.Entities
{
    public class Movie
    {
        public Movie(int id, string name , List<Language> language)
        {
            Id = id;
            Name = name;
            Language = language;

        }

        public Movie(string name , List<Language> language)
        {
            Name = name;
            Language= language;
            
        }

        protected Movie()
        {
        }

        public virtual int Id { get; protected set; }

        public virtual string Name { get; protected set; }

        public  virtual List<Language> Language { get; set; }

        public virtual Guid AndroidToken { get; set; }

        public virtual File  Poster { get; set; }
    }
}